package com.cgdpd.library.catalog.app;

import com.cgdpd.library.catalog.app.entity.AuthorEntity;
import com.cgdpd.library.catalog.app.entity.BookCopyEntity;
import com.cgdpd.library.catalog.app.entity.BookEntity;

import java.util.List;
import java.util.stream.Stream;

public class TestData {

    public static List<AuthorEntity> authors() {
        return List.of(
              AuthorTestData.AUTHOR_JOHN_DOE,
              AuthorTestData.AUTHOR_JANE_DANE,
              AuthorTestData.AUTHOR_CHRIS_DANE,
              AuthorTestData.AUTHOR_COLUMBUS_CHRISTOPHER);
    }

    public static List<BookEntity> books() {
        return Stream.of(
                    johnDoeBooks(),
                    janeDaneBooks(),
                    chrisDaneBooks(),
                    columbusChristopherBooks())
              .flatMap(List::stream)
              .toList();
    }

    public static List<BookEntity> booksByTitleCriteria(String bookTitleCriteria) {
        return books().stream()
              .filter(bookEntity -> containsIgnoreCase(bookEntity.getTitle(), bookTitleCriteria))
              .toList();
    }

    public static List<BookEntity> johnDoeBooks() {
        return List.of(
              BookEntityTestData.JOHN_DOE__THE_ADVENTUROUS__1987,
              BookEntityTestData.JOHN_DOE__FINDER__1995);
    }

    public static List<BookEntity> janeDaneBooks() {
        return List.of(
              BookEntityTestData.JANE_DANE__KILLER__2001,
              BookEntityTestData.JANE_DANE__SURVIVOR__2007);
    }

    public static List<BookEntity> chrisDaneBooks() {
        return List.of(BookEntityTestData.CHRIS_DANE__ONCE_AGAIN__2022);
    }

    public static List<BookEntity> columbusChristopherBooks() {
        return List.of(BookEntityTestData.COLUMBUS_CHRISTOPHER__IN_LOVE__2011);
    }

    public static List<BookEntity> booksByAuthorNameCriteria(String authorNameCriteria) {
        return books().stream()
              .filter(bookEntity -> containsIgnoreCase(bookEntity.getAuthorEntity().getName(),
                    authorNameCriteria))
              .toList();
    }

    public static List<BookEntity> booksByGenreCriteria(String genreCriteria) {
        return books().stream()
              .filter(bookEntity -> bookEntity.getGenre().equalsIgnoreCase(genreCriteria))
              .toList();
    }

    public static List<BookEntity> booksYoungerThan(short year) {
        return books().stream()
              .filter(bookEntity -> bookEntity.getPublicationYear() < year)
              .toList();
    }

    public static List<BookEntity> booksOlderThan(short year) {
        return books().stream()
              .filter(bookEntity -> bookEntity.getPublicationYear() > year)
              .toList();
    }


    public static List<BookCopyEntity> bookCopies() {
        return Stream.of(
                    johnDoeBookCopies(),
                    janeDaneBookCopies(),
                    chrisDaneBookCopies(),
                    columbusChristopherBookCopies())
              .flatMap(List::stream)
              .toList();
    }

    public static List<BookCopyEntity> johnDoeBookCopies() {
        return List.of(
              BookCopyEntityTestData.JOHN_DOE__THE_ADVENTUROUS_COPY_1,
              BookCopyEntityTestData.JOHN_DOE__THE_ADVENTUROUS_COPY_2,
              BookCopyEntityTestData.JOHN_DOE__FINDER_COPY_1,
              BookCopyEntityTestData.JOHN_DOE__FINDER_COPY_2);
    }

    public static List<BookCopyEntity> janeDaneBookCopies() {
        return List.of(
              BookCopyEntityTestData.JANE_DANE__KILLER_COPY_1,
              BookCopyEntityTestData.JANE_DANE__KILLER_COPY_2,
              BookCopyEntityTestData.JANE_DANE__SURVIVOR_COPY_1);
    }

    public static List<BookCopyEntity> chrisDaneBookCopies() {
        return List.of(
              BookCopyEntityTestData.CHRIS_DANE__ONCE_AGAIN_COPY_1,
              BookCopyEntityTestData.CHRIS_DANE__ONCE_AGAIN_COPY_2);
    }

    public static List<BookCopyEntity> columbusChristopherBookCopies() {
        return List.of(
              BookCopyEntityTestData.COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_1,
              BookCopyEntityTestData.COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_2,
              BookCopyEntityTestData.COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_3);
    }

    private static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toUpperCase().contains(s2.toUpperCase());
    }
}
