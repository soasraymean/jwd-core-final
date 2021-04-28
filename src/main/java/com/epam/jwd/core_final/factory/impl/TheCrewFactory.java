package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.TheCrew;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.List;

public class TheCrewFactory implements EntityFactory<TheCrew> {
    private static TheCrewFactory INSTANCE;
    public static TheCrewFactory getInstance() {
        if(INSTANCE==null){
            INSTANCE=new TheCrewFactory();
        }
        return INSTANCE;
    }

    @Override
    public TheCrew create(Object... args) {
        return new TheCrew((List<CrewMember>)args[0]);
    }
}
