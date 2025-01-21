package edu.example.springbootblog.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v1.0.0")
                .title("화석연료 API")
                .description("화석연료 API 목록입니다.")
                .contact(new Contact()
                        .name("Admin")
                        .email("adorahelenmin@gmail.com")
                        .url("https://fossilfuel.site"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        return new OpenAPI().info(info);
    }
}