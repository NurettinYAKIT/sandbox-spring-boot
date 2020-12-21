package com.nurettinyakit.sandboxspringboot.usecase;

import com.nurettinyakit.cleanarchitecture.usecase.UseCase;
import org.springframework.stereotype.Service;

@Service
public class GetWelcome implements UseCase<String, String> {
    @Override
    public String execute(String name) {
        return "Hello " + name;
    }
}
