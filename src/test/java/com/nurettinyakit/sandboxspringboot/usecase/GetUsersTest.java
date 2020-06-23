package com.nurettinyakit.sandboxspringboot.usecase;

import com.nurettinyakit.sandboxspringboot.gateway.reqres.ReqResGateway;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.Ad;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.Data;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersTest {
    @Mock
    private ReqResGateway reqResGateway;

    @InjectMocks
    private GetUsers getUsers;

    @Test
    void shouldReturnUsers() {
        //GIVEN
        final String id = "1";

        Data data = new Data(1, "email", "fistName", "lastName", "avatar");
        Ad ad = new Ad("company", "url", "text");
        User expected = new User(data, ad);
        //AND
        when(reqResGateway.getUsers(id)).thenReturn(expected);

        //WHEN
        User response = getUsers.execute(id);

        //THEN
        assertThat(response).isEqualTo(expected);
    }
}