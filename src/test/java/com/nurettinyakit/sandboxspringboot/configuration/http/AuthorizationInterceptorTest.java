package com.nurettinyakit.sandboxspringboot.configuration.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.nurettinyakit.sandboxspringboot.domain.ConstantsUtil.USER_ID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationInterceptorTest {

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;

    private final AuthorizationInterceptor interceptor = new AuthorizationInterceptor();

    @Test
    void shouldAllowIfUserAllowedUser() throws IOException {
        when(request.getRequestURI()).thenReturn("localhost");
        when(request.getHeader(USER_ID)).thenReturn("1234");

        assertTrue(interceptor.preHandle(request, response, null));
        verify(request, times(2)).getHeader(eq(USER_ID));
        verifyNoInteractions(response);
    }

    @Test
    void shouldAllowIfSwagger() throws IOException {
        when(request.getRequestURI()).thenReturn("swagger");

        assertTrue(interceptor.preHandle(request, response, null));
        verifyNoInteractions(response);
    }

    @Test
    void shouldRedirectIfUserIdNull() throws IOException {
        when(request.getHeader(USER_ID)).thenReturn(null);
        when(request.getRequestURI()).thenReturn("localhost");

        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).sendRedirect("https://www.google.com");
        verify(request).getHeader(eq(USER_ID));
        verifyNoMoreInteractions(response, request);
    }

    @Test
    void shouldRedirectIfUserNotInAllowedUsers() throws IOException {
        when(request.getHeader(USER_ID)).thenReturn("xxxx");
        when(request.getRequestURI()).thenReturn("localhost");

        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).sendRedirect("https://www.google.com");
        verify(request, times(2)).getHeader(eq(USER_ID));
        verifyNoMoreInteractions(response, request);
    }

}