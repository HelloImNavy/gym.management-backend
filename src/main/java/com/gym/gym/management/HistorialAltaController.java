package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/historial-altas")
public class HistorialAltaController {

    @Autowired
    private HistorialAltaService historialAltaService;

    @Autowired
    private MiembroRepository miembroRepository;

    // Registrar alta
    @PostMapping("/alta/{miembroId}")
    public ResponseEntity<HistorialAlta> registrarAlta(@PathVariable Long miembroId, @RequestParam LocalDate fechaAlta) {
        Miembro miembro = miembroRepository.findById(miembroId).orElse(null);
        if (miembro != null) {
            HistorialAlta historialAlta = historialAltaService.registrarAlta(miembro, fechaAlta);
            return new ResponseEntity<>(historialAlta, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Registrar baja
    @PostMapping("/baja/{miembroId}")
    public ResponseEntity<HistorialAlta> registrarBaja(@PathVariable Long miembroId, @RequestParam LocalDate fechaBaja) {
        Miembro miembro = miembroRepository.findById(miembroId).orElse(null);
        if (miembro != null) {
            HistorialAlta historialAlta = historialAltaService.registrarBaja(miembro, fechaBaja);
            return new ResponseEntity<>(historialAlta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener historial de un miembro
    @GetMapping("/{miembroId}")
    public ResponseEntity<List<HistorialAlta>> obtenerHistorial(@PathVariable Long miembroId) {
        Miembro miembro = miembroRepository.findById(miembroId).orElse(null);
        if (miembro != null) {
            List<HistorialAlta> historial = historialAltaService.obtenerHistorialPorMiembro(miembro);
            return new ResponseEntity<>(historial, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

