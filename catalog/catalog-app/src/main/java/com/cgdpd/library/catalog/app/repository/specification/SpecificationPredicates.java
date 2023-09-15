package com.cgdpd.library.catalog.app.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.util.function.Function;

public final class SpecificationPredicates {

    private SpecificationPredicates() {
    }

    public static Function<String, Predicate> like(CriteriaBuilder criteriaBuilder,
                                                   Expression<String> expression) {
        return (it) -> criteriaBuilder.like(
              criteriaBuilder.upper(expression),
              "%" + it.toUpperCase() + "%");
    }

    public static <T extends Comparable<? super T>> Function<T, Predicate> lessThan(
          CriteriaBuilder criteriaBuilder,
          Expression<T> expression) {
        return (it) -> criteriaBuilder.lessThan(expression, it);
    }

    public static <T extends Comparable<? super T>> Function<T, Predicate> greaterThan(
          CriteriaBuilder criteriaBuilder,
          Expression<T> expression) {
        return (it) -> criteriaBuilder.greaterThan(expression, it);
    }
}
