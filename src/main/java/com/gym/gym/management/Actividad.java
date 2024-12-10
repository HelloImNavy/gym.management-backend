package com.gym.gym.management;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private double costo;
    private int cupo;
    
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Horario> horarios = new ArrayList<>();
    
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("actividad-inscripcion")
    private List<Inscripcion> inscripciones = new ArrayList<>();



	private int cupoUsado;

    public boolean tieneCupoDisponible() {
        return inscripciones.size() < cupo;
    }

    public String obtenerEstadoCupo() {
        return String.format("%d/%d", inscripciones.size(), cupo);
    }

    public int obtenerNumeroInscripciones() {
        return inscripciones.size();
    }
    
    public void incrementarCupoUsado() {
        if (tieneCupoDisponible()) {
            this.setCupoUsado(this.getCupoUsado() + 1);
        } else {
            throw new IllegalStateException("No hay cupo disponible para esta actividad.");
        }
    }

    // Constructor vacío
    public Actividad() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

	public int getCupoUsado() {
		return cupoUsado;
	}

	public void setCupoUsado(int cupoUsado) {
		this.cupoUsado = cupoUsado;
	}
}
