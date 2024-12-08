package com.gym.gym.management;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "actividad_id", nullable = false)
    private Actividad actividad;

    @NotNull(message = "La fecha de alta no puede ser nula")
    @PastOrPresent(message = "La fecha de alta no puede ser en el futuro")
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
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        // Validamos que la fecha de baja no sea anterior a la fecha de alta
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
