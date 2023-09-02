package com.cgdpd.library.catalog.api.repository;

import com.cgdpd.library.catalog.api.entity.BookCopyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopyEntity, Long> {
}
