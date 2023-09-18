package com.cgdpd.library.common.http.security.server;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.cgdpd.library.common.error.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.Clock;

public class ApiAuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final MappingJackson2HttpMessageConverter messageConverter;
    private final Clock clock;

    public ApiAuthenticationFailureHandler(MappingJackson2HttpMessageConverter messageConverter,
                                           Clock clock) {
        this.messageConverter = messageConverter;
        this.clock = clock;
    }

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException exception) throws IOException {
        var httpStatus = UNAUTHORIZED;
        var errorMessage = "Invalid authentication credentials provided. Please check your key and secret.";

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Invalid authentication credentials provided. Username not found.";
        }

        var errorResponse = ErrorResponse.builder()
              .status(httpStatus.value())
              .error(httpStatus.getReasonPhrase())
              .message(errorMessage)
              .timestamp(clock.instant())
              .build();

        final ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        outputMessage.setStatusCode(httpStatus);

        messageConverter.write(errorResponse, MediaType.APPLICATION_JSON, outputMessage);
    }
}
