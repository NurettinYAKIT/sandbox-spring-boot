package com.nurettinyakit.sandboxspringboot.gateway.reqres;

import com.nurettinyakit.sandboxspringboot.configuration.properties.ReqResProperties;
import com.nurettinyakit.sandboxspringboot.domain.exception.UserNotFoundException;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.ValidationException;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReqResGateway {

    private final RestTemplate restTemplate;
    private final ReqResProperties properties;

    public User getUsers(final String id) {
        final User response;
        try {
            response = restTemplate
                    .exchange(properties.getBaseUrl() + properties.getUsersPath(),
                            GET,
                            new HttpEntity<>(createHeaders()),
                            User.class, id).getBody();
        } catch (final HttpClientErrorException ex) {
            switch (ex.getStatusCode()) {
                case FORBIDDEN -> throw new IllegalCallerException("You've found the forbidden apple! Sorry it's forbidden for you.");
                case NOT_FOUND -> throw new UserNotFoundException(id);
                default -> throw new ValidationException(ex.getMessage());
            }
        } catch (final RestClientException ex) {
            log.warn("Problem occurred while getting user with id {}", id, ex);
            throw new IllegalStateException(ex.getMessage());
        }
        return response;
    }

    private HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
