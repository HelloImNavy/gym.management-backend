package com.gym.gym.management;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CobroRepository extends JpaRepository<Cobro, Long> {

    @Query("SELECT c FROM Cobro c WHERE c.miembro.nombre LIKE %:nombre% OR c.miembro.apellidos LIKE %:apellidos%")
    List<Cobro> buscarPorMiembro(@Param("nombre") String nombre, @Param("apellidos") String apellidos);

    @Query("SELECT c FROM Cobro c WHERE c.miembro.id = :id")
    List<Cobro> buscarPorMiembroId(@Param("id") Long id);

    List<Cobro> findByFechaPagoIsNull();    
    
    @Query("SELECT c FROM Cobro c WHERE c.miembro.id = :miembroId AND c.estado = :estado")
    List<Cobro> findByMiembroIdAndEstado(@Param("miembroId") Long miembroId, @Param("estado") String estado);

    @Query("SELECT COUNT(c) > 0 FROM Cobro c WHERE c.inscripcion.id = :inscripcionId AND c.estado = 'PENDIENTE'")
    boolean existeCobroPendiente(@Param("inscripcionId") Long inscripcionId);

}