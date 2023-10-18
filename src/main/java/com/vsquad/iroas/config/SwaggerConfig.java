package com.vsquad.iroas.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "\uD83C\uDF31iroas\uD83C\uDF31",
                description = "iroas😁 \n" +
                        "\n배포링크 👉 [클릭](http://localhost:8001/)\n" +
                        "\n👀Swagger 사용 방법👀 \n" +
                        "1. 첫번째 \n" +
                        "2. 두번째 \n" +
                        "3. 세번째 \n" +
                        "\n💡설명💡",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
        @Bean
        public GroupedOpenApi firstOpenApi() {
                String[] paths = {
                        "com.sixesSense.recorder"
                };

                return GroupedOpenApi
                        .builder()
                        .group("식스센스 스웨거")
                        .packagesToScan(paths)
                        .build();
        }
}
