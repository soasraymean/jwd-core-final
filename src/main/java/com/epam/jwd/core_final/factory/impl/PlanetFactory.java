package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;

public final  class PlanetFactory implements EntityFactory<Planet> {
    private static PlanetFactory INSTANCE;

    public static PlanetFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlanetFactory();
        }
        return INSTANCE;
    }

    private PlanetFactory() {

    }

    @Override
    public Planet create(Object... args) {
        return new Planet((String)args[0], (int)args[1], (int)args[2], (Long)args[3]);
    }
}
