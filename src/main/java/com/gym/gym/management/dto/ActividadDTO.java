package com.gym.gym.management.dto;

import java.util.List;

import com.gym.gym.management.Actividad;
import com.gym.gym.management.Inscripcion;

public class ActividadDTO {
    private Long id;
    private String nombre;  
    private double costo;
    private int cupoUsado;
    private int cupo;
    private boolean disponible;
    private List<Inscripcion> inscripciones;

    public ActividadDTO(Actividad actividad) {
        this.id = actividad.getId();
        this.nombre = actividad.getNombre();
        this.costo = actividad.getCosto();
        this.cupoUsado = actividad.obtenerNumeroInscripciones();
        this.cupo = actividad.getCupo();
        this.disponible = actividad.tieneCupoDisponible();
        this.inscripciones = actividad.getInscripciones();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getCupoUsado() {
        return cupoUsado;
    }

    public void setCupoUsado(int cupoUsado) {
        this.cupoUsado = cupoUsado;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public boolean tieneCupoDisponible() {
        return inscripciones.size() < cupo;
    }
}
