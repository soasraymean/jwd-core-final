package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * location could be a simple class Point with 2 coordinates
 */
public class Planet extends AbstractBaseEntity{
    private String name;
    private int x,y;
    private long id;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Planet(String name, int x, int y, Long id) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.id=id;
    }
}
