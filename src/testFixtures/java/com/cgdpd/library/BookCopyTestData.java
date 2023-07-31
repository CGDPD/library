package com.cgdpd.library;

import static com.cgdpd.library.BookTestData.aBookEntity;
import static com.cgdpd.library.UserTestData.aUserEntity;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.CHECKED_OUT;

import com.cgdpd.library.dto.book.copy.BookCopyDTO;
import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.type.BookCopyId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.UserId;
import java.util.Optional;

public class BookCopyTestData {

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
