package com.gym.gym.management.dto;

import java.time.LocalDate;

public class InscripcionDTO {
    private Long id;
    private Long idMiembro;
    private String nombreMiembro;
    private String apellidosMiembro;
    private Long idActividad;
    private String nombreActividad;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;

    // Constructor con todos los parámetros necesarios
    public InscripcionDTO(Long id, Long idMiembro, String nombreMiembro, String apellidosMiembro, Long idActividad, String nombreActividad, LocalDate fechaAlta, LocalDate fechaBaja) {
        this.id = id;
        this.idMiembro = idMiembro;
        this.nombreMiembro = nombreMiembro;
        this.apellidosMiembro = apellidosMiembro;
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Long idMiembro) {
        this.idMiembro = idMiembro;
    }

    public String getNombreMiembro() {
        return nombreMiembro;
    }

    public void setNombreMiembro(String nombreMiembro) {
        this.nombreMiembro = nombreMiembro;
    }

    public String getApellidosMiembro() {
        return apellidosMiembro;
    }

    public void setApellidosMiembro(String apellidosMiembro) {
        this.apellidosMiembro = apellidosMiembro;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
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
}
