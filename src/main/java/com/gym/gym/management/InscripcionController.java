	package com.gym.gym.management;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

import com.gym.gym.management.dto.InscripcionDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
	
	@RestController
	@RequestMapping("/inscripciones")
	public class InscripcionController {
	
		@Autowired
		private InscripcionService inscripcionService;
		
	    @Autowired
	    private MiembroRepository miembroRepository;
	    
	    @Autowired
	    private ActividadRepository actividadRepository;

	    @Autowired
	    private InscripcionRepository inscripcionRepository;

	
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
		public List<Inscripcion> registrarInscripciones(@RequestBody List<InscripcionDTO> inscripcionesDTO) {
		    List<Inscripcion> inscripciones = new ArrayList<>();
		    for (InscripcionDTO inscripcionDTO : inscripcionesDTO) {
		        Miembro miembro = miembroRepository.findById(inscripcionDTO.getIdMiembro())
		                .orElseThrow(() -> new IllegalArgumentException("Miembro no encontrado"));

		        Actividad actividad = actividadRepository.findById(inscripcionDTO.getIdActividad())
		                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));

		        Inscripcion inscripcion = new Inscripcion();
		        inscripcion.setMiembro(miembro);
		        inscripcion.setActividad(actividad);
		        inscripcion.setFechaAlta(inscripcionDTO.getFechaAlta());

		        inscripciones.add(inscripcionRepository.save(inscripcion));
		    }
		    return inscripciones;
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
