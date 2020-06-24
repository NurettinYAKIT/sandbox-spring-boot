package com.nurettinyakit.sandboxspringboot.gateway.controller;

import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.User;
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
public class UserController {

    private final UseCase<String, User> getUsers;

    @GetMapping(value = "users")
    public User welcome(@RequestHeader(USER_ID) final String customerId, @RequestParam String id) {
        log.info("Fetching user {} for the customer {}", id, customerId);
        return getUsers.execute(id);
    }
}
