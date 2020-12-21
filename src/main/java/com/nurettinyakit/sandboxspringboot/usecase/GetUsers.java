package com.nurettinyakit.sandboxspringboot.usecase;

import com.nurettinyakit.cleanarchitecture.usecase.UseCase;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.ReqResGateway;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUsers implements UseCase<String, User> {

    private final ReqResGateway gateway;

    @Override
    public User execute(final String id) {
        return gateway.getUser(id);
    }
}
