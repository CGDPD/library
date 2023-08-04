package com.cgdpd.library;

import static com.cgdpd.library.BookTestData.CHRIS_DANE__ONCE_AGAIN;
import static com.cgdpd.library.BookTestData.COLUMBUS_CHRISTOPHER__IN_LOVE;
import static com.cgdpd.library.BookTestData.JANE_DANE__KILLER;
import static com.cgdpd.library.BookTestData.JANE_DANE__SURVIVOR;
import static com.cgdpd.library.BookTestData.JOHN_DOE__FINDER;
import static com.cgdpd.library.BookTestData.JOHN_DOE__THE_ADVENTUROUS;
import static com.cgdpd.library.BookTestData.aBookEntity;
import static com.cgdpd.library.UserTestData.USER_LOLA_ROGER;
import static com.cgdpd.library.UserTestData.USER_LOUIS_CASTRO;
import static com.cgdpd.library.UserTestData.aUserEntity;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.AVAILABLE;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.BEING_PROCESSED;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.CHECKED_OUT;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.LOST;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.ON_HOLD;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.REFERENCE;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.RETIRED;

import com.cgdpd.library.dto.book.copy.BookCopyDTO;
import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.type.BookCopyId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.UserId;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class BookCopyTestData {

    private static final AtomicLong bookCopyId = new AtomicLong(1L);
    public static final BookCopyEntity JOHN_DOE__THE_ADVENTUROUS_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__THE_ADVENTUROUS)
          .trackingStatus(CHECKED_OUT.name())
          .userEntity(USER_LOLA_ROGER)
          .build();
    public static final BookCopyEntity JOHN_DOE__THE_ADVENTUROUS_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__THE_ADVENTUROUS)
          .trackingStatus(AVAILABLE.name())
          .build();

    public static final BookCopyEntity JOHN_DOE__FINDER_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__FINDER)
          .trackingStatus(BEING_PROCESSED.name())
          .build();
    public static final BookCopyEntity JOHN_DOE__FINDER_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JOHN_DOE__FINDER)
          .trackingStatus(LOST.name())
          .build();

    public static final BookCopyEntity JANE_DANE__KILLER_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JANE_DANE__KILLER)
          .trackingStatus(AVAILABLE.name())
          .build();
    public static final BookCopyEntity JANE_DANE__KILLER_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JANE_DANE__KILLER)
          .trackingStatus(LOST.name())
          .userEntity(USER_LOLA_ROGER)
          .build();

    public static final BookCopyEntity JANE_DANE__SURVIVOR_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(JANE_DANE__SURVIVOR)
          .trackingStatus(REFERENCE.name())
          .build();


    public static final BookCopyEntity CHRIS_DANE__ONCE_AGAIN_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(CHRIS_DANE__ONCE_AGAIN)
          .trackingStatus(ON_HOLD.name())
          .userEntity(USER_LOUIS_CASTRO)
          .build();

    public static final BookCopyEntity CHRIS_DANE__ONCE_AGAIN_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(CHRIS_DANE__ONCE_AGAIN)
          .trackingStatus(RETIRED.name())
          .build();


    public static final BookCopyEntity COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_1 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(COLUMBUS_CHRISTOPHER__IN_LOVE)
          .trackingStatus(CHECKED_OUT.name())
          .userEntity(USER_LOUIS_CASTRO)
          .build();

    public static final BookCopyEntity COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_2 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(COLUMBUS_CHRISTOPHER__IN_LOVE)
          .trackingStatus(AVAILABLE.name())
          .build();

    public static final BookCopyEntity COLUMBUS_CHRISTOPHER__IN_LOVE_COPY_3 = BookCopyEntity.builder()
          .id(bookCopyId.getAndIncrement())
          .bookEntity(COLUMBUS_CHRISTOPHER__IN_LOVE)
          .trackingStatus(AVAILABLE.name())
          .build();

    public static BookCopyDTO.BookCopyDTOBuilder aBookCopy() {
        return BookCopyDTO.builder()
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
