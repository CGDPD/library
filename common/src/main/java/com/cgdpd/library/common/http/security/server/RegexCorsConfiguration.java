package com.cgdpd.library.common.http.security.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexCorsConfiguration extends CorsConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(RegexCorsConfiguration.class);
    private static final String ERROR_MSG =
          "Wrong syntax for the origin '%s' as a regular expression.";
    private final List<Pattern> allowedOriginPatterns = new ArrayList<>();

    @Override
    public void addAllowedOrigin(String origin) {
        super.addAllowedOrigin(origin);
        try {
            allowedOriginPatterns.add(Pattern.compile(origin));
        } catch (final PatternSyntaxException e) {
            LOG.warn(String.format(ERROR_MSG, origin), e);
        }
    }

    @Override
    public String checkOrigin(String requestOrigin) {
        var result = checkOriginWithRegex(requestOrigin);
        return Optional.ofNullable(result).orElseGet(() -> super.checkOrigin(requestOrigin));
    }

    private String checkOriginWithRegex(String requestOrigin) {
        return allowedOriginPatterns.stream()
              .filter(pattern -> pattern.matcher(requestOrigin).matches())
              .map(pattern -> requestOrigin)
              .findFirst()
              .orElse(null);
    }
}
