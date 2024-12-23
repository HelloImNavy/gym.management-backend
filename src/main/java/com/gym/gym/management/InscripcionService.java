package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.gym.management.dto.InscripcionConMiembroDTO;
import com.gym.gym.management.dto.InscripcionDTO;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private ActividadRepository actividadRepository;


    @Transactional
    public void registrarInscripciones(Long miembroId, Long actividadId, LocalDate fechaAlta) {
        // Buscar el miembro y la actividad
        Miembro miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new IllegalArgumentException("Miembro no encontrado: " + miembroId));
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada: " + actividadId));

        // Procesar la inscripción
        procesarInscripcion(miembro, actividad, fechaAlta);
    }
    
    public void procesarInscripcion(Miembro miembro, Actividad actividad, LocalDate fechaAlta) {
        // Validar si la actividad tiene cupo disponible
        if (actividad.getCupoUsado() >= actividad.getCupo()) {
            throw new IllegalArgumentException("No hay cupo disponible para la actividad: " + actividad.getNombre());
        }

        // Crear y configurar la inscripción
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setMiembro(miembro);
        inscripcion.setActividad(actividad);
        inscripcion.setFechaAlta(fechaAlta);

        // Guardar la inscripción
        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcion);

        // Incrementar el cupo de la actividad
        actividad.setCupoUsado(actividad.getCupoUsado() + 1);
        actividadRepository.save(actividad);

    }
    
    public Inscripcion darDeBaja(Long inscripcionId, LocalDate fechaBaja) {
        return inscripcionRepository.findById(inscripcionId).map(inscripcion -> {
            if (inscripcion.getFechaBaja() != null) {
                throw new IllegalArgumentException("La inscripción ya está dada de baja");
            }
            // Validar fecha de baja 
            if (fechaBaja.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("La fecha de baja no puede ser futura");
            }

            // Asignar fecha de baja
            inscripcion.setFechaBaja(fechaBaja);

            // Actualizar el cupo de la actividad
            Actividad actividad = inscripcion.getActividad();
            if (actividad != null) {
            	actividad.setCupoUsado(actividad.getCupoUsado() - 1);
                actividadRepository.save(actividad);
            }

            return inscripcionRepository.save(inscripcion);
        }).orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
    }


    public boolean eliminarInscripcion(Long id) {
        if (inscripcionRepository.existsById(id)) {
            inscripcionRepository.deleteById(id);
            return true; 
        }
        return false; 
    }
    
    public List<Inscripcion> obtenerTodasLasInscripciones() {
        return inscripcionRepository.obtenerInscripcionesConMiembro();
    }
    
    public Optional<Inscripcion> obtenerInscripcionPorId(Long id) {
        return inscripcionRepository.findById(id);
    }
    

    public InscripcionConMiembroDTO convertirAInscripcionConMiembroDTO(Inscripcion inscripcion) {
        InscripcionConMiembroDTO dto = new InscripcionConMiembroDTO(
            inscripcion.getId(),
            inscripcion.getMiembro().getId(),
            inscripcion.getActividad().getId(),  
            inscripcion.getFechaAlta().toString(),
            inscripcion.getFechaBaja() != null ? inscripcion.getFechaBaja().toString() : null,
            inscripcion.isActivo() ? "ACTIVO" : "INACTIVO",
            inscripcion.getMiembro().getNombre(),
            inscripcion.getMiembro().getApellidos()
        );
        return dto;
    }
    

    public List<InscripcionDTO> obtenerInscripcionesPorMiembro(Long miembroId) {
        return inscripcionRepository.findInscripcionesConNombreActividad(miembroId);
    }

}




