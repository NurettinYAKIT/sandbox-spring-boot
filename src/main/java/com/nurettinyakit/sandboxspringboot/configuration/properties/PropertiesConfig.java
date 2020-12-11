package com.nurettinyakit.sandboxspringboot.configuration.properties;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ReqResProperties.class, WelcomeProperties.class})
public class PropertiesConfig {
}
