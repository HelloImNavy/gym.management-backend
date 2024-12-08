package com.gym.gym.management;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    Inscripcion findByMiembro(Miembro miembro);
    
    public List<Inscripcion> findByMiembroId(Long idMiembro);

}
