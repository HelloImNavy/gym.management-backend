package com.gym.gym.management.controller;

import com.gym.gym.management.entity.Inscripcion;
import com.gym.gym.management.entity.Miembro;
import com.gym.gym.management.service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/miembros")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    @GetMapping
    public List<Miembro> obtenerMiembros() {
        return miembroService.obtenerTodosLosMiembros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerMiembro(@PathVariable Long id) {
        Miembro miembro = miembroService.obtenerMiembroPorId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("id", miembro.getId());
        response.put("nombre", miembro.getNombre());
        response.put("apellidos", miembro.getApellidos());
        response.put("direccion", miembro.getDireccion());
        response.put("telefono", miembro.getTelefono());
        response.put("fechaNacimiento", miembro.getFechaNacimiento());
        response.put("fechaAlta", miembro.getFechaAlta());
        response.put("fechaBaja", miembro.getFechaBaja());
        response.put("observaciones", miembro.getObservaciones());

        // Agregar inscripciones con detalles completos de la actividad
        List<Map<String, Object>> inscripciones = new ArrayList<>();
        for (Inscripcion inscripcion : miembro.getInscripciones()) {
            Map<String, Object> inscripcionMap = new HashMap<>();
            inscripcionMap.put("id", inscripcion.getId());
            inscripcionMap.put("fechaAlta", inscripcion.getFechaAlta());
            inscripcionMap.put("fechaBaja", inscripcion.getFechaBaja());
            inscripcionMap.put("activo", inscripcion.isActivo());

            // Detalles completos de la actividad
            Map<String, Object> actividadMap = new HashMap<>();
            actividadMap.put("id", inscripcion.getActividad().getId());
            actividadMap.put("nombre", inscripcion.getActividad().getNombre());
            actividadMap.put("descripcion", inscripcion.getActividad().getDescripcion());
            // Agrega otros detalles de la actividad si es necesario

            inscripcionMap.put("actividad", actividadMap);  // Incluir los detalles de la actividad
            inscripciones.add(inscripcionMap);
        }

        response.put("inscripciones", inscripciones);

        return ResponseEntity.ok(response);
    }



    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Miembro> actualizarMiembro(@PathVariable Long id, @RequestBody Miembro miembro) {
        Miembro miembroActualizado = miembroService.actualizarMiembro(id, miembro);
        if (miembroActualizado != null) {
            return ResponseEntity.ok(miembroActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Miembro> registrarMiembro(@RequestBody Miembro miembro) {
        Miembro nuevoMiembro = miembroService.guardarMiembro(miembro);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMiembro);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMiembro(@PathVariable Long id) {
        miembroService.eliminarMiembro(id);
        return ResponseEntity.ok("Miembro eliminado correctamente");
    }

    @GetMapping("/actividad/{actividadId}")
    public ResponseEntity<Page<Miembro>> obtenerMiembrosDeActividad(
            @PathVariable Long actividadId,
            @RequestParam(required = false) String query,
            Pageable pageable) {
        Page<Miembro> miembros = miembroService.obtenerMiembrosPorActividad(actividadId, query, pageable);
        return ResponseEntity.ok(miembros);
    }
    

    // Dar de baja el miembro completo (asignar fecha de baja al miembro y sus inscripciones)
    @PutMapping("/{id}/baja")
    public ResponseEntity<Map<String, Object>> darDeBajaMiembro(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String fechaBaja = payload.get("fechaBaja");

        // Validar si la fecha de baja está presente en el payload
        if (fechaBaja == null || fechaBaja.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "La fecha de baja es requerida");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            // Convertir la fecha de baja en LocalDate
            LocalDate fecha = LocalDate.parse(fechaBaja);

            // Llamar al servicio para dar de baja al miembro
            miembroService.darDeBajaMiembro(id, fecha);

            // Preparar la respuesta con los detalles de la operación
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Miembro dado de baja correctamente");
            response.put("id", id); // Puedes agregar más información relevante aquí si es necesario
            response.put("fechaBaja", fecha.toString()); // Fecha de baja en formato String (yyyy-MM-dd)

            // Retornar una respuesta exitosa con el mensaje en JSON
            return ResponseEntity.ok(response);

        } catch (DateTimeParseException e) {
            // Si la fecha no tiene el formato adecuado, retornamos un error
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Formato de fecha inválido. Se esperaba 'yyyy-MM-dd'.");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (IllegalArgumentException e) {
            // Manejo de otro tipo de error, si se lanza una excepción de este tipo
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }




}
