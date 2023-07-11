package com.cgdpd.library.repository;

import com.cgdpd.library.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository <UserEntity, Long> {
}
