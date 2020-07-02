package com.nurettinyakit.sandboxspringboot.gateway.reqres.dto;

import lombok.Value;

import java.util.List;

@Value
public class UsersDelayResponse {

    Integer page;
    Integer perPage;
    Integer total;
    Integer totalPages;
    List<Data> data;
    Ad ad;

}
