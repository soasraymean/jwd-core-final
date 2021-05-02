package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidInputException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu implements ApplicationMenu {

    private static final Logger logger = LoggerFactory.getLogger(Menu.class);
    private static final CrewService crewService = CrewServiceImpl.getInstance();
    private static final MissionService missionService = MissionServiceImpl.getInstance();
    private static final SpacemapService spacemapService = SpacemapServiceImpl.getInstance();
    private static final SpaceshipService spaceshipService = SpaceshipServiceImpl.getInstance();


    @Override
    public boolean printAvailableOptions() throws InvalidInputException {
        System.out.println("1 - Hangar");
        System.out.println("2 - Available Missions");
        System.out.println("3 - Barracks");
        System.out.println("4 - Spacemap");
        System.out.println("5 - Spaceship by ID");
        System.out.println("6 - Mission by ID");
        System.out.println("7 - Crew Member by ID");
        System.out.println("8 - !!!Try to Execute Mission!!!");
        System.out.println("9 - Mark officer as dead by ID");
        System.out.println("10 - Mark spaceship as 'Not ready' for any mission");
        System.out.println("0 - Exit\n");
        return handleUserInput();
    }

    @Override
    public boolean handleUserInput() throws InvalidInputException {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the option, commander!\nOption: ");
        int option = Integer.parseInt(in.nextLine());
        logger.info("USER INPUT - " + option);
        if (option < 0 || option > 10) {
            logger.error("Wrong Option chosen");
            throw new InvalidInputException();
        }
        switch (option) {
            case 1:
                printSpaceships();
                break;
            case 2:
                printMissions();
                break;
            case 3:
                printCrewCandidates();
                break;
            case 4:
                printSpacemap();
                break;
            case 5:
                getSpaceshipById();
                break;
            case 6:
                printMissionById();
                break;
            case 7:
                getCrewMemberById();
                break;
            case 8:
                startMission();
                break;
            case 9:
                markOfficerAsDead();
                break;
            case 10:
                markSpaceshipAsNotReadyAnymore();
                break;
            case 0:
                halt();
        }

        return true;
    }

    private void printSpacemap() {
        showProgress();
        List<Planet> list = spacemapService.findAllPlanets();
        for (Planet planet : list) {
            System.out.println(planet.toString());

        }
    }

    private void markSpaceshipAsNotReadyAnymore() throws InvalidInputException {
        Spaceship spaceship = getSpaceshipById();
        System.out.println("Updating information");
        spaceship = spaceshipService.updateSpaceshipDetails(spaceship);
        System.out.println(spaceship.toString());
    }

    private void markOfficerAsDead() throws InvalidInputException {
        CrewMember member = getCrewMemberById();
        System.out.println("Updating information");
        member = crewService.updateCrewMemberDetails(member);
        System.out.println(member.toString());
    }

    private void startMission() throws InvalidInputException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Mission ID: ");
        long missionID = Long.parseLong(in.nextLine());
        logger.info("USER INPUT - " + missionID);
        FlightMission mission = missionService.findById(missionID);
        System.out.println("Chosen mission: " + mission.toString());
        System.out.print("Enter Spaceship ID: ");
        long shipID = Long.parseLong(in.nextLine());
        logger.info("USER INPUT - " + shipID);
        Spaceship warBoat = spaceshipService.findById(shipID);
        System.out.println("Chosen spaceship: " + warBoat.toString());
        List<TheCrew> theCrewList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            theCrewList.add(crewService.generateTheCrewForSpaceship(warBoat));
        }
        for (int i = 1; i < theCrewList.size(); i++) {
            if (theCrewList.get(0).getMembersInfo().size() != theCrewList.get(i).getMembersInfo().size()) {
                theCrewList.remove(i);
                i--;
            }
        }
        showCrewList(theCrewList);

        System.out.print("Enter The Crew ID: ");
        long crewID = Long.parseLong(in.nextLine());
        logger.info("USER INPUT - " + crewID);
        TheCrew theCrew = theCrewList.stream()
                .filter(crew -> crewID == crew.getId())
                .findAny()
                .orElse(null);
        if (theCrew == null) {
            logger.error("No crews were found");
        } else {
            System.out.println("Chosen crew: " + theCrew.toString());
        }


        warBoat = spaceshipService.updateSpaceshipDetails(warBoat);
        mission = missionService.updateMissionDetails(mission, warBoat, theCrew);
        int rng = (int) (1 + Math.random() * 10);
        if (rng % 2 != 0) {
            missionService.updateMissionDetails(mission, 1);
            spaceshipService.updateSpaceshipDetails(warBoat, 1);
            crewService.updateCrewMemberDetails(theCrew, 1);
            System.out.println("\n\n\nSUCCESS");
        } else {
            missionService.updateMissionDetails(mission, 2);
            spaceshipService.updateSpaceshipDetails(warBoat, 2);
            crewService.updateCrewMemberDetails(theCrew, 2);
            System.out.println("\n\n\nSOMETHING WENT WRONG");
        }
    }

    private CrewMember getCrewMemberById() throws InvalidInputException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Crew Member ID: ");
        long memberID = Long.parseLong(in.nextLine());
        logger.info("USER INPUT - " + memberID);
        CrewMember member = crewService.findById(memberID);
        System.out.println(member.toString());
        return member;
    }

    private void printMissionById() throws InvalidInputException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Mission ID: ");
        long missionID = Long.parseLong(in.nextLine());
        logger.info("USER INPUT - " + missionID);
        FlightMission mission = missionService.findById(missionID);
        System.out.println(mission.toString());
    }

    private Spaceship getSpaceshipById() throws InvalidInputException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Spaceship ID: ");
        long shipID = Long.parseLong(in.nextLine());
        logger.info("USER INPUT - " + shipID);
        Spaceship spaceship = spaceshipService.findById(shipID);
        System.out.println(spaceship.toString());
        return spaceship;
    }

    private void showCrewList(List<TheCrew> theCrewList) {
        for (TheCrew crew : theCrewList) {
            System.out.println(crew.toString());
        }
    }

    private void halt() {
        System.exit(0);
    }

    private void printCrewCandidates() {
        showProgress();
        List<CrewMember> list = crewService.findAllCrewMembers();
        for (CrewMember cm : list) {
            System.out.println(cm.toString());
        }
    }

    private void printSpaceships() {
        showProgress();
        List<Spaceship> list = spaceshipService.findAllSpaceships();
        for (Spaceship spaceship : list) {
            System.out.println(spaceship.toString());

        }
    }

    private void printMissions() {
        showProgress();
        List<FlightMission> list = missionService.findAllMissions();
        for (FlightMission mission : list) {
            System.out.println(mission.toString());
        }
    }

    private void showProgress() {
        ProgressBarBuilder pbb = new ProgressBarBuilder()
                .setInitialMax(100)
                .setStyle(ProgressBarStyle.ASCII)
                .setUpdateIntervalMillis((int) (15 + Math.random() * 30))
                .setTaskName("Data retrieval")
                .setMaxRenderedLength("Data retrieval".length() + 60);
        ProgressBar pb = pbb.build();
        while (pb.getCurrent() != 100) {
            try {
                pb.step();
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        pb.close();
    }

}
