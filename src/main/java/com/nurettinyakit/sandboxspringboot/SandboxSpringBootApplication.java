package com.nurettinyakit.sandboxspringboot;

import com.nurettinyakit.sandboxspringboot.configuration.properties.ReqResProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ReqResProperties.class)
public class SandboxSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SandboxSpringBootApplication.class, args);
    }

}
