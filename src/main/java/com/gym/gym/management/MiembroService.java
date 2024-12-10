package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;
    
    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<Miembro> obtenerTodosLosMiembros() {
        return miembroRepository.findAll();
    }

    public Miembro obtenerMiembroPorId(Long id) {
        return miembroRepository.findById(id).orElse(null);
    }

    public Miembro guardarMiembro(Miembro miembro) {
        miembro.getInscripciones().forEach(inscripcion -> {
            // Asocia las actividades y establece la fecha de alta
            Long actividadId = inscripcion.getActividad().getId();
            Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con ID: " + actividadId));

            // Verificar disponibilidad de cupo
            if (!actividad.tieneCupoDisponible()) {
                throw new IllegalStateException("La actividad '" + actividad.getNombre() + "' no tiene cupo disponible.");
            }

            // Incrementar cupo usado y asociar actividad a la inscripción
            actividad.incrementarCupoUsado();
            inscripcion.setActividad(actividad);

            // Si no se establece una fecha, usa la fecha actual
            if (inscripcion.getFechaAlta() == null) {
                inscripcion.setFechaAlta(LocalDate.now());
            }

            // Guardar cambios en la actividad
            actividadRepository.save(actividad);
        });

        // Guardar el miembro con sus inscripciones
        return miembroRepository.save(miembro);
    }


    public void eliminarMiembro(Long id) {
        miembroRepository.deleteById(id);
    }

    // Agregar métodos para trabajar con inscripciones si es necesario
    public List<Inscripcion> obtenerInscripcionesDeMiembro(Long miembroId) {
        return inscripcionRepository.findByMiembroId(miembroId);
    }

    public Miembro darDeBajaMiembro(Long miembroId, Long idInscripcion, LocalDate fechaBaja) {
        Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion).orElse(null);
        if (inscripcion != null && inscripcion.getMiembro().getId().equals(miembroId)) {
            inscripcion.setFechaBaja(fechaBaja);
            inscripcionRepository.save(inscripcion);
            return miembroRepository.findById(miembroId).orElse(null);
        }
        return null;
    }
}