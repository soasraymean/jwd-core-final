package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.TheCrew;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewProcessor;
import com.epam.jwd.core_final.service.impl.MissionProcessor;
import com.epam.jwd.core_final.service.impl.SpaceshipProcessor;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu implements ApplicationMenu {

    private static Logger logger = LoggerFactory.getLogger(ApplicationMenu.class);

    @Override
    public boolean printAvailableOptions() {
        System.out.println("1 - Hangar");
        System.out.println("2 - Available Missions");
        System.out.println("3 - Barracks");
        System.out.println("4 - Spaceship by ID");
        System.out.println("5 - Mission by ID");
        System.out.println("6 - Crew Member by ID");
        System.out.println("7 - !!!Try to Execute Mission!!!");
        System.out.println("8 - Mark officer as dead by ID");
        System.out.println("9 - Mark spaceship as it's not ready for any mission");
        System.out.println("0 - Exit\n");
        return handleUserInput();
    }

    @Override
    public boolean handleUserInput() {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the option, commander!\nOption: ");
        int option = Integer.parseInt(in.nextLine());
        logger.info("USER INPUT - "+option);
        switch (option) {
            case 1:
                showProgress("Data retrieval");
                printSpaceships();

                break;
            case 2:
                showProgress("Data retrieval");
                printMissions();
                break;
            case 3:
                showProgress("Data retrieval");
                printCrewCandidates();
                break;
            case 4:
                SpaceshipService spaceshipService = new SpaceshipProcessor();
                System.out.print("Enter Spaceship ID: ");
                long shipID = Long.parseLong(in.nextLine());
                logger.info("USER INPUT - "+shipID);
                Spaceship spaceship = spaceshipService.findById(shipID);
                System.out.println(spaceship.toString());
                break;
            case 5:
                MissionService missionService = new MissionProcessor();
                System.out.print("Enter Mission ID: ");
                long missionID = Long.parseLong(in.nextLine());
                logger.info("USER INPUT - "+missionID);
                FlightMission mission = missionService.findById(missionID);
                System.out.println(mission.toString());
                break;
            case 6:
                CrewService crewService = new CrewProcessor();
                System.out.print("Enter Crew Member ID: ");
                long memberID = Long.parseLong(in.nextLine());
                logger.info("USER INPUT - "+memberID);
                CrewMember member = crewService.findById(memberID);
                System.out.println(member.toString());
                break;
            case 7:
                MissionService missionForStartService = new MissionProcessor();
                SpaceshipService warBoatForStartService = new SpaceshipProcessor();
                CrewService crewForMission = new CrewProcessor();
                System.out.print("Enter Mission ID: ");
                missionID = Long.parseLong(in.nextLine());
                logger.info("USER INPUT - "+missionID);
                mission = missionForStartService.findById(missionID);
                System.out.println("Chosen mission: " + mission.toString());
                System.out.print("Enter Spaceship ID: ");
                shipID = Long.parseLong(in.nextLine());
                logger.info("USER INPUT - "+shipID);
                Spaceship warBoat = warBoatForStartService.findById(shipID);
                System.out.println("Chosen spaceship: " + warBoat.toString());
                List<TheCrew> theCrewList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    theCrewList.add(crewForMission.generateTheCrewForSpaceship(warBoat));
                }
                for (int i = 1; i < theCrewList.size(); i++) {
                    if (theCrewList.get(0).getMembersInfo().size() != theCrewList.get(i).getMembersInfo().size()) {
                        theCrewList.remove(i);
                        i--;
                    }
                }
                shiwCrewList(theCrewList);


                System.out.print("Enter The Crew ID: ");
                long crewID = Long.parseLong(in.nextLine());
                logger.info("USER INPUT - "+crewID);
                TheCrew theCrew = theCrewList.stream()
                        .filter(crew -> crewID == crew.getId())
                        .findAny()
                        .orElse(null);
                System.out.println("Chosen crew: " + theCrew.toString());


                warBoat = warBoatForStartService.updateSpaceshipDetails(warBoat);
                mission = missionForStartService.updateMissionDetails(mission, warBoat, theCrew);
                int rng = (int) (1 + Math.random() * 10);
                if (rng % 2 != 0) {
                    missionForStartService.updateMissionDetails(mission, 1);
                    warBoatForStartService.updateSpaceshipDetails(warBoat, 1);
                    crewForMission.updateCrewMemberDetails(theCrew, 1);
                    System.out.println("\n\n\nSUCCESS");
                } else {
                    missionForStartService.updateMissionDetails(mission, 2);
                    warBoatForStartService.updateSpaceshipDetails(warBoat, 2);
                    crewForMission.updateCrewMemberDetails(theCrew, 2);
                    System.out.println("\n\n\nSOMETHING WENT WRONG");
                }
                break;
            case 8:
                crewService = new CrewProcessor();
                System.out.print("Enter Crew Member ID: ");
                memberID = Long.parseLong(in.nextLine());
                logger.info("USER INPUT - "+memberID);
                member = crewService.findById(memberID);
                System.out.println(member.toString());
                System.out.println("Updating information");
                member = crewService.updateCrewMemberDetails(member);
                System.out.println(member.toString());
                break;
            case 9:
                spaceshipService = new SpaceshipProcessor();
                System.out.print("Enter Spaceship ID: ");
                shipID = Long.parseLong(in.nextLine());
                logger.info("USER INPUT - "+shipID);
                spaceship = spaceshipService.findById(shipID);
                System.out.println(spaceship.toString());
                System.out.println("Updating information");
                spaceship = spaceshipService.updateSpaceshipDetails(spaceship);
                System.out.println(spaceship.toString());
                break;
            case 0:
                halt();
        }

        return true;
    }

    private void shiwCrewList(List<TheCrew> theCrewList) {
        for (TheCrew crew : theCrewList) {
            System.out.println(crew.toString());
        }
    }

    private void halt() {
        System.exit(0);
    }

    private void printCrewCandidates() {
        List<CrewMember> list = new ArrayList<>(context.retrieveBaseEntityList(CrewMember.class));
        for (CrewMember cm : list) {
            System.out.println(cm.toString());
        }
    }

    private void printSpaceships() {
        List<Spaceship> list = new ArrayList<>(context.retrieveBaseEntityList(Spaceship.class));
        for (Spaceship spaceship : list) {
            System.out.println(spaceship.toString());

        }
    }

    private void printMissions() {
        List<FlightMission> list = new ArrayList<>(context.retrieveBaseEntityList(FlightMission.class));
        for (FlightMission mission : list) {
            System.out.println(mission.toString());
        }
    }

    private void showProgress(String name) {
        ProgressBarBuilder pbb = new ProgressBarBuilder()
                .setInitialMax(100)
                .setStyle(ProgressBarStyle.ASCII)
                .setUpdateIntervalMillis((int) (15 + Math.random() * 30))
                .setTaskName(name)
                .setMaxRenderedLength(name.length() + 60);
        ProgressBar pb = pbb.build();
        while (pb.getCurrent() != 100) {
            try {
                pb.step();
                Thread.sleep(30);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        pb.close();
    }

}
