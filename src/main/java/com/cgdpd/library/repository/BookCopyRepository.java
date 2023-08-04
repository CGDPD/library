package com.cgdpd.library.repository;

import com.cgdpd.library.entity.BookCopyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopyEntity, Long> {

    @Query("""
          SELECT bc FROM BookCopyEntity bc
          JOIN bc.bookEntity b
          JOIN b.authorEntity a
          WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))
          """)
    List<BookCopyEntity> findByAuthorName(String authorName);
}
