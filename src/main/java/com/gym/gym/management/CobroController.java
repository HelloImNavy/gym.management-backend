package com.gym.gym.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cobros")
public class CobroController {

    @Autowired
    private CobroService cobroService;

    @Autowired
    private CobroRepository cobroRepository;

    @GetMapping
    public ResponseEntity<List<Cobro>> obtenerTodosLosCobros() {
        List<Cobro> cobros = cobroRepository.findAll();
        return ResponseEntity.ok(cobros);
    }

    @GetMapping("/miembro")
    public ResponseEntity<List<Cobro>> buscarCobrosPorMiembro(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellidos) {
        List<Cobro> cobros = cobroRepository.buscarPorMiembro(
                nombre != null ? nombre : "",
                apellidos != null ? apellidos : "");
        return ResponseEntity.ok(cobros);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Cobro>> buscarCobrosConFiltros(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellidos,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) String estado) {
        List<Cobro> cobros = cobroService.buscarCobrosConFiltros(
                nombre, apellidos, fechaInicio, fechaFin, estado);
        return ResponseEntity.ok(cobros);
    }

    @PostMapping
    public ResponseEntity<Cobro> registrarCobro(@RequestBody Cobro nuevoCobro) {
        Cobro cobro = cobroService.registrarCobro(nuevoCobro);
        return new ResponseEntity<>(cobro, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<String> marcarComoPagado(@PathVariable Long id) {
        cobroService.marcarComoPagado(id);
        return new ResponseEntity<>("Cobro marcado como pagado", HttpStatus.OK);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Cobro>> getCobrosPendientes() {
        List<Cobro> pendientes = cobroService.obtenerCobrosPendientes();
        return ResponseEntity.ok(pendientes);
    }

    @PostMapping("/crear")
    public ResponseEntity<Cobro> crearCobro(@RequestParam Long miembroId, @RequestParam(required = false) Long inscripcionId, @RequestParam String concepto, @RequestParam double monto) {
        Cobro nuevoCobro = cobroService.crearCobro(miembroId, inscripcionId, concepto, monto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCobro);
    }
}
