package com.gym.gym.management.repository;

import java.util.List;

import com.gym.gym.management.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
   
	@Query("SELECT a FROM Actividad a WHERE a.cupoUsado < a.cupo")
	List<Actividad> findByCupoUsadoLessThanCupo();


}
