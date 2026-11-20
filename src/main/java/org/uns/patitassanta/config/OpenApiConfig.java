package org.uns.patitassanta.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI patitasSantaOpenApi() {
        return new OpenAPI()
            // Ruta relativa: Swagger usa el origen actual (HTTPS en Koyeb/Mistral Run,
            // HTTP en localhost). Evita que Try it out llame a un http:// fijo.
            .servers(List.of(new Server()
                .url("/")
                .description("Servidor actual: mismo dominio y protocolo del navegador")))
            .info(new Info()
                .title("Patitas del Santa - API REST")
                .version("1.0.0")
                .description("Gestión veterinaria: dueños, mascotas, veterinarios y citas. "
                    + "Proyecto de Arquitectura de Software (UNS), con Spring Boot y persistencia JPA. "
                    + "Las operaciones de escritura deben ejecutarse con datos de prueba."));
    }
}
