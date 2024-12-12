package com.gym.gym.management;

public class CobroManualRequest {
    private Long miembroId;
    private String concepto;
    private Double monto;
    
    
    //Getters y setters
	public Long getMiembroId() {
		return miembroId;
	}
	public void setMiembroId(Long miembroId) {
		this.miembroId = miembroId;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}

   
}
