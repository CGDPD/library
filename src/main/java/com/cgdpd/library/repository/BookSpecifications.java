package com.cgdpd.library.repository;

import com.cgdpd.library.dto.book.copy.SearchBookCriteria;
import com.cgdpd.library.entity.AuthorEntity_;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.entity.BookEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

    /**
     * Creates a JPA Specification based on the search criteria provided.
     *
     * @param criteria The criteria to search by.
     * @return A JPA Specification for filtering books.
     */
    public static Specification<BookEntity> byBookSearchCriteria(SearchBookCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            addPredicateIfValueIsPresent(
                  criteria.bookTitle(),
                  predicates,
                  like(criteriaBuilder, root.get(BookEntity_.title)));

            addPredicateIfValueIsPresent(
                  criteria.authorName(),
                  predicates,
                  like(criteriaBuilder,
                        root.get(BookEntity_.authorEntity).get(AuthorEntity_.name)));

            addPredicateIfValueIsPresent(
                  criteria.genre(),
                  predicates,
                  like(criteriaBuilder, root.get(BookEntity_.genre)));

            addPredicateIfValueIsPresent(
                  criteria.publicationYearLessThan(),
                  predicates,
                  lessThan(criteriaBuilder, root.get(BookEntity_.publicationYear)));

            addPredicateIfValueIsPresent(
                  criteria.publicationYearGreaterThan(),
                  predicates,
                  greaterThan(criteriaBuilder, root.get(BookEntity_.publicationYear)));

            query.orderBy(criteriaBuilder.desc(root.get(BookEntity_.publicationYear)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <T> void addPredicateIfValueIsPresent(Optional<T> value,
                                                         List<Predicate> predicates,
                                                         Function<T, Predicate> predicate) {
        value.ifPresent(it ->
              predicates.add(predicate.apply(it))
        );
    }

    private static Function<String, Predicate> like(CriteriaBuilder criteriaBuilder,
                                                    Expression<String> expression) {
        return (it) -> criteriaBuilder.like(
              criteriaBuilder.upper(expression),
              "%" + it.toUpperCase() + "%");
    }

    private static Function<Short, Predicate> lessThan(CriteriaBuilder criteriaBuilder,
                                                       Expression<Short> expression) {
        return (it) -> criteriaBuilder.lessThan(expression, it);
    }

    private static Function<Short, Predicate> greaterThan(CriteriaBuilder criteriaBuilder,
                                                          Expression<Short> expression) {
        return (it) -> criteriaBuilder.greaterThan(expression, it);
    }
}
