package com.nurettinyakit.sandboxspringboot.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GetWelcomeTest {

    GetWelcome welcome = new GetWelcome();

    @Test
    void shouldReturnHello() {
        final String name = "John Doe";

        final String expected = "Hello " + name;
        assertThat(welcome.execute(name)).isEqualTo(expected);
    }

}