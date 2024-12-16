package com.gym.gym.management;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cobrosproductos")
public class CobroProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String tipoComprador;
    private Long socioId; 
    private String productos;
    private Double importeTotal;
    @Column(name = "fecha_pago")
    private LocalDate fechaPago;
    private String estado;
    private String nombreComprador;
    
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombreComprador() {
		return nombreComprador;
	}
	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}
	public String getTipoComprador() {
		return tipoComprador;
	}
	public void setTipoComprador(String tipoComprador) {
		this.tipoComprador = tipoComprador;
	}
	public Long getSocioId() {
		return socioId;
	}
	public void setSocioId(Long socioId) {
		this.socioId = socioId;
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


    
}
