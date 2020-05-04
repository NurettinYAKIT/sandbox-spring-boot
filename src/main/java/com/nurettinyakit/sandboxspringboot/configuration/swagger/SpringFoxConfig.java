package com.nurettinyakit.sandboxspringboot.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    @Profile("local")
    public Docket dev() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/*.*"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Sandbox Rest API")
                        .description("Local Env")
                        .build())
                ;
    }

    @Bean
    @Profile("!local")
    public Docket test() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/*.*"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Sandbox Rest API")
                        .description("!local Env")
                        .build())
                ;
    }

}
