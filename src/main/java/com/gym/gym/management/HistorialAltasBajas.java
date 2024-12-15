package com.gym.gym.management;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "historial_altas")
public class HistorialAltasBajas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

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

    
}
