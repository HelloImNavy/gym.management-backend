package com.gym.gym.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gym.gym.management.dto.CobroDTO;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cobros")
public class CobroController {

    @Autowired
    private CobroService cobroService;

    @Autowired
    private CobroRepository cobroRepository;

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

    @PutMapping("/{id}")
    public ResponseEntity<CobroDTO> actualizarCobro(@PathVariable Long id, @RequestBody CobroDTO cobroDTO) {
        CobroDTO actualizado = cobroService.actualizarCobro(id, cobroDTO);
        return ResponseEntity.ok(actualizado);
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
    
    @GetMapping public ResponseEntity<List<CobroDTO>> obtenerTodosLosCobros() { 
        List<CobroDTO> cobros = cobroService.obtenerTodosLosCobros(); return ResponseEntity.ok(cobros); 
    }
}
