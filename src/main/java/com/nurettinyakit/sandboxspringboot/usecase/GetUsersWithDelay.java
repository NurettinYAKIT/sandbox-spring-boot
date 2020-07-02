package com.nurettinyakit.sandboxspringboot.usecase;

import com.nurettinyakit.sandboxspringboot.gateway.reqres.ReqResGateway;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.UsersDelayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUsersWithDelay implements UseCase<String, UsersDelayResponse> {

    private final ReqResGateway gateway;

    @Override
    public UsersDelayResponse execute(final String delay) {
        return gateway.getDelayedUsers(delay);
    }
}
