package com.space.planetmoonapi.repository;

import com.space.planetmoonapi.dto.UserSummaryDTO;
import com.space.planetmoonapi.entity.User;
import com.space.planetmoonapi.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByRole(UserRole role);

    List<User> findByEnabled(Boolean enabled);

    @Query("SELECT new com.space.planetmoonapi.dto.UserSummaryDTO(u.userId, u.username, u.role, u.enabled, u.active) FROM User u")
    List<UserSummaryDTO> findAllUserSummaries();

    @Query("SELECT new com.space.planetmoonapi.dto.UserSummaryDTO(u.userId, u.username, u.role, u.enabled, u.active) FROM User u WHERE u.userId = :id")
    Optional<UserSummaryDTO> findUserSummaryById(@Param("id") Long id);

    @Query("SELECT new com.space.planetmoonapi.dto.UserSummaryDTO(u.userId, u.username, u.role, u.enabled, u.active) FROM User u WHERE u.role = :role")
    List<UserSummaryDTO> findUserSummariesByRole(@Param("role") UserRole role);
}