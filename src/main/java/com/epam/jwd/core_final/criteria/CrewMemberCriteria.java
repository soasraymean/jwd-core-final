package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private Role role;
    private Rank rank;

    @Override
    public List<String> getCriteriaList() {
        List<String> criteriaList = new ArrayList<>(super.getCriteriaList());
        criteriaList.add("role");
        criteriaList.add("rank");
        return criteriaList;
    }

    public static class Builder{
        private final CrewMemberCriteria product = new CrewMemberCriteria();

        public List<String> getCriteriaList(){
            return product.getCriteriaList();
        }

        public CrewMemberCriteria build(){
            return product;
        }
    }
}
