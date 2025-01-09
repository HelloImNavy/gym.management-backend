package com.gym.gym.management.service;

import com.gym.gym.management.entity.*;
import com.gym.gym.management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    @Autowired
    private InscripcionService inscripcionService;


    @Autowired
    private CobroRepository cobroRepository;

    @Autowired
    private HistorialAltasRepository historialRepository;

    public List<Miembro> obtenerTodosLosMiembros() {
        return miembroRepository.findAll();
    }

    public Miembro obtenerMiembroPorId(Long id) {
        return miembroRepository.findById(id).orElse(null);
    }

    @Transactional
    public Miembro guardarMiembro(Miembro miembro) {
        // Guardar el miembro en la base de datos
        Miembro nuevoMiembro = miembroRepository.save(miembro);

        // Procesar inscripciones
        if (miembro.getInscripciones() != null) {
            for (Inscripcion inscripcion : miembro.getInscripciones()) {
                Actividad actividad = actividadRepository.findById(inscripcion.getActividad().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada: " + inscripcion.getActividad().getId()));
                
                inscripcionService.procesarInscripcion(nuevoMiembro, actividad, inscripcion.getFechaAlta());
            }
        }

        // Crear registro en el historial de altas
        HistorialAltas alta = new HistorialAltas();
        alta.setFechaAlta(LocalDate.now());
        alta.setMiembro(nuevoMiembro);
        historialRepository.save(alta);
        crearCobrosIniciales(nuevoMiembro);

        return nuevoMiembro;
    }


    private void crearCobrosIniciales(Miembro miembro) {
        miembro.getInscripciones().forEach(inscripcion -> {
            Cobro cobro = new Cobro();
            cobro.setMiembro(miembro);
            cobro.setInscripcion(inscripcion);
            cobro.setFecha(LocalDate.now());
            cobro.setMonto(inscripcion.getActividad().getCosto());
            cobro.setEstado("PENDIENTE");
            cobro.setConcepto("Cuota de Actividad: " + inscripcion.getActividad().getNombre());
            cobroRepository.save(cobro);
        });
    }

    public void eliminarMiembro(Long id) {
        List<HistorialAltas> historial = historialRepository.findByMiembroId(id);
        historialRepository.deleteAll(historial);

        List<Inscripcion> inscripciones = inscripcionRepository.findByMiembroId(id);
        inscripcionRepository.deleteAll(inscripciones);

        List<Cobro> cobros = cobroRepository.buscarPorMiembroId(id);
        cobroRepository.deleteAll(cobros);

        miembroRepository.deleteById(id);
    }

    public Miembro actualizarMiembro(Long id, Miembro miembroActualizado) {
        Miembro miembro = miembroRepository.findById(id).orElse(null);
        if (miembro != null) {
            miembro.setNombre(miembroActualizado.getNombre());
            miembro.setApellidos(miembroActualizado.getApellidos());
            miembro.setDireccion(miembroActualizado.getDireccion());
            miembro.setFechaNacimiento(miembroActualizado.getFechaNacimiento());
            miembro.setTelefono(miembroActualizado.getTelefono());
            miembro.setObservaciones(miembroActualizado.getObservaciones());
            miembro.setFechaBaja(miembroActualizado.getFechaBaja());
            return miembroRepository.save(miembro);
        }
        return null;
    }

    public Page<Miembro> obtenerMiembrosPorActividad(Long actividadId, String query, Pageable pageable) {
        Page<Inscripcion> inscripciones;

        if (query == null || query.isEmpty()) {
            inscripciones = inscripcionRepository.findByActividadId(actividadId, pageable);
        } else {
            inscripciones = inscripcionRepository.buscarInscripcionesPorActividadYMiembro(actividadId, query, pageable);
        }
        List<Miembro> miembros = inscripciones.stream()
                .map(Inscripcion::getMiembro)
                .collect(Collectors.toList());
        return new PageImpl<>(miembros, pageable, inscripciones.getTotalElements());
    }

    public void darDeBajaMiembro(Long miembroId, LocalDate fechaBaja) {
        Miembro miembro = miembroRepository.findById(miembroId)
            .orElseThrow(() -> new IllegalArgumentException("Miembro no encontrado"));

        if (miembro.getFechaBaja() != null) {
            throw new IllegalArgumentException("El miembro ya está dado de baja");
        }

        // Dar de baja todas las inscripciones activas
        List<Inscripcion> inscripcionesActivas = inscripcionRepository.findByMiembroIdAndFechaBajaIsNull(miembroId);
        for (Inscripcion inscripcion : inscripcionesActivas) {
            inscripcion.setFechaBaja(fechaBaja);

            // Actualizar el cupo de la actividad
            Actividad actividad = inscripcion.getActividad();
            if (actividad != null) {
                actividad.setCupo(actividad.getCupo() + 1);
                actividadRepository.save(actividad);
            }

            inscripcionRepository.save(inscripcion);
        }

        // Actualizar el estado del miembro
        miembro.setFechaBaja(fechaBaja);
        miembroRepository.save(miembro);

        // Registrar en el historial
        HistorialAltas historial = new HistorialAltas();
        historial.setFechaAlta(LocalDate.now());
        historial.setFechaBaja(fechaBaja);
        historial.setMiembro(miembro);
        historialRepository.save(historial);
    }
}
