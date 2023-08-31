package com.cgdpd.library.repository;

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
          SELECT b
          FROM BookEntity b
          JOIN b.authorEntity a
          LEFT JOIN b.bookCopyEntities c
          WHERE b.isbn = :isbn

          """)
    Optional<BookEntity> findDetailedBookByIsbn(@Param("isbn") String isbn);
}
