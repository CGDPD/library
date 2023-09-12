package com.cgdpd.library.catalog.app.repository;

import com.cgdpd.library.catalog.app.entity.AuthorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {}
