package com.ll.exam.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<SiteUser> findByusername(String username);
}
