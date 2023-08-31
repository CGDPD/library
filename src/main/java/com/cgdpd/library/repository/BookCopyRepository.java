package com.cgdpd.library.repository;

import com.cgdpd.library.entity.BookCopyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopyEntity, Long> {
}
