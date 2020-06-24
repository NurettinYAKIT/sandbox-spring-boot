package com.nurettinyakit.sandboxspringboot.configuration.http;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.nurettinyakit.sandboxspringboot.domain.ConstantsUtil.USER_ID;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String GOOGLE_COM = "https://www.google.com";

    private final List<String> allowedUsers = Arrays.asList(
            "1234", "123", "1"
    );

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws IOException {

        if (request.getRequestURI().contains("swagger")
                || (Objects.nonNull(request.getHeader(USER_ID)) && allowedUsers.contains(request.getHeader(USER_ID)))) {
            return true;
        }
        response.sendRedirect(GOOGLE_COM);
        return false;
    }
}
