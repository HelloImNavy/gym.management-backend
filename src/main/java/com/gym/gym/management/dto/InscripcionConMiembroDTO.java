package com.gym.gym.management.dto;

public class InscripcionConMiembroDTO {
    private Long id;
    private Long miembroId;
    private Long actividadId;
    private String fechaInscripcion;
    private String fechaBaja;
    private String estado;
    private String miembroNombre;
    private String miembroApellido;
    
    
    
	public InscripcionConMiembroDTO(Long id, Long miembroId, Long actividadId, String fechaInscripcion,
			String fechaBaja, String estado, String miembroNombre, String miembroApellido) {
		super();
		this.id = id;
		this.miembroId = miembroId;
		this.actividadId = actividadId;
		this.fechaInscripcion = fechaInscripcion;
		this.fechaBaja = fechaBaja;
		this.estado = estado;	 
		this.miembroNombre = miembroNombre;
		this.miembroApellido = miembroApellido;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMiembroId() {
		return miembroId;
	}
	public void setMiembroId(Long miembroId) {
		this.miembroId = miembroId;
	}
	public Long getActividadId() {
		return actividadId;
	}
	public void setActividadId(Long actividadId) {
		this.actividadId = actividadId;
	}
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public String getMiembroNombre() {
		return miembroNombre;
	}
	public void setMiembroNombre(String miembroNombre) {
		this.miembroNombre = miembroNombre;
	}
	public String getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMiembroApellido() {
		return miembroApellido;
	}
	public void setMiembroApellido(String miembroApellido) {
		this.miembroApellido = miembroApellido;
	}

}
