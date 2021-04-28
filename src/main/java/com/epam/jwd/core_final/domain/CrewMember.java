package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    // todo
    private Role role;
    private Rank rank;
    private String name;
    private boolean isReadyForNextMissions = true;
    private boolean isAssigned = false;
    private long id;

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    @Override
    public String toString() {
        return id+" "+name+" "+role+" "+rank+" "+isReadyForNextMissions+" "+isAssigned;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void assign() {
        isAssigned = true;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public Rank getRank() {
        return rank;
    }

    public Role getRole() {
        return role;
    }

    public CrewMember(Short role, String name, Short rank, Long id) {
        super();
        this.role = Role.resolveRoleById(role);
        this.name = name;
        this.rank = Rank.resolveRankById(rank);
        this.id = id;
    }
}
