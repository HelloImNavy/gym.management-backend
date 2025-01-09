package com.gym.gym.management.controller;

import com.gym.gym.management.entity.CobroProducto;
import com.gym.gym.management.service.CobroProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos/pagos")

public class CobroProductoController {

    @Autowired
    private CobroProductoService cobroProductoService;

    @GetMapping
    public List<CobroProducto> getAllCobros() {
        return cobroProductoService.getAllCobros();
    }

    @GetMapping("/pagado")
    public List<CobroProducto> getAllCobrosPagados() {
        return cobroProductoService.getAllCobrosPagados();
    }

    @PostMapping
    public CobroProducto saveCobro(@RequestBody CobroProducto cobroProducto) {
        return cobroProductoService.saveCobro(cobroProducto);
    }

    @PutMapping("/{id}")
    public CobroProducto updateCobro(@PathVariable Long id, @RequestBody CobroProducto cobroProducto) {
        return cobroProductoService.updateCobro(id, cobroProducto);
    }

    @DeleteMapping("/{id}")
    public void deleteCobro(@PathVariable Long id) {
        cobroProductoService.deleteCobro(id);
    }
}
