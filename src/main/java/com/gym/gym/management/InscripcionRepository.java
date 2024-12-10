package com.gym.gym.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Buscar inscripciones por miembro
    Inscripcion findByMiembro(Miembro miembro);
    
    // Buscar inscripciones por ID de miembro
    List<Inscripcion> findByMiembroId(Long idMiembro);

    // Método para obtener inscripciones solo por actividadId con paginación
    Page<Inscripcion> findByActividadId(Long actividadId, Pageable pageable);

    // Método con consulta personalizada usando JPQL
    @Query("SELECT i FROM Inscripcion i JOIN i.miembro m JOIN i.actividad a")
    List<Inscripcion> obtenerInscripcionesConMiembro();
    
 // Método para obtener inscripciones por actividadId y buscar por nombre o apellido
    Page<Inscripcion> findByActividadIdAndMiembroNombreContainingOrMiembroApellidosContaining(Long actividadId, String nombre, String apellidos, Pageable pageable);


}
