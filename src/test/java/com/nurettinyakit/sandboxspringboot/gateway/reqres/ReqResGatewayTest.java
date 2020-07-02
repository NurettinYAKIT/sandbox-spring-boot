package com.nurettinyakit.sandboxspringboot.gateway.reqres;

import com.nurettinyakit.sandboxspringboot.configuration.properties.ReqResProperties;
import com.nurettinyakit.sandboxspringboot.domain.exception.UserNotFoundException;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.Ad;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.Data;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class ReqResGatewayTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ReqResProperties properties;

    @InjectMocks
    private ReqResGateway gateway;

    @BeforeEach
    void setUp() {
        when(properties.getBaseUrl()).thenReturn("https://base.url");
        when(properties.getUsersPath()).thenReturn("/users/{id}");
    }

    @Test
    void shouldReturnUser() {
        final String id = "1";

        Data data = new Data(1, "email", "fistName", "lastName", "avatar");
        Ad ad = new Ad("company", "url", "text");
        User expected = new User(data, ad);

        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(User.class), eq(id)))
                .thenReturn(ResponseEntity.of(Optional.of(expected)));

        assertThat(gateway.getUser(id)).isEqualTo(expected);
    }

    @Test
    void shouldThrowIllegalStateException() {
        final String id = "1";

        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(User.class), eq(id)))
                .thenThrow(new RestClientException("Rest Exception"));

        assertThatThrownBy(() -> gateway.getUser(id))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldThrowValidationExceptionWhenBadRequest() {
        final String id = "1";

        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(User.class), eq(id)))
                .thenThrow(new HttpClientErrorException(BAD_REQUEST));

        assertThatThrownBy(() -> gateway.getUser(id))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void shouldThrowValidationExceptionWhenUnauthorized() {
        final String id = "1";

        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(User.class), eq(id)))
                .thenThrow(new HttpClientErrorException(UNAUTHORIZED));

        assertThatThrownBy(() -> gateway.getUser(id))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void shouldThrowIllegalCallerExceptionWhenForbidden() {
        final String id = "1";

        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(User.class), eq(id)))
                .thenThrow(new HttpClientErrorException(FORBIDDEN));

        assertThatThrownBy(() -> gateway.getUser(id))
                .isInstanceOf(IllegalCallerException.class);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenNotFound() {
        final String id = "1";

        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(User.class), eq(id)))
                .thenThrow(new HttpClientErrorException(NOT_FOUND));

        assertThatThrownBy(() -> gateway.getUser(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenStatusUnexpected() {
        final String id = "1";

        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(User.class), eq(id)))
                .thenThrow(new HttpClientErrorException(PAYMENT_REQUIRED));

        assertThatThrownBy(() -> gateway.getUser(id))
                .isInstanceOf(ValidationException.class);
    }

}