package com.gym.gym.management;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoRequest {
    private Long miembroId; // ID del socio que paga
    private BigDecimal monto; // Monto del pago
    private LocalDate fechaCobro; // Fecha del pago
    private String detalle; // Descripción o detalle del pago

    // Getters y setters
    public Long getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(Long miembroId) {
        this.miembroId = miembroId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(LocalDate fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
