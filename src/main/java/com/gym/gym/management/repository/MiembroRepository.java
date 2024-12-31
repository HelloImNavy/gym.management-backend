package com.gym.gym.management.repository;


import com.gym.gym.management.entity.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {

}
