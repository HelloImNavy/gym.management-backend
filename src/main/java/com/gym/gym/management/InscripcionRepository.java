package com.gym.gym.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gym.gym.management.dto.InscripcionDTO;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

	// Buscar inscripciones por miembro
	Inscripcion findByMiembro(Miembro miembro);

	// Método para obtener inscripciones solo por actividadId con paginación
	Page<Inscripcion> findByActividadId(Long actividadId, Pageable pageable);

	// Método con consulta personalizada usando JPQL
	@Query("SELECT i FROM Inscripcion i JOIN i.miembro m JOIN i.actividad a")
	List<Inscripcion> obtenerInscripcionesConMiembro();

	@Query("SELECT i FROM Inscripcion i JOIN i.miembro m WHERE i.actividad.id = :actividadId " +
		       "AND (m.nombre LIKE %:query% OR m.apellidos LIKE %:query%)")
		Page<Inscripcion> buscarInscripcionesPorActividadYMiembro(@Param("actividadId") Long actividadId, @Param("query") String query, Pageable pageable);

	List<Inscripcion> findByMiembroId(Long miembroId);
	    long countByMiembroIdAndFechaBajaIsNull(Long miembroId);
	    Inscripcion findByMiembroIdAndActividadId(Long miembroId, Long actividadId);
	    
	 
	List<Inscripcion> findByMiembroIdAndFechaBajaIsNull(Long miembroId);	   
	
	@Query("SELECT new com.gym.gym.management.dto.InscripcionDTO(i.miembro.id, i.miembro.nombre, i.miembro.apellidos, a.id, a.nombre, i.fechaAlta, i.fechaBaja) " +
		       "FROM Inscripcion i JOIN i.actividad a " +
		       "WHERE i.miembro.id = :idMiembro")
		List<InscripcionDTO> findInscripcionesConNombreActividad(@Param("idMiembro") Long idMiembro);

}




