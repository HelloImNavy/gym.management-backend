package com.gym.gym.management;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gym.gym.management.dto.CobroDTO;
import com.gym.gym.management.exceptions.CobroNotFoundException;

@Service
public class CobroService {

    @Autowired
    private CobroRepository cobroRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public enum EstadoCobro {
        PENDIENTE,
        PAGADO
    }

    public Cobro registrarCobro(Cobro nuevoCobro) {
        Miembro miembro = miembroRepository.findById(nuevoCobro.getMiembro().getId())
                .orElseThrow(() -> new CobroNotFoundException("El miembro especificado no existe."));

        nuevoCobro.setMiembro(miembro);
        nuevoCobro.setEstado(EstadoCobro.PENDIENTE.name());
        nuevoCobro.setFecha(LocalDate.now());

        if (nuevoCobro.getInscripcion() != null) {
            Inscripcion inscripcion = inscripcionRepository.findById(nuevoCobro.getInscripcion().getId())
                    .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
            nuevoCobro.setInscripcion(inscripcion);
        }

        return cobroRepository.save(nuevoCobro);
    }

    public void marcarComoPagado(Long cobroId) {
        Cobro cobro = cobroRepository.findById(cobroId)
                .orElseThrow(() -> new CobroNotFoundException("El cobro especificado no existe."));
        cobro.setEstado(EstadoCobro.PAGADO.name());
        cobro.setFechaPago(LocalDate.now());
        cobroRepository.save(cobro);
    }

    public Cobro crearCobro(Long miembroId, Long inscripcionId, String concepto, double monto) {
        Miembro miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new CobroNotFoundException("El miembro especificado no existe."));

        Cobro cobro = new Cobro();
        cobro.setMiembro(miembro);
        cobro.setFecha(LocalDate.now());
        cobro.setConcepto(concepto);
        cobro.setEstado(EstadoCobro.PENDIENTE.name());
        cobro.setMonto(monto);

        if (inscripcionId != null) {
            Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                    .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
            cobro.setInscripcion(inscripcion);
        }

        return cobroRepository.save(cobro);
    }

    public List<Cobro> buscarCobrosConFiltros(String nombre, String apellidos, String fechaInicio, String fechaFin, String estado) {
        return cobroRepository.buscarConFiltros(nombre, apellidos, fechaInicio, fechaFin, estado);
    }

    public List<Cobro> obtenerCobrosPendientes() {
        return cobroRepository.findByFechaPagoIsNull();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void generarCobrosMensuales() {
        List<Miembro> miembros = miembroRepository.findAll();
        for (Miembro miembro : miembros) {
            for (Inscripcion inscripcion : miembro.getInscripciones()) {
                if (inscripcion.getFechaBaja() == null) {
                    Cobro cobro = new Cobro();
                    cobro.setMiembro(miembro);
                    cobro.setFecha(LocalDate.now());
                    cobro.setMonto(inscripcion.getActividad().getCosto());
                    cobro.setEstado("PENDIENTE");

                    cobroRepository.save(cobro);
                }
            }
        }
    }

    public Cobro registrarCobroManual(Long inscripcionId, String concepto, Double monto) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        Cobro cobro = new Cobro();
        cobro.setInscripcion(inscripcion);  
        cobro.setConcepto(concepto);
        cobro.setMonto(monto);
        cobro.setFecha(LocalDate.now());
        cobro.setEstado("PENDIENTE");
        return cobroRepository.save(cobro);
    }

    public List<CobroDTO> obtenerTodosLosCobros() {
        List<Cobro> cobros = cobroRepository.findAll();
        return cobros.stream().map(this::convertirACobroDTO).collect(Collectors.toList());
    }

    public CobroDTO actualizarCobro(Long id, CobroDTO cobroDTO) {
        Cobro cobro = cobroRepository.findById(id)
                .orElseThrow(() -> new CobroNotFoundException("El cobro especificado no existe."));
        cobro.setConcepto(cobroDTO.getConcepto());
        cobro.setFecha(cobroDTO.getFecha());
        cobro.setEstado(cobroDTO.getEstado());
        cobro.setFechaPago(cobroDTO.getFechaPago());
        cobro.setMonto(cobroDTO.getMonto());
        cobroRepository.save(cobro);
        return convertirACobroDTO(cobro);
    }

    private CobroDTO convertirACobroDTO(Cobro cobro) {
        CobroDTO dto = new CobroDTO();
        dto.setId(cobro.getId());
        dto.setConcepto(cobro.getConcepto());
        dto.setFecha(cobro.getFecha());
        dto.setEstado(cobro.getEstado());
        dto.setFechaPago(cobro.getFechaPago());
        dto.setMonto(cobro.getMonto());
        if (cobro.getMiembro() != null) {
            dto.setMiembroId(cobro.getMiembro().getId());
            dto.setMiembroNombre(cobro.getMiembro().getNombre());
            dto.setMiembroApellidos(cobro.getMiembro().getApellidos());
        }
        return dto;
    }
}
