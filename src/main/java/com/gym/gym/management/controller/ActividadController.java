package com.gym.gym.management.controller;

import com.gym.gym.management.entity.Actividad;
import com.gym.gym.management.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gym.gym.management.dto.ActividadDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actividades")
@CrossOrigin(origins = "http://localhost:4200")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;
    
    // Método para obtener todas las actividades, ahora devolviendo el DTO
    @GetMapping
    public List<ActividadDTO> obtenerActividades() {
        List<Actividad> actividades = actividadService.obtenerTodasLasActividades();
        return actividades.stream()
                .map(ActividadDTO::new)
                .collect(Collectors.toList());
    }

    // Método para obtener una actividad por su ID, también devuelve el DTO
    @GetMapping("/{id}")
    public ActividadDTO obtenerActividad(@PathVariable Long id) {
        Actividad actividad = actividadService.obtenerActividadPorId(id);
        return new ActividadDTO(actividad);
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
    public ResponseEntity<Actividad> actualizarActividad(@PathVariable Long id, @RequestBody Actividad actividad) {
        Actividad actividadExistente = actividadService.obtenerActividadPorId(id);
        if (actividadExistente != null) {
            actividadExistente.setNombre(actividad.getNombre());
            actividadExistente.setCosto(actividad.getCosto());
            actividadExistente.setCupo(actividad.getCupo());
            return ResponseEntity.ok(actividadService.guardarActividad(actividadExistente));
        }
        return ResponseEntity.notFound().build();
    }


    // Método para obtener actividades disponibles
    @GetMapping("/disponibles")
    public List<ActividadDTO> getActividadesDisponibles() {
        List<Actividad> actividades = actividadService.getActividadesDisponibles();
        return actividades.stream()
                .map(ActividadDTO::new)
                .collect(Collectors.toList());
    }
    

    // Nuevo método para obtener solo el nombre de una actividad por su ID
    @GetMapping("/{id}/nombre")
    public ResponseEntity<String> obtenerNombreActividad(@PathVariable Long id) {
        Actividad actividad = actividadService.obtenerActividadPorId(id);
        if (actividad != null) {
            return ResponseEntity.ok(actividad.getNombre());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

}
