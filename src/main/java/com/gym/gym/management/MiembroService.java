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
    private InscripcionRepository inscripcionRepository;

    public List<Miembro> obtenerTodosLosMiembros() {
        return miembroRepository.findAll();
    }

    public Miembro obtenerMiembroPorId(Long id) {
        return miembroRepository.findById(id).orElse(null);
    }

    public Miembro guardarMiembro(Miembro miembro) {
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