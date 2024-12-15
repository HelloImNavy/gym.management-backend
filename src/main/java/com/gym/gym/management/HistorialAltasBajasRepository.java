
package com.gym.gym.management;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialAltasBajasRepository extends JpaRepository<HistorialAltasBajas, Long> {
	
	List<HistorialAltasBajas> findByMiembroId(Long miembroId);
   
}
