package com.nurettinyakit.sandboxspringboot.gateway.controller;

import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.User;
import com.nurettinyakit.sandboxspringboot.usecase.UseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.nurettinyakit.sandboxspringboot.domain.ConstantsUtil.USER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UseCase<String, User> getUsers;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieves users.")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "500", description = "Oh oh. We've a problem. Sending the apes to solve the problem.", content = @Content)
    @ApiResponse(responseCode = "404", description = "This is not the user you're looking for...", content = @Content)
    public User welcome(@RequestHeader(USER_ID) final String customerId, @RequestParam String id) {
        log.info("Fetching user {} for the customer {}", id, customerId);
        return getUsers.execute(id);
    }
}
