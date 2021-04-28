package com.epam.jwd.core_final.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    //todo
    private String name;
    private Map<Role, Short> crewInfo;
    private Long flightDistance;
    private boolean isReadyForNextMissions;
    private Long id;

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "id="+id+
                ", name='" + name + '\'' +
                ", flightDistance=" + flightDistance +
                ", isReadyForNextMissions=" + isReadyForNextMissions +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public Map<Role, Short> getCrewInfo() {
        return crewInfo;
    }

    public Spaceship(String name, Long flightDistance, HashMap<Role, Short> crewInfo, Long id) {
        this.name = name;
        this.crewInfo = crewInfo;
        this.flightDistance = flightDistance;
        this.isReadyForNextMissions = true;
        this.id =id;
    }

    public boolean isReady() {
        return isReadyForNextMissions;
    }
}