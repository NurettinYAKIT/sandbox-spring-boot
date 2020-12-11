package com.nurettinyakit.sandboxspringboot.gateway.reqres.dto;

import lombok.Value;

import java.util.List;

@Value
public class UsersResponse {
    Integer page;
    Integer perPage;
    Integer total;
    Integer totalPages;
    List<Data> data = null;
    Support support;
}
