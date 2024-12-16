package com.gym.gym.management;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CobroRepository extends JpaRepository<Cobro, Long> {

    @Query("SELECT c FROM Cobro c JOIN FETCH c.miembro WHERE c.miembro.nombre LIKE %:nombre% OR c.miembro.apellidos LIKE %:apellidos%")
    List<Cobro> buscarPorMiembro(@Param("nombre") String nombre, @Param("apellidos") String apellidos);

    @Query("SELECT c FROM Cobro c JOIN FETCH c.miembro WHERE c.miembro.id = :id")
    List<Cobro> buscarPorMiembroId(@Param("id") Long id);

    @Query("SELECT c FROM Cobro c JOIN FETCH c.miembro WHERE c.miembro.id = :miembroId AND c.estado = :estado")
    List<Cobro> findByMiembroIdAndEstado(@Param("miembroId") Long miembroId, @Param("estado") String estado);

    @Query("SELECT c FROM Cobro c JOIN FETCH c.miembro WHERE c.inscripcion.id = :inscripcionId AND c.estado = 'PENDIENTE'")
    boolean existeCobroPendiente(@Param("inscripcionId") Long inscripcionId);

    @Query("SELECT c FROM Cobro c JOIN FETCH c.miembro WHERE "
            + "(:nombre IS NULL OR c.miembro.nombre LIKE %:nombre%) "
            + "AND (:apellidos IS NULL OR c.miembro.apellidos LIKE %:apellidos%) "
            + "AND (:fechaInicio IS NULL OR c.fecha >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR c.fecha <= :fechaFin) "
            + "AND (:estado IS NULL OR c.estado = :estado)")
    List<Cobro> buscarConFiltros(@Param("nombre") String nombre, @Param("apellidos") String apellidos,
                                 @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin,
                                 @Param("estado") String estado);

    @Query("SELECT c FROM Cobro c JOIN FETCH c.miembro")
    List<Cobro> findAll();
    
    List<Cobro> findByFechaPagoIsNull();
}
