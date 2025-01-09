package com.gym.gym.management.controller;

import com.gym.gym.management.entity.Producto;
import com.gym.gym.management.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping
    public ResponseEntity<Producto> registrarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.registrarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @PutMapping("/{id}/restar-cantidad")
    public ResponseEntity<Producto> restarCantidad(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        int cantidadSolicitada = request.get("cantidad");
        Producto producto = productoService.obtenerProductoPorId(id);

        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Producto no encontrado
        }

        // Verificar si hay suficiente cantidad disponible
        if (producto.getCantidad() < cantidadSolicitada) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // No hay suficiente cantidad
        }

        // Restar la cantidad solicitada
        producto.setCantidad(producto.getCantidad() - cantidadSolicitada);

        // Guardar el producto actualizado
        Producto productoActualizado = productoService.actualizarProducto(id, producto);

        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar")
    public List<Producto> buscarProductos(@RequestParam(required = false) String nombre, @RequestParam(required = false) String categoria) {
        if (nombre != null) {
            return productoService.buscarPorNombre(nombre);
        }
        if (categoria != null) {
            return productoService.buscarPorCategoria(categoria);
        }
        return productoService.listarProductos();
    }
}
