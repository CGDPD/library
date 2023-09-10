package com.cgdpd.library.dto.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cgdpd.library.exceptions.ValidationException;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

public class SortParamsTest {

    @Test
    void should_convert_to_domain_sort_asc() {
        // given
        var field = "field";
        var direction = SortParams.Direction.ASC;
        var sortParams = new SortParams(field, direction);

        // when
        var resultDomainSort = sortParams.toDomainSort();

        // then
        assertThat(resultDomainSort.getOrderFor(field).getDirection()).isEqualTo(
              Sort.Direction.ASC);
    }

    @Test
    void should_throw_exception_when_field_is_null() {
        // when then
        assertThatThrownBy(() -> new SortParams(null, SortParams.Direction.ASC))
              .isInstanceOf(ValidationException.class)
              .hasMessage("field must not be null");
    }
}
