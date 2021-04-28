package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.TheCrew;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpaceshipProcessor implements SpaceshipService {

    @Override
    public List<Spaceship> findAllSpaceships() {
        return null;
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        return null;
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        return Optional.empty();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        NassaContext nassaContext = NassaContext.getInstance();
        List<Spaceship> spaceships = new ArrayList<>(nassaContext.retrieveBaseEntityList(Spaceship.class));
        for (Spaceship spc : spaceships) {
            if(spaceship.getId().longValue()==spc.getId().longValue()){
                spc.setReadyForNextMissions(!spc.isReady());
                return spc;
            }
        }
        return null;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship crewMember) throws RuntimeException {

    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        return null;
    }

    @Override
    public Spaceship findById(long shipID) {
        NassaContext nassaContext = NassaContext.getInstance();
        List<Spaceship> list = new ArrayList<>(nassaContext.retrieveBaseEntityList(Spaceship.class));
        Spaceship wanted = list.stream()
                .filter(spaceship -> shipID == spaceship.getId())
                .findAny()
                .orElse(null);
        return wanted;
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship warBoat, int i) {
        NassaContext nassaContext = NassaContext.getInstance();
        List<Spaceship> list = new ArrayList<>(nassaContext.retrieveBaseEntityList(Spaceship.class));
        for (Spaceship spaceship : list) {
            if (spaceship.getId().longValue() == warBoat.getId().longValue()) {
                if (i == 1) {
                    spaceship.setReadyForNextMissions(true);
                    return spaceship;
                } else {
                    spaceship.setReadyForNextMissions(false);
                    return spaceship;
                }
            }
        }
        return null;
    }

}
