package com.reshmah.incident.repository;

import com.reshmah.incident.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Users extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
}
