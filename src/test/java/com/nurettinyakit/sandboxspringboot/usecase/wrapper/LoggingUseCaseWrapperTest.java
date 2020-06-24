package com.nurettinyakit.sandboxspringboot.usecase.wrapper;

import com.nurettinyakit.sandboxspringboot.usecase.UseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingUseCaseWrapperTest {

    @Mock
    private UseCase<String, Void> useCase;

    @InjectMocks
    private LoggingUseCaseWrapper<String, Void> wrapper;

    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutContent;

    @BeforeEach
    void redirectSystemOutStream() {
        originalSystemOut = System.out;
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }

    @Test
    void shouldLogUseCaseAndParam() {
        String request = "Request";

        wrapper.execute(request);

        String consoleOutput = systemOutContent.toString();
        assertThat(consoleOutput).contains("Executing usecase for String parameters", "Finished executing usecase for String");
    }

    @Test
    void shouldLogEvenExceptionOccurs() {
        String request = "Request";

        when(useCase.execute(any())).thenThrow(new RuntimeException("Houston"));

        assertThatThrownBy(() -> wrapper.execute(request)).isInstanceOf(RuntimeException.class);

        String consoleOutput = systemOutContent.toString();
        assertThat(consoleOutput).contains("Executing usecase for String parameters Request");
        assertThat(consoleOutput).contains("Finished executing usecase for String");
    }

}