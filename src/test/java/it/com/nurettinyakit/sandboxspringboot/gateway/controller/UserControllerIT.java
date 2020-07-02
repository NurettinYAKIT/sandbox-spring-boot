package it.com.nurettinyakit.sandboxspringboot.gateway.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.nurettinyakit.sandboxspringboot.SandboxSpringBootApplication;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.Ad;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.Data;
import com.nurettinyakit.sandboxspringboot.gateway.reqres.dto.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.nurettinyakit.sandboxspringboot.FileUtil.readFileAsString;
import static com.nurettinyakit.sandboxspringboot.domain.ConstantsUtil.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {SandboxSpringBootApplication.class})
@ActiveProfiles("integrationtest")
class UserControllerIT {

    private static final WireMockServer SERVER = new WireMockServer(8541);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeAll
    static void setUpOnce() {
        SERVER.start();
    }

    @AfterAll
    static void tearDownOnce() {
        SERVER.stop();
    }

    @BeforeEach
    void setUp() {
        SERVER.resetMappings();
    }

    @Test
    void shouldReturnUser() throws IOException {
        //GIVEN id
        final String id = "1";

        //AND expected
        final User expected = createExpectedUser();

        //AND stub
        SERVER.stubFor(get("/users/" + id)
                .willReturn(aResponse()
                        .withBody(readFileAsString("data/user-response.json"))
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(OK.value())));

        //WHEN calling our endpoint
        final ResponseEntity<User> response;
        try {
            response = testRestTemplate
                    .exchange("/users/{id}", GET, new HttpEntity<>(id, createHeaders()), User.class, id);
        } catch (final RestClientException ex) {
            fail(ex.getMessage());
            return;
        }

        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturnInternalServerError() throws IOException {
        //GIVEN id
        final String id = "1";

        //AND stub
        SERVER.stubFor(get("/users/" + id)
                .willReturn(aResponse()
                        .withBody(readFileAsString("data/user-response-invalid.json"))
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(INTERNAL_SERVER_ERROR.value())));

        //WHEN calling our endpoint
        final ResponseEntity<Map<String, Object>> response;
        try {
            response = testRestTemplate
                    .exchange("/users/{id}", GET, new HttpEntity<>(id, createHeaders()), new ParameterizedTypeReference<>() {
                    }, id);
        } catch (final RestClientException ex) {
            fail(ex.getMessage());
            return;
        }

        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        final Map<String, Object> errorAttributes = response.getBody();
        assertThat(errorAttributes).isNotNull();
        assertThat(errorAttributes).containsOnlyKeys("timestamp", "status", "error", "message", "path");
        assertThat(errorAttributes.get("status")).isEqualTo(INTERNAL_SERVER_ERROR.value());
        assertThat((String) errorAttributes.get("message")).contains("Something went wrong");
    }

    @Test
    void shouldReturnForbiddenError() throws IOException {
        //GIVEN id
        final String id = "1";

        //AND stub
        SERVER.stubFor(get("/users/" + id)
                .willReturn(aResponse()
                        .withBody(readFileAsString("data/user-response-invalid.json"))
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(FORBIDDEN.value())));

        //WHEN calling our endpoint
        final ResponseEntity<Map<String, Object>> response;
        try {
            response = testRestTemplate
                    .exchange("/users/{id}", GET, new HttpEntity<>(id, createHeaders()), new ParameterizedTypeReference<>() {
                    }, id);
        } catch (final RestClientException ex) {
            fail(ex.getMessage());
            return;
        }

        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
        final Map<String, Object> errorAttributes = response.getBody();
        assertThat(errorAttributes).isNotNull();
        assertThat(errorAttributes).containsOnlyKeys("timestamp", "status", "error", "message", "path");
        assertThat(errorAttributes.get("status")).isEqualTo(FORBIDDEN.value());
        assertThat((String) errorAttributes.get("message")).contains("You've found the forbidden apple!");
    }

    @Test
    void shouldReturnNotFoundError() throws IOException {
        //GIVEN id
        final String id = "1";

        //AND stub
        SERVER.stubFor(get("/users/" + id)
                .willReturn(aResponse()
                        .withBody(readFileAsString("data/user-response-invalid.json"))
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(NOT_FOUND.value())));

        //WHEN calling our endpoint
        final ResponseEntity<Map<String, Object>> response;
        try {
            response = testRestTemplate
                    .exchange("/users/{id}", GET, new HttpEntity<>(id, createHeaders()), new ParameterizedTypeReference<>() {
                    }, id);
        } catch (final RestClientException ex) {
            fail(ex.getMessage());
            return;
        }

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        final Map<String, Object> errorAttributes = response.getBody();
        assertThat(errorAttributes).isNotNull();
        assertThat(errorAttributes).containsOnlyKeys("timestamp", "status", "error", "message", "path");
        assertThat(errorAttributes.get("status")).isEqualTo(NOT_FOUND.value());
        assertThat((String) errorAttributes.get("message")).contains("User with the id : " + id + " not found.");
    }

    @Test
    void shouldReturnBadRequestError() throws IOException {
        //GIVEN id
        final String id = "1";

        //AND stub
        SERVER.stubFor(get("/users/" + id)
                .willReturn(aResponse()
                        .withBody(readFileAsString("data/user-response-bad-request.json"))
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(BAD_REQUEST.value())));

        //WHEN calling our endpoint
        final ResponseEntity<Map<String, Object>> response;
        try {
            response = testRestTemplate
                    .exchange("/users/{id}", GET, new HttpEntity<>(id, createHeaders()), new ParameterizedTypeReference<>() {
                    }, id);
        } catch (final RestClientException ex) {
            fail(ex.getMessage());
            return;
        }

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        final Map<String, Object> errorAttributes = response.getBody();
        assertThat(errorAttributes).isNotNull();
        assertThat(errorAttributes).containsOnlyKeys("timestamp", "status", "error", "message", "path");
        assertThat(errorAttributes.get("status")).isEqualTo(BAD_REQUEST.value());
    }

    private User createExpectedUser() {
        final Data data = new Data(1, "george.bluth@reqres.in", "George", "Bluth", "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
        final Ad ad = new Ad("StatusCode Weekly", "http://statuscode.org/", "A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.");
        return new User(data, ad);
    }

    private HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(USER_ID, "1234");
        return headers;
    }
}