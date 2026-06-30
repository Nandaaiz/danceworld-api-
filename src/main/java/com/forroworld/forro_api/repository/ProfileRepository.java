package com.forroworld.forro_api.repository;

import com.forroworld.forro_api.model.Profile;
import com.forroworld.forro_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUser(User user);
}