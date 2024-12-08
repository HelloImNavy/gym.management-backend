package com.gym.gym.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para manejar operaciones relacionadas con la entidad Pago.
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    /**
     * Encuentra pagos por nombre de miembro, apellidos y rango de fechas.
     *
     * @param nombreSocio   Parte del nombre del miembro (ignora mayúsculas).
     * @param apellidosSocio Parte de los apellidos del miembro (ignora mayúsculas).
     * @param fechaInicio   Fecha de inicio del rango.
     * @param fechaFin      Fecha de fin del rango.
     * @return Lista de pagos encontrados.
     */
    List<Pago> findByMiembroNombreContainingIgnoreCaseAndMiembroApellidosContainingIgnoreCaseAndFechaCobroBetween(
            String nombreSocio, String apellidosSocio, LocalDate fechaInicio, LocalDate fechaFin);
}
