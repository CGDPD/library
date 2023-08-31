package com.cgdpd.library.repository;

import com.cgdpd.library.dto.book.DetailedBookDTO;
import com.cgdpd.library.entity.BookEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository
      extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {


    @Query("""
          SELECT new com.cgdpd.library.dto.book.DetailedBookDTO(
          b.id, b.title, a.id, a.name, b.isbn, b.genre, STRING_AGG(c.trackingStatus, ','), b.publicationYear)
          FROM BookEntity b
          JOIN b.authorEntity a
          LEFT JOIN b.bookCopyEntities c
          WHERE b.isbn = :isbn
          GROUP BY b.id, a.id, a.name, b.isbn, b.genre, b.publicationYear
          """)
    Optional<DetailedBookDTO> findDetailedBookByIsbn(@Param("isbn") String isbn);
}
