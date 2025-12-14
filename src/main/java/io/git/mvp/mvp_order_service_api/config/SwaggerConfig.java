package io.git.mvp.mvp_order_service_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Order Service API - Serviço principal do MVP")
                                .version("1.0")
                                .description("Swagger do projeto principal do MVP para a Pós Engenharia de Software.")
                );
    }
}
