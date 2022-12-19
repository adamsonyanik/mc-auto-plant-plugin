package com.dootie.turtles.repository;

import org.bukkit.Location;

import java.util.List;

public interface ITurtleRepository {
    List<Turtle> getTurtles();

    void removeTurtle(int x, int y, int z);

    Turtle createTurtle(Location location);

    Turtle getTurtle(int x, int y, int z);
}
