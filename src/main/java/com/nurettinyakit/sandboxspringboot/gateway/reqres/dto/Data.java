package com.nurettinyakit.sandboxspringboot.gateway.reqres.dto;

import lombok.Value;

@Value
public class Data {
    Integer id;
    String email;
    String firstName;
    String lastName;
    String avatar;
}
