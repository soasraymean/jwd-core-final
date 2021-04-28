package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.factory.EntityFactory;

public final class MissionsFactory implements EntityFactory {
    private static MissionsFactory INSTANCE;
    private static Long id=0L;
    public static MissionsFactory getInstance(){
        if(INSTANCE==null){
            INSTANCE=new MissionsFactory();
        }
        return INSTANCE;
    }

    private MissionsFactory(){

    }

    @Override
    public FlightMission create(Object... args) {
        return new FlightMission(++id);
    }
}
