package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.Planet;

import java.util.List;

public interface SpacemapService {


    Planet getRandomPlanet();

    // Dijkstra ?
    long getDistanceBetweenPlanets(Planet first, Planet second);

    List<Planet> findAllPlanets();
}
