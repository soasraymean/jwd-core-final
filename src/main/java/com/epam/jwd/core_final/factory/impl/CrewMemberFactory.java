package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public final class CrewMemberFactory implements EntityFactory<CrewMember> {

    private static CrewMemberFactory INSTANCE;

    public static CrewMemberFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrewMemberFactory();
        }
        return INSTANCE;
    }

    private CrewMemberFactory() {

    }

    @Override
    public CrewMember create(Object... args) {
        return new CrewMember((Short) args[0], (String) args[1], (Short) args[2], (Long) args[3]);
    }
}
