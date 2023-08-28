package com.cgdpd.library;

import static com.cgdpd.library.BookTestData.CHRIS_DANE__ONCE_AGAIN__2022;
import static com.cgdpd.library.BookTestData.COLUMBUS_CHRISTOPHER__IN_LOVE__2011;
import static com.cgdpd.library.BookTestData.JANE_DANE__KILLER__2001;
import static com.cgdpd.library.BookTestData.JANE_DANE__SURVIVOR__2007;
import static com.cgdpd.library.BookTestData.JOHN_DOE__FINDER__1995;
import static com.cgdpd.library.BookTestData.JOHN_DOE__THE_ADVENTUROUS__1987;
import static com.cgdpd.library.BookTestData.aBookEntity;
import static com.cgdpd.library.UserTestData.USER_LOLA_ROGER;
import static com.cgdpd.library.UserTestData.USER_LOUIS_CASTRO;
import static com.cgdpd.library.UserTestData.aUserEntity;
import static com.cgdpd.library.model.book.copy.TrackingStatus.AVAILABLE;
import static com.cgdpd.library.model.book.copy.TrackingStatus.BEING_PROCESSED;
import static com.cgdpd.library.model.book.copy.TrackingStatus.CHECKED_OUT;
import static com.cgdpd.library.model.book.copy.TrackingStatus.LOST;
import static com.cgdpd.library.model.book.copy.TrackingStatus.ON_HOLD;
import static com.cgdpd.library.model.book.copy.TrackingStatus.REFERENCE;
import static com.cgdpd.library.model.book.copy.TrackingStatus.RETIRED;

import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.model.book.copy.BookCopy;
import com.cgdpd.library.type.BookCopyId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.UserId;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class BookCopyTestData {

    private static final AtomicLong bookCopyId = new AtomicLong(1L);
    public static final BookCopyEntity JOHN_DOE__THE_ADVENTUROUS_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__THE_ADVENTUROUS__1987)
          .trackingStatus(CHECKED_OUT.name())
          .userEntity(USER_LOLA_ROGER)
          .build();
    public static final BookCopyEntity JOHN_DOE__THE_ADVENTUROUS_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__THE_ADVENTUROUS__1987)
          .trackingStatus(AVAILABLE.name())
          .build();

    public static final BookCopyEntity JOHN_DOE__FINDER_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__FINDER__1995)
          .trackingStatus(BEING_PROCESSED.name())
          .build();
    public static final BookCopyEntity JOHN_DOE__FINDER_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__FINDER__1995)
          .trackingStatus(LOST.name())
          .build();

    public static final BookCopyEntity JANE_DANE__KILLER_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JANE_DANE__KILLER__2001)
          .trackingStatus(AVAILABLE.name())
          .build();
    public static final BookCopyEntity JANE_DANE__KILLER_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JANE_DANE__KILLER__2001)
          .trackingStatus(LOST.name())
          .userEntity(USER_LOLA_ROGER)
          .build();

    public static final BookCopyEntity JANE_DANE__SURVIVOR_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JANE_DANE__SURVIVOR__2007)
          .trackingStatus(REFERENCE.name())
          .build();


    public static final BookCopyEntity CHRIS_DANE__ONCE_AGAIN_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(CHRIS_DANE__ONCE_AGAIN__2022)
          .trackingStatus(ON_HOLD.name())
          .userEntity(USER_LOUIS_CASTRO)
          .build();

    public static final BookCopyEntity CHRIS_DANE__ONCE_AGAIN_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(CHRIS_DANE__ONCE_AGAIN__2022)
          .trackingStatus(RETIRED.name())
          .build();


    public static final BookCopyEntity COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(COLUMBUS_CHRISTOPHER__IN_LOVE__2011)
          .trackingStatus(CHECKED_OUT.name())
          .userEntity(USER_LOUIS_CASTRO)
          .build();

    public static final BookCopyEntity COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(COLUMBUS_CHRISTOPHER__IN_LOVE__2011)
          .trackingStatus(AVAILABLE.name())
          .build();

    public static final BookCopyEntity COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_3 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(COLUMBUS_CHRISTOPHER__IN_LOVE__2011)
          .trackingStatus(AVAILABLE.name())
          .build();

    public static BookCopy.BookCopyBuilder aBookCopy() {
        return BookCopy.builder()
              .id(BookCopyId.of(1L))
              .bookId(BookId.of(1L))
              .trackingStatus(CHECKED_OUT)
              .userId(Optional.of(UserId.of(1L)));
    }

    public static BookCopyEntity.BookCopyEntityBuilder aBookCopyEntity() {
        return BookCopyEntity.builder()
              .id(1L)
              .bookEntity(aBookEntity().build())
              .trackingStatus(CHECKED_OUT.name())
              .userEntity(aUserEntity().build());
    }
}
