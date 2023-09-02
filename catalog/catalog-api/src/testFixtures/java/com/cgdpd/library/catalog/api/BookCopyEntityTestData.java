package com.cgdpd.library.catalog.api;

import static com.cgdpd.library.catalog.api.BookEntityTestData.CHRIS_DANE__ONCE_AGAIN__2022;
import static com.cgdpd.library.catalog.api.BookEntityTestData.COLUMBUS_CHRISTOPHER__IN_LOVE__2011;
import static com.cgdpd.library.catalog.api.BookEntityTestData.JANE_DANE__KILLER__2001;
import static com.cgdpd.library.catalog.api.BookEntityTestData.JANE_DANE__SURVIVOR__2007;
import static com.cgdpd.library.catalog.api.BookEntityTestData.JOHN_DOE__FINDER__1995;
import static com.cgdpd.library.catalog.api.BookEntityTestData.JOHN_DOE__THE_ADVENTUROUS__1987;
import static com.cgdpd.library.catalog.api.BookEntityTestData.aBookEntity;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.AVAILABLE;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.BEING_PROCESSED;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.CHECKED_OUT;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.LOST;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.ON_HOLD;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.REFERENCE;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.RETIRED;

import com.cgdpd.library.catalog.api.entity.BookCopyEntity;

import java.util.concurrent.atomic.AtomicLong;

public class BookCopyEntityTestData {

    private static final AtomicLong bookCopyId = new AtomicLong(1L);
    public static final BookCopyEntity JOHN_DOE__THE_ADVENTUROUS_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__THE_ADVENTUROUS__1987)
          .trackingStatus(CHECKED_OUT.name())
          .userId(1L)
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
          .userId(1L)
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
          .userId(2L)
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
          .userId(2L)
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

    public static BookCopyEntity.BookCopyEntityBuilder aBookCopyEntity() {
        return aBookCopyEntity(1L);
    }

    public static BookCopyEntity.BookCopyEntityBuilder aBookCopyEntity(long bookId) {
        return BookCopyEntity.builder()
              .id(null)
              .bookEntity(aBookEntity().id(bookId).build())
              .trackingStatus(AVAILABLE.name());
    }
}
