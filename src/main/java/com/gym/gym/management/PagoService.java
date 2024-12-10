package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para manejar operaciones relacionadas con pagos.
 */
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    /**
     * Registra un nuevo pago para un miembro.
     *
     * @param miembroId   ID del miembro.
     * @param monto       Monto del pago.
     * @param fechaCobro  Fecha del cobro.
     * @param detalle     Detalle del pago.
     * @return El pago registrado.
     */
    public Pago registrarPago(Long miembroId, BigDecimal monto, LocalDate fechaCobro, String detalle) {
        Pago nuevoPago = new Pago();

        // Buscar el miembro por su id
        Miembro miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        // Asignar el miembro al pago
        nuevoPago.setMiembro(miembro);
        nuevoPago.setMonto(monto);
        nuevoPago.setFechaCobro(fechaCobro != null ? fechaCobro : LocalDate.now());
        nuevoPago.setDetalle(detalle);

        // Guardar el pago en la base de datos
        return pagoRepository.save(nuevoPago);
    }

    /**
     * Busca pagos por nombre, apellidos y un rango de fechas.
     *
     * @param nombreSocio    Parte del nombre del socio.
     * @param apellidosSocio Parte de los apellidos del socio.
     * @param fechaInicio    Fecha de inicio del rango.
     * @param fechaFin       Fecha de fin del rango.
     * @return Lista de pagos encontrados.
     */
    public List<Pago> buscarPagosPorNombreYFechas(String nombreSocio, String apellidosSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        return pagoRepository.findByMiembroNombreContainingIgnoreCaseAndMiembroApellidosContainingIgnoreCaseAndFechaCobroBetween(
                nombreSocio, apellidosSocio, fechaInicio, fechaFin);
    }

    /**
     * Busca socios por nombre o apellidos.
     *
     * @param nombreSocio Parte del nombre o apellidos del socio.
     * @return Lista de socios encontrados.
     */
    //public List<Miembro> buscarSociosPorNombre(String nombreSocio) {
      //  if (nombreSocio == null || nombreSocio.trim().isEmpty()) {
        //    return miembroRepository.findAll(); // Si no se proporciona un nombre, devuelve todos los socios
        //}
        //return miembroRepository.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombreSocio, nombreSocio);
   // }
}
