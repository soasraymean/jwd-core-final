package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {

    private Long flightDistance;
    private boolean isReadyForNextMissions;
    private Map<Role, Short> crewInfo;

    @Override
    public List<String> getCriteriaList() {
        List<String> criteriaList = new ArrayList<>(super.getCriteriaList());
        criteriaList.add("flightDistance");
        criteriaList.add("isReadyForNextMissions");
        criteriaList.add("crewInfo");
        return criteriaList;
    }

    public static class Builder{
        private final SpaceshipCriteria product = new SpaceshipCriteria();
        public List<String> getCriteriaList(){
            return product.getCriteriaList();
        }
        public SpaceshipCriteria build(){
            return product;
        }
    }
}
