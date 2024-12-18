package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
    public ResponseEntity<Miembro> obtenerMiembro(@PathVariable Long id) {
        Miembro miembro = miembroService.obtenerMiembroPorId(id);
        if (miembro != null) {
            return ResponseEntity.ok(miembro);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    /*@PutMapping("/{miembroId}/baja/{inscripcionId}")
    public ResponseEntity<?> darDeBajaMiembro(@PathVariable Long miembroId, @PathVariable Long inscripcionId, @RequestParam LocalDate fechaBaja) {
        Miembro miembro = miembroService.darDeBajaMiembro(miembroId, inscripcionId, fechaBaja);
        if (miembro != null) {
            return ResponseEntity.ok(miembro);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo dar de baja la inscripción.");
        }
    }*/

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
    public ResponseEntity<String> darDeBajaMiembro(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String fechaBaja = payload.get("fechaBaja");

        if (fechaBaja == null || fechaBaja.isEmpty()) {
            return ResponseEntity.badRequest().body("La fecha de baja es requerida");
        }

        try {
            LocalDate fecha = LocalDate.parse(fechaBaja);
            boolean exito = miembroService.darDeBajaMiembro(id, fecha);
            if (exito) {
                return ResponseEntity.ok("Miembro dado de baja correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo dar de baja al miembro.");
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Formato de fecha inválido. Se esperaba 'yyyy-MM-dd'.");
        }
    }


}
