
package com.gym.gym.management;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistorialAltasRepository extends JpaRepository<HistorialAltas, Long> {
    // Buscar la última entrada de alta sin fecha de baja
    Optional<HistorialAltas> findFirstByMiembroIdAndFechaBajaIsNullOrderByFechaAltaDesc(Long miembroId);
    List<HistorialAltas> findByMiembroId(Long miembroId);
}
