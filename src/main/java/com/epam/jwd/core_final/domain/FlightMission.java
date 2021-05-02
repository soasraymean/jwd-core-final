package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceship {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 * from {@link Planet}
 * to {@link Planet}
 */
public class FlightMission extends AbstractBaseEntity {
    // todo
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long distance;
    private Spaceship spaceship;
    private TheCrew assignedCrew;
    private MissionResult missionResult;
    private Planet from;
    private Planet to;

    @Override
    public Long getId() {
        return id;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    public void setAssignedCrew(TheCrew assignedCrew) {
        this.assignedCrew = assignedCrew;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    public String toJSON() {
        return "FlightMission{" +
                "ID=" + id + ", " +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", distance=" + distance + "}";
    }

    @Override
    public String toString() {
        return "FlightMission{" +
                "ID=" + id + ", " +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", distance=" + distance +
                ", spaceship=" + spaceship +
                ", assignedCrew=" + assignedCrew +
                ", missionResult=" + missionResult + "}";
    }

    public FlightMission(Long id) {
        this.id = id;
        startDate = LocalDate.now();
        endDate = startDate.plusMonths(1 + (int) (Math.random() * 12));

        missionResult = MissionResult.WAITING_FOR_ASSIGNMENT;
        SpacemapService spacemapService = new SpacemapServiceImpl();
        do {
            from = spacemapService.getRandomPlanet();
            to = spacemapService.getRandomPlanet();
        } while (from.getName().equals(to.getName()));

        name = from.getName() + " - " + to.getName();
        distance = spacemapService.getDistanceBetweenPlanets(from, to);

    }


}
