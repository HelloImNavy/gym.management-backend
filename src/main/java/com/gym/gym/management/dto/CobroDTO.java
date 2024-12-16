package com.gym.gym.management.dto;

import java.time.LocalDate;

public class CobroDTO {

    private Long id;
    private String concepto;
    private LocalDate fecha;
    private String estado;
    private LocalDate fechaPago;
    private Double monto;
    private Long miembroId;
    private String miembroNombre;
    private String miembroApellidos;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Long getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(Long miembroId) {
        this.miembroId = miembroId;
    }

    public String getMiembroNombre() {
        return miembroNombre;
    }

    public void setMiembroNombre(String miembroNombre) {
        this.miembroNombre = miembroNombre;
    }

    public String getMiembroApellidos() {
        return miembroApellidos;
    }

    public void setMiembroApellidos(String miembroApellidos) {
        this.miembroApellidos = miembroApellidos;
    }
}
