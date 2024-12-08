package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

	@Autowired
	private InscripcionService inscripcionService;

	@GetMapping
	public List<Inscripcion> obtenerInscripciones() {
		return inscripcionService.obtenerTodasLasInscripciones();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Inscripcion> obtenerInscripcion(@PathVariable Long id) {
	    return inscripcionService.obtenerInscripcionPorId(id)
	            .map(inscripcion -> ResponseEntity.ok(inscripcion))
	            .orElse(ResponseEntity.notFound().build()); 
	}
	
	@PostMapping
	public ResponseEntity<?> registrarInscripcion(
	    @RequestParam Long idMiembro, 
	    @RequestParam Long idActividad, 
	    @RequestParam LocalDate fechaAlta
	) {
	    try {
	        Inscripcion nuevaInscripcion = inscripcionService.registrarInscripcion(idMiembro, idActividad, fechaAlta);
	        return new ResponseEntity<>(nuevaInscripcion, HttpStatus.CREATED);
	    } catch (IllegalArgumentException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}



	@PutMapping("/baja/{id}")
	public ResponseEntity<?> darDeBajaInscripcion(@PathVariable Long id, @RequestParam LocalDate fechaBaja) {
		Inscripcion inscripcion = inscripcionService.darDeBaja(id, fechaBaja);
		if (inscripcion != null) {
			return new ResponseEntity<>(inscripcion, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Inscripción ya está dada de baja o no encontrada", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarInscripcion(@PathVariable Long id) {
		boolean eliminado = inscripcionService.eliminarInscripcion(id);
		if (eliminado) {
			return new ResponseEntity<>("Inscripción eliminada correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Inscripción no encontrada", HttpStatus.NOT_FOUND);
		}
	}
}
