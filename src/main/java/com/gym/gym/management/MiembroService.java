package com.gym.gym.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MiembroService {

    private static final Logger logger = LoggerFactory.getLogger(MiembroService.class);

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

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

    public Miembro guardarMiembro(Miembro miembro) {
        Miembro nuevoMiembro = miembroRepository.save(miembro);
        logger.debug("Nuevo miembro guardado: " + nuevoMiembro.getId());

        // Crear registro en el historial de altas
        HistorialAltas alta = new HistorialAltas();
        alta.setFechaAlta(LocalDate.now());
        alta.setMiembro(nuevoMiembro);
        historialRepository.save(alta);
        logger.debug("Historial de alta creado para el miembro: " + nuevoMiembro.getId());

        // Guardar las inscripciones
        miembro.getInscripciones().forEach(inscripcion -> {
            Long actividadId = inscripcion.getActividad().getId();
            Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con ID: " + actividadId));

            if (!actividad.tieneCupoDisponible()) {
                throw new IllegalStateException("La actividad '" + actividad.getNombre() + "' no tiene cupo disponible.");
            }

            actividad.incrementarCupoUsado();
            inscripcion.setActividad(actividad);
            inscripcion.setMiembro(nuevoMiembro);

            actividadRepository.save(actividad);
            inscripcionRepository.save(inscripcion);
            logger.debug("Inscripción guardada: " + inscripcion.getId());
        });

        // Crear cobros iniciales basados en las inscripciones
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
            Cobro cobroGuardado = cobroRepository.save(cobro);
            logger.debug("Cobro guardado: " + cobroGuardado.getId() + ", para miembro: " + miembro.getId());
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

    public List<Inscripcion> obtenerInscripcionesDeMiembro(Long miembroId) {
        return inscripcionRepository.findByMiembroId(miembroId);
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

    public Miembro darDeBajaActividad(Long miembroId, Long actividadId) {
        Miembro miembro = miembroRepository.findById(miembroId).orElse(null);
        if (miembro != null) {
            Inscripcion inscripcion = inscripcionRepository.findByMiembroIdAndActividadId(miembroId, actividadId);
            if (inscripcion != null) {
                inscripcion.setFechaBaja(LocalDate.now());
                inscripcionRepository.save(inscripcion);
                if (inscripcionRepository.countByMiembroIdAndFechaBajaIsNull(miembroId) == 0) {
                    HistorialAltas baja = new HistorialAltas();
                    baja.setFechaBaja(LocalDate.now());
                    baja.setMiembro(miembro);
                    historialRepository.save(baja);
                }
            }
        }
        return miembro;
    }

    public Miembro darDeBajaInscripcion(Long miembroId, Long inscripcionId, LocalDate fechaBaja) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId).orElse(null);
        if (inscripcion != null && inscripcion.getMiembro().getId().equals(miembroId)) {
            inscripcion.setFechaBaja(fechaBaja);
            inscripcionRepository.save(inscripcion);
            return miembroRepository.findById(miembroId).orElse(null);
        }
        return null;
    }

    public boolean darDeBajaMiembro(Long miembroId, LocalDate fechaBaja) {
        // Obtener el miembro por ID
        Miembro miembro = miembroRepository.findById(miembroId).orElse(null);
        if (miembro != null) {
            // 1. Actualizar la fecha de baja del miembro
            miembro.setFechaBaja(fechaBaja);
            miembroRepository.save(miembro);

            // 2. Registrar la fecha de baja en el historial de altas
            HistorialAltas historialAlta = new HistorialAltas();
            historialAlta.setMiembro(miembro);
            historialAlta.setFechaBaja(fechaBaja); // Suponiendo que este campo existe
            historialRepository.save(historialAlta);

            // 3. Actualizar la fecha de baja en las inscripciones activas
            List<Inscripcion> inscripciones = inscripcionRepository.findByMiembroId(miembroId);
            for (Inscripcion inscripcion : inscripciones) {
                // Si la inscripción está activa (por ejemplo, si no tiene fecha de baja aún)
                if (inscripcion.getFechaBaja() == null) {
                    inscripcion.setFechaBaja(fechaBaja);
                    inscripcionRepository.save(inscripcion);
                }
            }

            return true;
        }
        return false;
    }

}
