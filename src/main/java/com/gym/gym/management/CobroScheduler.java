package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CobroScheduler {

    @Autowired
    private CobroService cobroService;

    // Ejecutar el día 1 de cada mes
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generarCobrosMensuales() {
        cobroService.generarCobrosPendientesMensuales();
        System.out.println("Cobros mensuales generados automáticamente");
    }
}
