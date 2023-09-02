package com.cgdpd.library.catalog.domain;

import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.CHECKED_OUT;

import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopyId;
import com.cgdpd.library.catalog.domain.book.model.copy.UserId;

import java.util.Optional;

public class BookCopyTestData {

    public static BookCopy.BookCopyBuilder aBookCopy() {
        return BookCopy.builder()
              .id(BookCopyId.of(1L))
              .bookId(BookId.of(1L))
              .trackingStatus(CHECKED_OUT)
              .userId(Optional.of(UserId.of(1L)));
    }
}
