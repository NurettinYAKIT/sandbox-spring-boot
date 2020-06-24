package com.nurettinyakit.sandboxspringboot.gateway.controller;

import com.nurettinyakit.sandboxspringboot.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.nurettinyakit.sandboxspringboot.domain.ConstantsUtil.USER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WelcomeController {

    private final UseCase<String, String> getWelcome;

    @GetMapping(value = "welcome")
    public String welcome(@RequestHeader(USER_ID) final String customerId, @RequestParam String name) {
        log.info("Welcoming Customer {}", customerId);
        return getWelcome.execute(name);
    }
}
