package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Registrar inscripción
    public Inscripcion registrarInscripcion(Long idMiembro, Long idActividad, LocalDate fechaAlta) {
        Miembro miembro = miembroRepository.findById(idMiembro).orElseThrow(() -> new IllegalArgumentException("Miembro no encontrado"));
        Actividad actividad = actividadRepository.findById(idActividad).orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
        
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setMiembro(miembro);
        inscripcion.setActividad(actividad);
        inscripcion.setFechaAlta(fechaAlta);
        
        return inscripcionRepository.save(inscripcion);
    }

    // Baja una inscripción
    public Inscripcion darDeBaja(Long id, LocalDate fechaBaja) {
        return inscripcionRepository.findById(id).map(inscripcion -> {
            if (inscripcion.getFechaBaja() != null) {
                throw new IllegalArgumentException("La inscripción ya está dada de baja");
            }
            inscripcion.setFechaBaja(fechaBaja);
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
        return inscripcionRepository.findAll();
    }
    
    public Optional<Inscripcion> obtenerInscripcionPorId(Long id) {
        return inscripcionRepository.findById(id);
    }


}
