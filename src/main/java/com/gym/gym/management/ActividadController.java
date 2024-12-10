package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gym.gym.management.dto.ActividadDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    // Método para obtener todas las actividades, ahora devolviendo el DTO
    @GetMapping
    public List<ActividadDTO> obtenerActividades() {
        List<Actividad> actividades = actividadService.obtenerTodasLasActividades();
        // Convertimos cada actividad a un DTO y lo devolvemos
        return actividades.stream()
                .map(ActividadDTO::new)  // Convertimos la entidad a DTO
                .collect(Collectors.toList());
    }

    // Método para obtener una actividad por su ID, también devuelve el DTO
    @GetMapping("/{id}")
    public ActividadDTO obtenerActividad(@PathVariable Long id) {
        Actividad actividad = actividadService.obtenerActividadPorId(id);
        return new ActividadDTO(actividad);  // Retornamos el DTO
    }

    // Método para guardar una nueva actividad
    @PostMapping
    public Actividad guardarActividad(@RequestBody Actividad actividad) {
        return actividadService.guardarActividad(actividad);
    }

    // Método para eliminar una actividad
    @DeleteMapping("/{id}")
    public void eliminarActividad(@PathVariable Long id) {
        actividadService.eliminarActividad(id);
    }
    
    // Método para verificar si hay cupo disponible para una actividad
    @GetMapping("/{id}/cupo-disponible")
    public boolean verificarCupoDisponible(@PathVariable Long id) {
        return actividadService.puedeAgregarInscripcion(id);
    }
    
    @PutMapping("/{id}")
    public Actividad actualizarActividad(@PathVariable Long id, @RequestBody Actividad actividad) {
        Actividad actividadExistente = actividadService.obtenerActividadPorId(id);
        if (actividadExistente != null) {
      
            actividadExistente.setNombre(actividad.getNombre());
            actividadExistente.setCosto(actividad.getCosto());
            actividadExistente.setCupo(actividad.getCupo());
            
            return actividadService.guardarActividad(actividadExistente);
        }
        return null; 
    }


}