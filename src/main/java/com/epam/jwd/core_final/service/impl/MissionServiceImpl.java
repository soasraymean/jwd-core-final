package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.TheCrew;
import com.epam.jwd.core_final.exception.InvalidInputException;
import com.epam.jwd.core_final.service.MissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MissionServiceImpl implements MissionService {
    private static final Logger logger = LoggerFactory.getLogger(MissionServiceImpl.class);
    @Override
    public List<FlightMission> findAllMissions() {
        NassaContext nassaContext = NassaContext.getInstance();
        return new ArrayList<>(nassaContext.retrieveBaseEntityList(FlightMission.class));
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        return null;
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return Optional.empty();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission, Spaceship warBoat, TheCrew theCrew) {
        NassaContext nassaContext = NassaContext.getInstance();
        List<FlightMission> missions = new ArrayList<>(nassaContext.retrieveBaseEntityList(FlightMission.class));
        for (FlightMission mission : missions) {
            if(mission.getId().longValue()==flightMission.getId().longValue()){
                mission.setAssignedCrew(theCrew);
                mission.setSpaceship(warBoat);
                mission.setMissionResult(MissionResult.IN_PROGRESS);
                return mission;
            }
        }
        return null;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {
        return null;
    }

    @Override
    public FlightMission findById(long id) throws InvalidInputException {
        NassaContext nassaContext = NassaContext.getInstance();
        List<FlightMission> list = new ArrayList<>(nassaContext.retrieveBaseEntityList(FlightMission.class));
        if(id<1 || id>list.size()){
            logger.error("Wrong ID");
            throw new InvalidInputException();
        }
        FlightMission wanted = list.stream()
                .filter(mission -> id == mission.getId())
                .findAny()
                .orElse(null);
        return wanted;
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission mission, int status) {
        NassaContext nassaContext = NassaContext.getInstance();
        List<FlightMission> list = new ArrayList<>(nassaContext.retrieveBaseEntityList(FlightMission.class));
        for (FlightMission flightMission : list) {

            if (mission.getId().longValue() == flightMission.getId().longValue()) {
                if (status == 1) {
                    flightMission.setMissionResult(MissionResult.COMPLETED);
                    return flightMission;
                } else {
                    flightMission.setMissionResult(MissionResult.FAILED);
                    return flightMission;
                }

            }
        }
        return null;
    }
}
