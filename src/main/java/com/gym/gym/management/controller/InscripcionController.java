package com.gym.gym.management.controller;

import com.gym.gym.management.entity.Inscripcion;
import com.gym.gym.management.service.InscripcionService;
import com.gym.gym.management.dto.InscripcionConMiembroDTO;
import com.gym.gym.management.dto.InscripcionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    // Registrar inscripciones
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrarInscripcion(
            @RequestParam Long miembroId,
            @RequestParam Long actividadId) {

        // Crear la inscripción usando el servicio
        Inscripcion inscripcion = inscripcionService.registrarInscripciones(miembroId, actividadId, LocalDate.now());

        // Crear la respuesta con el mensaje y el objeto de inscripción
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Inscripción registrada correctamente");
        response.put("inscripcion", inscripcion);  // Aquí agregas el objeto de inscripción

        return ResponseEntity.ok(response);  // Retornar la respuesta
    }


    // Dar de baja una inscripción
    @PutMapping("/baja/{id}")
    public ResponseEntity<Map<String, String>> darDeBaja(@PathVariable Long id, @RequestParam String fechaBaja) {
        LocalDate fecha = LocalDate.parse(fechaBaja);
        inscripcionService.darDeBaja(id, fecha);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Inscripción dada de baja correctamente");
        return ResponseEntity.ok(response);
    }

    // Eliminar una inscripción
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarInscripcion(@PathVariable Long id) {
        boolean eliminado = inscripcionService.eliminarInscripcion(id);
        if (eliminado) {
            return ResponseEntity.ok("Inscripción eliminada correctamente");
        } else {
            return ResponseEntity.badRequest().body("Inscripción no encontrada");
        }
    }

    // Obtener todas las inscripciones con detalles de miembro
    @GetMapping("/listar")
    public ResponseEntity<List<InscripcionConMiembroDTO>> obtenerTodasLasInscripciones() {
        List<Inscripcion> inscripciones = inscripcionService.obtenerTodasLasInscripciones();
        List<InscripcionConMiembroDTO> dtos = inscripciones.stream()
                .map(inscripcionService::convertirAInscripcionConMiembroDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    // Obtener una inscripción por ID
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionConMiembroDTO> obtenerInscripcionPorId(@PathVariable Long id) {
        Optional<Inscripcion> inscripcion = inscripcionService.obtenerInscripcionPorId(id);
        if (inscripcion.isPresent()) {
            InscripcionConMiembroDTO dto = inscripcionService.convertirAInscripcionConMiembroDTO(inscripcion.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar inscripciones por miembro y actividad
    @GetMapping("/buscar")
    public ResponseEntity<List<InscripcionDTO>> buscarInscripciones(
            @RequestParam Long miembroId) {
        List<InscripcionDTO> inscripciones = inscripcionService.obtenerInscripcionesPorMiembro(miembroId);
        return ResponseEntity.ok(inscripciones);
    }
}
