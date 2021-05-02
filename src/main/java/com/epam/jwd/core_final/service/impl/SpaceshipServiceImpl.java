package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.TheCrew;
import com.epam.jwd.core_final.exception.InvalidInputException;
import com.epam.jwd.core_final.service.SpaceshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpaceshipServiceImpl implements SpaceshipService {

    private static final Logger logger = LoggerFactory.getLogger(SpaceshipServiceImpl.class);

    @Override
    public List<Spaceship> findAllSpaceships() {
        NassaContext nassaContext = NassaContext.getInstance();
        return new ArrayList<>(nassaContext.retrieveBaseEntityList(Spaceship.class));
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
    public Spaceship findById(long id) throws InvalidInputException {
        NassaContext nassaContext = NassaContext.getInstance();
        List<Spaceship> list = new ArrayList<>(nassaContext.retrieveBaseEntityList(Spaceship.class));
        if(id<1 || id>list.size()){
            logger.error("Wrong ID");
            throw new InvalidInputException();
        }
        Spaceship wanted = list.stream()
                .filter(spaceship -> id == spaceship.getId())
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
