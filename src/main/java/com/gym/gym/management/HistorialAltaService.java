package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class HistorialAltaService {

    @Autowired
    private HistorialAltaRepository historialAltaRepository;

    // Registrar una nueva alta
    public HistorialAlta registrarAlta(Miembro miembro, LocalDate fechaAlta) {
        HistorialAlta historialAlta = new HistorialAlta();
        historialAlta.setMiembro(miembro);
        historialAlta.setFechaAlta(fechaAlta);
        historialAlta.setEstado("activo");
        return historialAltaRepository.save(historialAlta);
    }

    // Registrar una baja
    public HistorialAlta registrarBaja(Miembro miembro, LocalDate fechaBaja) {
        HistorialAlta historialAlta = historialAltaRepository.findTopByMiembroOrderByFechaAltaDesc(miembro);
        if (historialAlta != null) {
            historialAlta.setFechaBaja(fechaBaja);
            historialAlta.setEstado("inactivo");
            return historialAltaRepository.save(historialAlta);
        }
        return null;
    }

    // Obtener historial completo de un miembro
    public List<HistorialAlta> obtenerHistorialPorMiembro(Miembro miembro) {
        return historialAltaRepository.findAllByMiembro(miembro);
    }
}

