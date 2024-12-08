package com.gym.gym.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Miembro.
 */
@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {

    /**
     * Busca miembros cuyo nombre o apellidos contengan una cadena específica, ignorando mayúsculas y minúsculas.
     *
     * @param nombre    Parte del nombre.
     * @param apellidos Parte de los apellidos.
     * @return Lista de miembros encontrados.
     */
    List<Miembro> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);
}
