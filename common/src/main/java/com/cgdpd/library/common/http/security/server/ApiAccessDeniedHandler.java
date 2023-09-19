package com.cgdpd.library.common.http.security.server;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.cgdpd.library.common.error.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.Clock;

public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    private final MappingJackson2HttpMessageConverter messageConverter;
    private final Clock clock;

    public ApiAccessDeniedHandler(MappingJackson2HttpMessageConverter messageConverter,
                                  Clock clock) {
        this.messageConverter = messageConverter;
        this.clock = clock;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        var httpStatus = FORBIDDEN;
        var errorMessage = "Access Denied. You don't have the required role to access this resource.";

        var errorResponse = ErrorResponse.builder()
              .status(httpStatus.value())
              .error(httpStatus.getReasonPhrase())
              .message(errorMessage)
              .timestamp(clock.instant())
              .build();

        var outputMessage = new ServletServerHttpResponse(response);
        outputMessage.setStatusCode(httpStatus);

        messageConverter.write(errorResponse, MediaType.APPLICATION_JSON, outputMessage);
    }
}
