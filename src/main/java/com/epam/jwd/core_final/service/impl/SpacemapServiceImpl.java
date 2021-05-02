package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpacemapService;

import java.util.ArrayList;
import java.util.List;

public class SpacemapServiceImpl implements SpacemapService {
    @Override
    public Planet getRandomPlanet() {
        NassaContext nassaContext = NassaContext.getInstance();
        List<Planet> planets = new ArrayList<>(nassaContext.retrieveBaseEntityList(Planet.class));
        return planets.get((int) (Math.random() * planets.size()));
    }

    @Override
    public long getDistanceBetweenPlanets(Planet first, Planet second) {
        return (long) Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2));
    }
}
