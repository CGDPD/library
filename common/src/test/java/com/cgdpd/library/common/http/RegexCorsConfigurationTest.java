package com.cgdpd.library.common.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class RegexCorsConfigurationTest {

    private final RegexCorsConfiguration configuration = new RegexCorsConfiguration();

    static Stream<Arguments> validValuesForRegex() {
        return Stream.of(
              Arguments.of("http://localhost(:[0-9]+)?", "http://localhost"),
              Arguments.of("http://localhost(:[0-9]+)?", "http://localhost:8080"));
    }

    static Stream<Arguments> invalidValuesForRegex() {
        return Stream.of(
              Arguments.of("http://localhost(:[0-9]+)?", "http://somewhereelse.com"),
              Arguments.of("http://localhost(:[0-9]+)?", "http://localhost:abc"));
    }

    @ParameterizedTest
    @MethodSource("validValuesForRegex")
    void should_return_origin_when_it_matches(String originRegex, String requestOrigin) {
        // given
        configuration.addAllowedOrigin(originRegex);

        // when
        var result = configuration.checkOrigin(requestOrigin);

        // then
        assertThat(result).isNotNull();
        assertThat(configuration.checkOrigin("http://somewhereelse.com")).isNull();
    }

    @ParameterizedTest
    @MethodSource("invalidValuesForRegex")
    void should_return_null_when_it_doesnt_match(String originRegex, String requestOrigin) {
        // given
        configuration.addAllowedOrigin(originRegex);

        // when
        var result = configuration.checkOrigin(requestOrigin);

        // then
        assertThat(result).isNull();
    }

    @Test
    void should_still_add_origin_if_it_fails_to_compile_regex() {
        // given
        var originInvalidRegex = "http://localhost([";
        configuration.addAllowedOrigin(originInvalidRegex);

        // when
        var result = configuration.checkOrigin(originInvalidRegex);

        // then
        assertThat(result).isNotNull();
    }
}
