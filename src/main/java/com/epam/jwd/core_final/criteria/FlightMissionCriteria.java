package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.TheCrew;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long distance;
    private Spaceship spaceship;
    private TheCrew assignedCrew;
    private MissionResult missionResult;
    private Planet from;
    private Planet to;

    @Override
    public List<String> getCriteriaList() {
        List<String> criteriaList = new ArrayList<>(super.getCriteriaList());
        criteriaList.add("startDate");
        criteriaList.add("endDate");
        criteriaList.add("distance");
        criteriaList.add("spaceship");
        criteriaList.add("theCrew");
        criteriaList.add("missionResult");
        criteriaList.add("fromPlanet");
        criteriaList.add("toPlanet");
        return criteriaList;
    }

    public static class Builder{
        private final FlightMissionCriteria product = new FlightMissionCriteria();

        public List<String> getCriteriaList(){
            return product.getCriteriaList();
        }
        public FlightMissionCriteria build(){
            return product;
        }
    }
}
