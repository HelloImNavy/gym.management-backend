package com.gym.gym.management;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    @JsonBackReference("miembro-inscripcion")
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "actividad_id", nullable = false)
    @JsonBackReference("actividad-inscripcion")
    private Actividad actividad;


    private LocalDate fechaAlta;

    private LocalDate fechaBaja;

    // Constructor vacío
    public Inscripcion() {}

    // Constructor con fecha de alta
    public Inscripcion(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        this.fechaBaja = null;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        if (fechaAlta == null) {
            throw new IllegalArgumentException("La fecha de alta no puede ser nula");
        }
        if (fechaAlta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser en el futuro");
        }
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        if (fechaBaja != null && fechaBaja.isBefore(fechaAlta)) {
            throw new IllegalArgumentException("La fecha de baja no puede ser anterior a la fecha de alta");
        }
        this.fechaBaja = fechaBaja;
    }

    public boolean isActivo() {
        return fechaBaja == null;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
}
