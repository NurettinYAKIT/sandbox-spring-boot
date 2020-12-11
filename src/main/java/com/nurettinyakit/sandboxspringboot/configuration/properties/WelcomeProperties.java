package com.nurettinyakit.sandboxspringboot.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties("app.gateway.welcome")
public class WelcomeProperties {

    @NotBlank
    private String baseUrl;
    private String path;
}
