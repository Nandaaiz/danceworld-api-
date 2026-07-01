package com.forroworld.forro_api.repository;

import com.forroworld.forro_api.model.TravelSchedule;
import com.forroworld.forro_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TravelScheduleRepository extends JpaRepository<TravelSchedule, UUID> {
    List<TravelSchedule> findByUser(User user);

    @Query("SELECT t FROM TravelSchedule t WHERE LOWER(t.destinationCity) = LOWER(:city)")
    List<TravelSchedule> findByDestinationCity(@Param("city") String city);

    @Query("SELECT t FROM TravelSchedule t WHERE LOWER(t.destinationCountry) = LOWER(:country)")
    List<TravelSchedule> findByDestinationCountry(@Param("country") String country);

    @Query("SELECT t FROM TravelSchedule t WHERE LOWER(t.destinationCity) = LOWER(:city) AND t.startDate <= :date AND t.endDate >= :date")
    List<TravelSchedule> findByDestinationCityAndDate(@Param("city") String city, @Param("date") LocalDateTime date);
}