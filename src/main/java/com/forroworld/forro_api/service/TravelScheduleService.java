package com.forroworld.forro_api.service;

import com.forroworld.forro_api.dto.TravelScheduleRequest;
import com.forroworld.forro_api.dto.TravelScheduleResponse;
import com.forroworld.forro_api.model.Profile;
import com.forroworld.forro_api.model.TravelSchedule;
import com.forroworld.forro_api.model.User;
import com.forroworld.forro_api.repository.ProfileRepository;
import com.forroworld.forro_api.repository.TravelScheduleRepository;
import com.forroworld.forro_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelScheduleService {

    private final TravelScheduleRepository travelRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public TravelScheduleService(TravelScheduleRepository travelRepository,
                                 UserRepository userRepository,
                                 ProfileRepository profileRepository) {
        this.travelRepository = travelRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public TravelScheduleResponse createTravel(String email, TravelScheduleRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TravelSchedule travel = new TravelSchedule();
        travel.setUser(user);
        travel.setDestinationCity(request.getDestinationCity());
        travel.setDestinationCountry(request.getDestinationCountry());
        travel.setStartDate(request.getStartDate());
        travel.setEndDate(request.getEndDate());
        travel.setNotes(request.getNotes());

        travelRepository.save(travel);
        return toResponse(travel);
    }

    public List<TravelScheduleResponse> getMyTravels(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return travelRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TravelScheduleResponse> findByCity(String city) {
        return travelRepository.findByDestinationCity(city)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TravelScheduleResponse> findByCityAndDate(String city, LocalDateTime date) {
        return travelRepository.findByDestinationCityAndDate(city, date)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteTravel(String email, UUID id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        TravelSchedule travel = travelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel not found"));
        if (!travel.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You can only delete your own travels");
        }
        travelRepository.delete(travel);
    }

    private TravelScheduleResponse toResponse(TravelSchedule travel) {
        Profile profile = profileRepository.findByUser(travel.getUser()).orElse(null);
        String displayName = profile != null ? profile.getDisplayName() : null;

        return new TravelScheduleResponse(
                travel.getId(),
                travel.getUser().getEmail(),
                displayName,
                travel.getUser().getUserType().name(),
                travel.getDestinationCity(),
                travel.getDestinationCountry(),
                travel.getStartDate(),
                travel.getEndDate(),
                travel.getNotes()
        );
    }
}