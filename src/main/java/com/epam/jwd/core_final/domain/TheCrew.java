package com.epam.jwd.core_final.domain;

import java.util.List;

public class TheCrew implements BaseEntity {
    private static Long idCounter = 0L; // think about the other way to generate ID
    private Long id;
    private String name;
    private List<CrewMember> membersInfo;

    public TheCrew(List<CrewMember> membersInfo) {
        id = ++idCounter;
        this.membersInfo = membersInfo;
        for (CrewMember member : membersInfo) {
            if (member.getRole().equals(Role.COMMANDER)) {
                name = member.getName() + "'s CREW";
                break;
            }
        }
    }


    public List<CrewMember> getMembersInfo() {
        return membersInfo;
    }

    @Override
    public String toString() {
        return "TheCrew{" +
                "ID='" + id + '\'' +
                ", name=" + name +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }


}
