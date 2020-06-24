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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.nurettinyakit.sandboxspringboot.FileUtil.readBodyFromFile;
import static com.nurettinyakit.sandboxspringboot.domain.ConstantsUtil.CUSTOMER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
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
                        .withBody(readBodyFromFile("data/user-response.json"))
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(OK.value())));

        //WHEN calling our endpoint
        final ResponseEntity<User> response;
        try {
            response = testRestTemplate
                    .exchange("/users?id={id}", GET, new HttpEntity<>(id, createHeaders()), User.class, id);
        } catch (final RestClientException ex) {
            fail(ex.getMessage());
            return;
        }

        assertThat(response.getBody()).isEqualTo(expected);

    }

    private User createExpectedUser() {
        final Data data = new Data(1, "george.bluth@reqres.in", "George", "Bluth", "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
        final Ad ad = new Ad("StatusCode Weekly", "http://statuscode.org/", "A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.");
        return new User(data, ad);
    }

    private HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(CUSTOMER_ID, "1234");
        return headers;
    }
}