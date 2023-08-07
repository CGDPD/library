package com.cgdpd.library.repository;

import com.cgdpd.library.dto.book.copy.SearchBookCriteria;
import com.cgdpd.library.entity.AuthorEntity_;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.entity.BookEntity_;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {


    public static Specification<BookEntity> byBookSearchCriteria(SearchBookCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            criteria.bookTitle().ifPresent(title ->
                  predicates.add(
                        criteriaBuilder.like(
                              criteriaBuilder.upper(root.get(BookEntity_.title)),
                              "%" + title.toUpperCase() + "%")
                  )
            );

            criteria.authorName().ifPresent(author ->
                  predicates.add(
                        criteriaBuilder.like(
                              criteriaBuilder.upper(
                                    root.get(BookEntity_.authorEntity).get(AuthorEntity_.name)),
                              "%" + author.toUpperCase() + "%")
                  )
            );

            criteria.genre().ifPresent(genre ->
                  predicates.add(
                        criteriaBuilder.equal(
                              criteriaBuilder.upper(root.get(BookEntity_.genre)),
                              genre.toUpperCase())
                  )
            );

            criteria.publicationYearLessThan().ifPresent(year ->
                  predicates.add(
                        criteriaBuilder.lessThan(root
                              .get(BookEntity_.publicationYear), year))
            );

            criteria.publicationYearGreaterThan().ifPresent(year ->
                  predicates.add(
                        criteriaBuilder.greaterThan(root
                              .get(BookEntity_.publicationYear), year))
            );

            query.orderBy(criteriaBuilder.desc(root.get(BookEntity_.publicationYear)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

