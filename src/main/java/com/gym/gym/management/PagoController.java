package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador para manejar operaciones relacionadas con pagos.
 */
@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    /**
     * Endpoint para registrar un nuevo pago.
     *
     * @param pagoRequest Objeto con los datos necesarios para registrar el pago.
     * @return El pago registrado.
     */
    @PostMapping
    public Pago registrarPago(@RequestBody PagoRequest pagoRequest) {
        return pagoService.registrarPago(
                pagoRequest.getMiembroId(),
                pagoRequest.getMonto(),
                pagoRequest.getFechaCobro(),
                pagoRequest.getDetalle()
        );
    }

    /**
     * Endpoint para buscar pagos por nombre, apellidos y rango de fechas.
     *
     * @param nombreSocio   Parte del nombre del socio (opcional).
     * @param apellidosSocio Parte de los apellidos del socio (opcional).
     * @param fechaInicio   Fecha de inicio del rango (opcional, formato ISO).
     * @param fechaFin      Fecha de fin del rango (opcional, formato ISO).
     * @return Lista de pagos encontrados.
     */
    @GetMapping("/buscar")
    public List<Pago> buscarCobros(
            @RequestParam(required = false) String nombreSocio,
            @RequestParam(required = false) String apellidosSocio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return pagoService.buscarPagosPorNombreYFechas(nombreSocio, apellidosSocio, fechaInicio, fechaFin);
    }

    /**
     * Endpoint para buscar socios por nombre.
     *
     * @param nombreSocio Parte del nombre del socio (opcional).
     * @return Lista de socios encontrados.
     */
    @GetMapping("/buscarSocios")
    public List<Miembro> buscarSocios(@RequestParam(required = false) String nombreSocio) {
        return pagoService.buscarSociosPorNombre(nombreSocio);
    }
}
