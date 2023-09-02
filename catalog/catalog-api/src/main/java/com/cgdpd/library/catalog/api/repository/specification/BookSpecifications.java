package com.cgdpd.library.catalog.api.repository.specification;

import static com.cgdpd.library.catalog.api.repository.specification.SpecificationPredicates.greaterThan;
import static com.cgdpd.library.catalog.api.repository.specification.SpecificationPredicates.lessThan;
import static com.cgdpd.library.catalog.api.repository.specification.SpecificationPredicates.like;

import com.cgdpd.library.catalog.api.entity.AuthorEntity_;
import com.cgdpd.library.catalog.api.entity.BookEntity;

import com.cgdpd.library.catalog.api.entity.BookEntity_;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;

import jakarta.persistence.criteria.Predicate;
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
            var predicates = new Predicates();

            predicates.addPredicateIfValueIsPresent(
                  like(criteriaBuilder, root.get(BookEntity_.title)),
                  criteria.bookTitle());

            predicates.addPredicateIfValueIsPresent(
                  like(criteriaBuilder,
                        root.get(BookEntity_.authorEntity).get(AuthorEntity_.name)),
                  criteria.authorName());

            predicates.addPredicateIfValueIsPresent(
                  like(criteriaBuilder, root.get(BookEntity_.genre)),
                  criteria.genre());

            predicates.addPredicateIfValueIsPresent(
                  lessThan(criteriaBuilder, root.get(BookEntity_.publicationYear)),
                  criteria.publicationYearLessThan());

            predicates.addPredicateIfValueIsPresent(
                  greaterThan(criteriaBuilder, root.get(BookEntity_.publicationYear)),
                  criteria.publicationYearGreaterThan());

            query.orderBy(criteriaBuilder.desc(root.get(BookEntity_.publicationYear)));

            return criteriaBuilder.and(predicates.value().toArray(new Predicate[0]));
        };
    }
}
