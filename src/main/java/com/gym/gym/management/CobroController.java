package com.gym.gym.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<Cobro> cobros = cobroRepository.buscarPorMiembro(nombre != null ? nombre : "", apellidos != null ? apellidos : "");
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
}