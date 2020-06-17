package com.nurettinyakit.sandboxspringboot.configuration.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties("app.gateway.req-res")
public class ReqResProperties {

    @NotBlank
    private String baseUrl;

    @NotBlank
    @Value("${users:/users/{id}}")
    private String usersPath;

}
