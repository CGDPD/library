package com.cgdpd.library;

import static com.cgdpd.library.AuthorTestData.AUTHOR_CHRIS_DANE;
import static com.cgdpd.library.AuthorTestData.AUTHOR_COLUMBUS_CHRISTOPHER;
import static com.cgdpd.library.AuthorTestData.AUTHOR_JANE_DANE;
import static com.cgdpd.library.AuthorTestData.AUTHOR_JOHN_DOE;
import static com.cgdpd.library.BookCopyTestData.CHRIS_DANE__ONCE_AGAIN_COPY_1;
import static com.cgdpd.library.BookCopyTestData.CHRIS_DANE__ONCE_AGAIN_COPY_2;
import static com.cgdpd.library.BookCopyTestData.COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_1;
import static com.cgdpd.library.BookCopyTestData.COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_2;
import static com.cgdpd.library.BookCopyTestData.COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_3;
import static com.cgdpd.library.BookCopyTestData.JANE_DANE__KILLER_COPY_1;
import static com.cgdpd.library.BookCopyTestData.JANE_DANE__KILLER_COPY_2;
import static com.cgdpd.library.BookCopyTestData.JANE_DANE__SURVIVOR_COPY_1;
import static com.cgdpd.library.BookCopyTestData.JOHN_DOE__FINDER_COPY_1;
import static com.cgdpd.library.BookCopyTestData.JOHN_DOE__FINDER_COPY_2;
import static com.cgdpd.library.BookCopyTestData.JOHN_DOE__THE_ADVENTUROUS_COPY_1;
import static com.cgdpd.library.BookCopyTestData.JOHN_DOE__THE_ADVENTUROUS_COPY_2;
import static com.cgdpd.library.BookTestData.CHRIS_DANE__ONCE_AGAIN;
import static com.cgdpd.library.BookTestData.COLUMBUS_CHRISTOPHER__IN_LOVE;
import static com.cgdpd.library.BookTestData.JANE_DANE__KILLER;
import static com.cgdpd.library.BookTestData.JANE_DANE__SURVIVOR;
import static com.cgdpd.library.BookTestData.JOHN_DOE__FINDER;
import static com.cgdpd.library.BookTestData.JOHN_DOE__THE_ADVENTUROUS;
import static com.cgdpd.library.UserTestData.USER_LOLA_ROGER;
import static com.cgdpd.library.UserTestData.USER_LOUIS_CASTRO;

import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.entity.UserEntity;
import java.util.List;
import java.util.stream.Stream;

public class TestData {

    public static List<UserEntity> users() {
        return List.of(
              USER_LOLA_ROGER,
              USER_LOUIS_CASTRO);
    }

    public static List<AuthorEntity> authors() {
        return List.of(
              AUTHOR_JOHN_DOE,
              AUTHOR_JANE_DANE,
              AUTHOR_CHRIS_DANE,
              AUTHOR_COLUMBUS_CHRISTOPHER);
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

    public static List<BookEntity> johnDoeBooks() {
        return List.of(
              JOHN_DOE__THE_ADVENTUROUS,
              JOHN_DOE__FINDER);
    }

    public static List<BookEntity> janeDaneBooks() {
        return List.of(
              JANE_DANE__KILLER,
              JANE_DANE__SURVIVOR);
    }

    public static List<BookEntity> chrisDaneBooks() {
        return List.of(CHRIS_DANE__ONCE_AGAIN);
    }

    public static List<BookEntity> columbusChristopherBooks() {
        return List.of(COLUMBUS_CHRISTOPHER__IN_LOVE);
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
              JOHN_DOE__THE_ADVENTUROUS_COPY_1,
              JOHN_DOE__THE_ADVENTUROUS_COPY_2,
              JOHN_DOE__FINDER_COPY_1,
              JOHN_DOE__FINDER_COPY_2);
    }

    public static List<BookCopyEntity> janeDaneBookCopies() {
        return List.of(
              JANE_DANE__KILLER_COPY_1,
              JANE_DANE__KILLER_COPY_2,
              JANE_DANE__SURVIVOR_COPY_1);
    }

    public static List<BookCopyEntity> chrisDaneBookCopies() {
        return List.of(
              CHRIS_DANE__ONCE_AGAIN_COPY_1,
              CHRIS_DANE__ONCE_AGAIN_COPY_2);
    }

    public static List<BookCopyEntity> columbusChristopherBookCopies() {
        return List.of(
              COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_1,
              COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_2,
              COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_3);
    }
}
