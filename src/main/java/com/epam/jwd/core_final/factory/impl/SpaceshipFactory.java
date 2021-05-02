package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public final class SpaceshipFactory implements EntityFactory<Spaceship> {
    private static SpaceshipFactory INSTANCE;

    public static SpaceshipFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpaceshipFactory();
        }
        return INSTANCE;
    }

    private SpaceshipFactory() {
    }

    @Override
    public Spaceship create(Object... args) {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Spaceship((String) args[0], (long) args[1], objectMapper.convertValue(args[2], new TypeReference<HashMap<Role, Short>>() {}), (Long) args[3]);
    }
}
