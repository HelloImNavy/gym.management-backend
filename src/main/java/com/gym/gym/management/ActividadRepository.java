package com.gym.gym.management;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    // Métodos personalizados si es necesario
}