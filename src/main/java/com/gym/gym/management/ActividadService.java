package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    public List<Actividad> obtenerTodasLasActividades() {
        return actividadRepository.findAll();
    }

    public Actividad obtenerActividadPorId(Long id) {
        return actividadRepository.findById(id).orElse(null);
    }

    public Actividad guardarActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    public void eliminarActividad(Long id) {
        actividadRepository.deleteById(id);
    }
    
    public boolean puedeAgregarInscripcion(Long actividadId) {
        Actividad actividad = actividadRepository.findById(actividadId).orElse(null);
        return actividad != null && actividad.tieneCupoDisponible();
    }

}
