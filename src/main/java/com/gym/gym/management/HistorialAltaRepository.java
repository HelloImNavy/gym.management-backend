package com.gym.gym.management;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialAltaRepository extends JpaRepository<HistorialAlta, Long> {

    // Encuentra el último registro de alta de un miembro ordenado por fecha de alta descendente
    HistorialAlta findTopByMiembroOrderByFechaAltaDesc(Miembro miembro);

    // Encuentra todos los registros de alta de un miembro
    List<HistorialAlta> findAllByMiembro(Miembro miembro);
}
