package com.gym.gym.management;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

        return cobroRepository.save(nuevoCobro);
    }

    public void marcarComoPagado(Long cobroId) {
        Cobro cobro = cobroRepository.findById(cobroId)
                .orElseThrow(() -> new CobroNotFoundException("El cobro especificado no existe."));
        cobro.setEstado(EstadoCobro.PAGADO.name());
        cobro.setFechaPago(LocalDate.now());
        cobroRepository.save(cobro);
    }

    public void generarCobrosPendientesMensuales() {
        List<Inscripcion> inscripcionesActivas = inscripcionRepository.findAll();

        for (Inscripcion inscripcion : inscripcionesActivas) {
            Miembro miembro = inscripcion.getMiembro();
            Actividad actividad = inscripcion.getActividad();

            if (cobroRepository.existeCobroPendiente(inscripcion.getId())) {
                continue;
            }

            Cobro cobro = new Cobro();
            cobro.setInscripcion(inscripcion); 
            cobro.setConcepto("Mensualidad de " + actividad.getNombre());
            cobro.setEstado(EstadoCobro.PENDIENTE.name());
            cobro.setFecha(LocalDate.now());
            cobro.setMonto(actividad.getCosto()); 
            cobroRepository.save(cobro);
        }
    }


    public List<Cobro> obtenerCobrosPendientes() {
        return cobroRepository.findByFechaPagoIsNull();
    }
    
    public Cobro registrarCobroManual(Long inscripcionId, String concepto, Double monto) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
            .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        Cobro cobro = new Cobro();
        cobro.setInscripcion(inscripcion);
        cobro.setConcepto(concepto);
        cobro.setMonto(monto);
        cobro.setFecha(LocalDate.now());
        cobro.setEstado(EstadoCobro.PENDIENTE.name());
        return cobroRepository.save(cobro);
    }

    

}