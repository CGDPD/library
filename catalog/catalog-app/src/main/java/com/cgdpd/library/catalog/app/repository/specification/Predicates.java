package com.cgdpd.library.catalog.app.repository.specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class Predicates {

    private final List<Predicate> predicates;

    public Predicates() {
        this.predicates = new ArrayList<>();
    }

    public <T> void addPredicateIfValueIsPresent(Function<T, Predicate> predicate,
                                                 Optional<T> value) {
        value.ifPresent(it -> addPredicate(predicate, it));
    }

    public <T> void addPredicate(Function<T, Predicate> predicate, T value) {
        predicates.add(predicate.apply(value));
    }

    public List<Predicate> value() {
        return List.copyOf(predicates);
    }
}
