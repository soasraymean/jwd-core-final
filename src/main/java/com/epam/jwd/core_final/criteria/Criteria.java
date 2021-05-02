package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {
    Long id;
    String name;

    public List<String> getCriteriaList(){
        List<String> criteriaList = new ArrayList<>();
        criteriaList.add("id");
        criteriaList.add("name");
        return criteriaList;
    }
}