package com.gym.gym.management.dto;

import java.util.List;

import com.gym.gym.management.Actividad;
import com.gym.gym.management.Inscripcion;

public class ActividadDTO {
    private Long id;
    private String nombre;	
    private String descripcion;
    private double costo;
    private int cupoUsado;
    private int cupoTotal;
    private List<Inscripcion> inscripciones; // Añadido para incluir las inscripciones

    public ActividadDTO(Actividad actividad) {
        this.id = actividad.getId();
        this.nombre = actividad.getNombre();
        this.descripcion = actividad.getDescripcion();
        this.costo = actividad.getCosto();
        this.setInscripciones(actividad.getInscripciones());
        this.cupoUsado = actividad.obtenerNumeroInscripciones();
        this.cupoTotal = actividad.getCupo();
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

    public int getCupoTotal() {
        return cupoTotal;
    }

    public void setCupoTotal(int cupoTotal) {
        this.cupoTotal = cupoTotal;
    }

    public int getCupoUsado() {
        return cupoUsado;
    }

    public void setCupoUsado(int cupoUsado) {
        this.cupoUsado = cupoUsado;
    }

	public List<Inscripcion> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(List<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}
}
