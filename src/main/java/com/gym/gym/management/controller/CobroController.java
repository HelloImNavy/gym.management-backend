package com.gym.gym.management.controller;

import java.util.List;

import com.gym.gym.management.entity.Cobro;
import com.gym.gym.management.entity.CobroProducto;
import com.gym.gym.management.repository.CobroRepository;
import com.gym.gym.management.service.CobroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gym.gym.management.dto.CobroDTO;


@RestController
@RequestMapping("/cobros")
public class CobroController {

    @Autowired
    private CobroService cobroService;

    @Autowired
    private CobroRepository cobroRepository;

    @GetMapping("/miembro/{miembroId}")
    public ResponseEntity<List<CobroDTO>> getCobrosPorMiembro(@PathVariable Long miembroId) {
        try {
            List<CobroDTO> cobros = cobroService.buscarCobrosPorMiembro(miembroId);
            return ResponseEntity.ok(cobros);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/pagado")
    public ResponseEntity<List<CobroDTO>> getAllCobrosPagados() {
        List<CobroDTO> cobrosPagados = cobroService.getAllCobrosPagados();
        return ResponseEntity.ok(cobrosPagados);
    }

    @GetMapping("/pagado/anio")
    public ResponseEntity<List<CobroDTO>> getAllCobrosPagadosAnio( @RequestParam Integer anio) {
        List<CobroDTO> cobrosPagados = cobroService.getAllCobrosPagadosAnio(anio);
        return ResponseEntity.ok(cobrosPagados);
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
    
    @GetMapping
    public ResponseEntity<List<CobroDTO>> obtenerTodosLosCobros() { 
        List<CobroDTO> cobros = cobroService.obtenerTodosLosCobros(); 
        return ResponseEntity.ok(cobros); 
    }
    
    @GetMapping("/todosPagados/{miembroId}")
    public ResponseEntity<Boolean> verificarTodosPagados(@PathVariable Long miembroId) {
        boolean todosPagados = cobroService.verificarTodosPagados(miembroId);
        return ResponseEntity.ok(todosPagados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCobro(@PathVariable Long id) {
        cobroRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

}
