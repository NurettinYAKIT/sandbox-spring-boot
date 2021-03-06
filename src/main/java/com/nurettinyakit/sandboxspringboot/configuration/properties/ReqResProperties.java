package com.nurettinyakit.sandboxspringboot.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties("app.gateway.req-res")
public class ReqResProperties {

    @NotBlank
    private String baseUrl;

    @NotBlank
    @Value("${users:/users}")
    private String usersPath;

}
