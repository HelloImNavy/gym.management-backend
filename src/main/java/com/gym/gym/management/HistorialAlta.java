package com.gym.gym.management;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "historial_altas")
public class HistorialAlta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    private LocalDate fechaAlta;

    private LocalDate fechaBaja;

    // Indicador del estado del miembro en este tramo
    @Column(nullable = false)
    private String estado; // "activo" o "inactivo"

    // Constructor vacío
    public HistorialAlta() {
    }

    // Constructor con parámetros
    public HistorialAlta(Miembro miembro, LocalDate fechaAlta, LocalDate fechaBaja, String estado) {
        this.miembro = miembro;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
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
        this.fechaBaja = fechaBaja;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
