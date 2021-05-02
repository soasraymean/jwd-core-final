package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.TheCrew;
import com.epam.jwd.core_final.exception.InvalidInputException;
import com.epam.jwd.core_final.factory.impl.TheCrewFactory;
import com.epam.jwd.core_final.service.CrewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrewServiceImpl implements CrewService {

    private static final Logger logger = LoggerFactory.getLogger(CrewServiceImpl.class);

    private static CrewService INSTANCE;
    public static CrewService getInstance(){
        if(INSTANCE==null){
            INSTANCE=new CrewServiceImpl();
        }
        return INSTANCE;
    }
    private CrewServiceImpl() {

    }
    @Override
    public TheCrew generateTheCrewForSpaceship(Spaceship spaceship) {
        NassaContext nassaContext = NassaContext.getInstance();
        Map<Role, Short> spaceshipCrewInfo = new HashMap<>(spaceship.getCrewInfo());
        List<CrewMember> crewMembers = new ArrayList<>(nassaContext.retrieveBaseEntityList(CrewMember.class));

        List<CrewMember> crew = new ArrayList<>();//to create the crew
        TheCrewFactory tcf = TheCrewFactory.getInstance();

        for (Map.Entry<Role, Short> requiredMember : spaceshipCrewInfo.entrySet()) {

            for (CrewMember candidate : crewMembers) {
                if (!candidate.isAssigned()) {
                    if (requiredMember.getKey() == candidate.getRole() && candidate.isReadyForNextMissions() && requiredMember.getValue() > 0) {
                        candidate.assign();
                        crew.add(candidate);
                        requiredMember.setValue((short) (requiredMember.getValue() - 1));
                    }
                }
            }
        }
        return tcf.create(crew);
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        NassaContext nassaContext = NassaContext.getInstance();
        return new ArrayList<>(nassaContext.retrieveBaseEntityList(CrewMember.class));
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        return null;
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return Optional.empty();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        NassaContext nassaContext=NassaContext.getInstance();
        List<CrewMember> members = new ArrayList<>(nassaContext.retrieveBaseEntityList(CrewMember.class));
        for (CrewMember member : members) {
            if(member.getId().longValue()==crewMember.getId().longValue()){
                member.setRank(Rank.resolveRankById(0));
                member.setReadyForNextMissions(false);
                return member;
            }
        }
        return null;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {

    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        return null;
    }

    @Override
    public CrewMember findById(long id) throws InvalidInputException {
        NassaContext nassaContext = NassaContext.getInstance();
        List<CrewMember> list = new ArrayList<>(nassaContext.retrieveBaseEntityList(CrewMember.class));
        if(id<1 || id>list.size()){
            logger.error("Wrong ID");
            throw new InvalidInputException();
        }
            CrewMember wanted = list.stream()
                .filter(member -> id== member.getId())
                .findAny()
                .orElse(null);
        return wanted;
    }

    @Override
    public TheCrew updateCrewMemberDetails(TheCrew theCrew, int i) {
        NassaContext nassaContext = NassaContext.getInstance();
        List<CrewMember> list = new ArrayList<>(nassaContext.retrieveBaseEntityList(CrewMember.class));
        for (CrewMember crewMember : theCrew.getMembersInfo()) {
            for (int j = 0; j < list.size(); j++) {
                if(crewMember.getId().longValue()==list.get(j).getId()){
                    list.get(j).setReadyForNextMissions(false);
                    list.get(j).setRank(Rank.resolveRankById(0));
                }
            }
        }
        return null;
    }
}
