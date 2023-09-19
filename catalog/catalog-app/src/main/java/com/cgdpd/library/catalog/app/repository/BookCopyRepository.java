package com.cgdpd.library.catalog.app.repository;

import com.cgdpd.library.catalog.app.entity.BookCopyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopyEntity, Long> {
}
