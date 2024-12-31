package com.gym.gym.management.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cobrosproductos")
public class CobroProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoComprador;

    @ManyToOne
    @JoinColumn(name = "socio_id", referencedColumnName = "id", nullable = true)  // Relación con Miembro, puede ser null
    private Miembro miembro;  // Relacionamos con la entidad Miembro, puede ser null

    private String productos;
    private Double importeTotal;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    private String estado;
    private String nombreComprador; 

    private String observaciones;

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTipoComprador() {
        return tipoComprador;
    }
    public void setTipoComprador(String tipoComprador) {
        this.tipoComprador = tipoComprador;
    }
    public Miembro getMiembro() {
        return miembro;
    }
    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }
    public Long getSocioId() {
        return miembro != null ? miembro.getId() : null;  // Si miembro no es null, devolvemos su ID
    }
    public void setSocioId(Long socioId) {
        if (socioId != null) {
            this.miembro = new Miembro();  // Aseguramos que miembro se asigna
            this.miembro.setId(socioId);
        } else {
            this.miembro = null;  // Si no hay miembro, lo dejamos como null
        }
    }
    public String getProductos() {
        return productos;
    }
    public void setProductos(String productos) {
        this.productos = productos;
    }
    public Double getImporteTotal() {
        return importeTotal;
    }
    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }
    public LocalDate getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNombreComprador() {
        return nombreComprador;
    }
    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
