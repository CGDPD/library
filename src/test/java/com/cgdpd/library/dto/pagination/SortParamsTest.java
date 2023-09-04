package com.cgdpd.library.dto.pagination;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cgdpd.library.exceptions.ValidationException;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

public class SortParamsTest {

    @Test
    void should_return_domain_sort_asc() {
        // given
        var sortParams = new SortParams("field", SortParams.Direction.ASC);

        // when
        var sort = sortParams.toDomainSort();

        // then
        assertThat(requireNonNull(sort.getOrderFor("field")).getDirection()).isEqualTo(
              Sort.Direction.ASC);
    }

    @Test
    void should_throw_exception_when_field_is_null() {
        // when/then
        assertThatThrownBy(() -> new SortParams(null, SortParams.Direction.ASC))
              .isInstanceOf(ValidationException.class)
              .hasMessage("field must not be null");
    }
}
