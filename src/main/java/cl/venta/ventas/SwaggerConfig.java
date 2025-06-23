package cl.venta.ventas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


    @Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customizarOpenApi(){
        return new OpenAPI()
        .info(new Info()
                .title("Documentación de la API de Ventas")
                .version("1.0.0")
                .description("Esta API permite gestionar ventas")
                .contact(new Contact()
                    .name("Equipo de Desarrollo BDLR-Developers"))
                   );
    }
    
}

