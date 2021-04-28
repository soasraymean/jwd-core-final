package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.TheCrew;
import com.epam.jwd.core_final.service.MissionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MissionProcessor implements MissionService {
    @Override
    public List<FlightMission> findAllMissions() {
        return null;
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
    public FlightMission findById(long id) {
        NassaContext nassaContext = NassaContext.getInstance();
        List<FlightMission> list = new ArrayList<>(nassaContext.retrieveBaseEntityList(FlightMission.class));
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
