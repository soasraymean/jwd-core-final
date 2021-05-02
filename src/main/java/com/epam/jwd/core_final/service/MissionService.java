package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.TheCrew;
import com.epam.jwd.core_final.exception.InvalidInputException;

import java.util.List;
import java.util.Optional;

public interface MissionService {


    List<FlightMission> findAllMissions();

    List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria);

    Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria);

    FlightMission updateMissionDetails(FlightMission flightMission, Spaceship warBoat, TheCrew theCrew);

    FlightMission createMission(FlightMission flightMission);

    FlightMission findById(long id) throws InvalidInputException;

    FlightMission updateMissionDetails(FlightMission mission, int status);
}
