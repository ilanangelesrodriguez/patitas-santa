package org.uns.patitassanta.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI patitasSantaOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Patitas del Santa - API REST")
                .version("1.0.0")
                .description("Gestión veterinaria: dueños, mascotas, veterinarios y citas. "
                    + "Proyecto de Arquitectura de Software (UNS), con Spring Boot y persistencia JPA. "
                    + "Las operaciones de escritura deben ejecutarse con datos de prueba."));
    }
}
