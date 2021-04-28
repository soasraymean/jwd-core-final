package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.context.impl.NassaContext;

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

    @Override
    public Long getId() {
        return id;
    }

    private Planet from;
    private Planet to;

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    public void setAssignedCrew(TheCrew assignedCrew) {
        this.assignedCrew = assignedCrew;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    public String toJSON(){
        return "FlightMission{" +
                "ID="+id+", "+
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", distance=" + distance + "}";
    }
    @Override
    public String toString() {
        return "FlightMission{" +
                "ID="+id+", "+
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", distance=" + distance +
                ", spaceship=" + spaceship +
                ", assignedCrew=" + assignedCrew +
                ", missionResult=" + missionResult + "}";
    }

    public FlightMission(Long id) {
        this.id=id;
        startDate = LocalDate.now();
        endDate = startDate.plusMonths(1 + (int) (Math.random() * 12));

        NassaContext nassaContext = NassaContext.getInstance();
        Collection<Planet> planets = nassaContext.retrieveBaseEntityList(Planet.class);
        List<Planet> planetList = new ArrayList<>(planets);
        int i1 = 0, i2 = 0;
        missionResult = MissionResult.WAITING_FOR_ASSIGNMENT;
        while (i1 == i2) {
            i1 = (int) (Math.random() * planets.size());
            i2 = (int) (Math.random() * planets.size());
        }
        from = planetList.get(i1);
        to = planetList.get(i2);
        name=from.getName()+" - "+to.getName();
        distance = (long) Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2));

    }


}
