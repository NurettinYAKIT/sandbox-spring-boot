package com.nurettinyakit.sandboxspringboot.gateway.controller;

import com.nurettinyakit.sandboxspringboot.gateway.reqres.ReqResGateway;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.User;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.UsersResponse;
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
    private final ReqResGateway gateway;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieves user.")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "500", description = "Oh oh. We've a problem. Sending the apes to solve the problem.", content = @Content)
    @ApiResponse(responseCode = "404", description = "This is not the user you're looking for...", content = @Content)
    public User getUser(@RequestHeader(USER_ID) final String customerId, @PathVariable String id) {
        log.info("Fetching user {} for the customer {}", id, customerId);
        return getUsers.execute(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieves users.")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UsersResponse.class)))
    @ApiResponse(responseCode = "500", description = "Oh oh. We've a problem. Sending the apes to solve the problem.", content = @Content)
    @ApiResponse(responseCode = "404", description = "These are not the users you're looking for...", content = @Content)
    public UsersResponse getUsers(@RequestHeader(USER_ID) final String customerId) {
        log.info("Fetching users for the customer {}", customerId);
        return gateway.getUsers();
    }
}
