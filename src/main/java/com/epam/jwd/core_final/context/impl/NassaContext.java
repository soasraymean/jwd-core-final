package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.MissionsFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them

    private static final Logger logger = LoggerFactory.getLogger(NassaContext.class);

    private static NassaContext INSTANCE;

    public static NassaContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NassaContext();
        }
        return INSTANCE;
    }

    private NassaContext() {

    }

    private final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<Planet> planetMap = new ArrayList<>();
    private Collection<FlightMission> flightMissions = new ArrayList<>();

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {

        Collection<T> collection = null;
        if (CrewMember.class.equals(tClass)) {
            collection = (Collection<T>) crewMembers;
        } else if (Spaceship.class.equals(tClass)) {
            collection = (Collection<T>) spaceships;
        } else if (Planet.class.equals(tClass)) {
            collection = (Collection<T>) planetMap;
        } else if (FlightMission.class.equals(tClass)) {
            collection = (Collection<T>) flightMissions;
        }
        if (collection == null) {
            logger.error("Unknown Entity Exception");
            throw new UnknownEntityException(tClass.getName() + " was passed to method retrieveBaseEntityList");
        }
        return collection; // throw exception if null?

    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */

    @Override
    public void init() throws InvalidStateException {
        initCrew(applicationProperties);
        initSpaceships(applicationProperties);
        initPlanets(applicationProperties);
        generateMissions();
    }


    private void initCrew(ApplicationProperties applicationProperties) throws InvalidStateException {
        Scanner in;
        try {
            String path = "C:\\Users\\danko\\IdeaProjects\\jwd-core-final\\src\\main\\resources\\" + applicationProperties.getInputRootDir() + applicationProperties.getCrewFileName();
            File file = new File(path);
            in = new Scanner(file);

            CrewMemberFactory crewMemberFactory = CrewMemberFactory.getInstance();
            long id = 0;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.charAt(0) != '#') {
                    while (line.length() > 0) {
                        String crewMemberInfo = line.substring(0, line.indexOf(';'));
                        line = line.substring(line.indexOf(';') + 1);
                        //4,Davey Bentley,2;
                        short role = Short.parseShort(crewMemberInfo.substring(0, crewMemberInfo.indexOf(',')));
                        String name = crewMemberInfo.substring(crewMemberInfo.indexOf(',') + 1, crewMemberInfo.lastIndexOf(','));
                        short rank = Short.parseShort(crewMemberInfo.substring(crewMemberInfo.lastIndexOf(',') + 1));

                        if (role > 4 || name.isEmpty() || rank > 4) {
                            logger.error("Invalid State Exception");
                            throw new InvalidStateException();
                        }
                        crewMembers.add(crewMemberFactory.create(
                                role,
                                name,
                                rank,
                                ++id));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void initSpaceships(ApplicationProperties applicationProperties) throws InvalidStateException {
        try {
            String path = "C:\\Users\\danko\\IdeaProjects\\jwd-core-final\\src\\main\\resources\\" + applicationProperties.getInputRootDir() + applicationProperties.getSpaceshipsFileName();
            File file = new File(path);
            Scanner in = new Scanner(file);
            SpaceshipFactory spaceshipFactory = SpaceshipFactory.getInstance();
            long id = 0;
            //Challenger;201117;{1:5,2:9,3:3,4:3}
            while (in.hasNextLine()) {
                String spaceshipInfo = in.nextLine();
                if (spaceshipInfo.charAt(0) != '#') {
                    Map<Role, Short> crewInfoMap = new HashMap<>();
                    String crewInfo = spaceshipInfo.substring(spaceshipInfo.indexOf('{') + 1, spaceshipInfo.lastIndexOf('}')) + ',';
                    while (crewInfo.length() > 0) {
                        String crewInfoMapElement = crewInfo.substring(0, crewInfo.indexOf(','));
                        crewInfo = crewInfo.substring(crewInfo.indexOf(',') + 1);

                        short roleLong = Short.parseShort(crewInfoMapElement.substring(0, crewInfoMapElement.indexOf(':')));
                        short members = Short.parseShort(crewInfoMapElement.substring(crewInfoMapElement.indexOf(':') + 1));
                        if (roleLong > 4) throw new InvalidStateException();

                        Role role = Role.resolveRoleById(roleLong);
                        crewInfoMap.put(role, members);
                    }
                    String name = spaceshipInfo.substring(0, spaceshipInfo.indexOf(';'));
                    long flightDistance = Long.parseLong(spaceshipInfo.substring(spaceshipInfo.indexOf(';') + 1, spaceshipInfo.lastIndexOf(';')));
                    if (name.isEmpty()) {
                        logger.error("Invalid State Exception");
                        throw new InvalidStateException();
                    }
                    spaceships.add(spaceshipFactory.create(
                            name,
                            flightDistance,
                            crewInfoMap,
                            ++id
                    ));
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    private void initPlanets(ApplicationProperties applicationProperties) {
        try {
            String path = "C:\\Users\\danko\\IdeaProjects\\jwd-core-final\\src\\main\\resources\\" + applicationProperties.getInputRootDir() + applicationProperties.getSpacemapFileName();
            File file = new File(path);
            Scanner in = new Scanner(file);
            int MAP_SIZE_ROWS = 150, MAP_SIZE_COLS = 150;
            String[][] map = new String[MAP_SIZE_ROWS][MAP_SIZE_COLS];
            int i = 0;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] split = line.split(",");
                map[i++] = split;
            }
            PlanetFactory planetFactory = PlanetFactory.getInstance();
            long id = 0;
            for (i = 0; i < MAP_SIZE_ROWS; i++) {
                for (int j = 0; j < MAP_SIZE_COLS; j++) {
                    if (!"null".equals(map[i][j])) {
                        planetMap.add(planetFactory.create(map[i][j], i, j, ++id));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void generateMissions() {
        MissionsFactory missionsFactory = MissionsFactory.getInstance();

        File jsonFile = new File("C:\\Users\\danko\\IdeaProjects\\jwd-core-final\\src\\main\\resources\\" + applicationProperties.getOutputRootDir() + applicationProperties.getMissionsFileName());
        jsonFile.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            int missionsQuantity = planetMap.size();
            for (int i = 0; i < missionsQuantity; i++) {
                flightMissions.add(missionsFactory.create());
            }
            for (FlightMission flightMission : flightMissions) {
                fileWriter.write(flightMission.toJSON());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


    }

}