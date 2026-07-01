package com.forroworld.forro_api.repository;

import com.forroworld.forro_api.model.Profile;
import com.forroworld.forro_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUser(User user);
    List<Profile> findByUser_UserType(String userType);

    @Query("SELECT p FROM Profile p WHERE LOWER(p.city) = LOWER(:city) AND p.user.userType = :userType")
    List<Profile> findByCityAndUser_UserType(@Param("city") String city, @Param("userType") String userType);

    @Query("SELECT p FROM Profile p WHERE LOWER(p.country) = LOWER(:country) AND p.user.userType = :userType")
    List<Profile> findByCountryAndUser_UserType(@Param("country") String country, @Param("userType") String userType);

    @Query("SELECT p FROM Profile p WHERE LOWER(p.displayName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Profile> findByDisplayNameContaining(@Param("name") String name);

    @Query("SELECT p FROM Profile p WHERE LOWER(p.displayName) LIKE LOWER(CONCAT('%', :name, '%')) AND UPPER(p.user.userType) = UPPER(:userType)")
    List<Profile> findByDisplayNameContainingAndUserType(@Param("name") String name, @Param("userType") String userType);
}