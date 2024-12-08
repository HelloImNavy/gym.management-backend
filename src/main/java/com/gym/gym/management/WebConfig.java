package com.gym.gym.management;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir todas las rutas
            .allowedOrigins("http://localhost:4200") // Permitir solo el origen de tu frontend Angular
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
            .allowedHeaders("*") // Permitir todos los encabezados
            .allowCredentials(true); // Permitir el envío de cookies
    }
}
