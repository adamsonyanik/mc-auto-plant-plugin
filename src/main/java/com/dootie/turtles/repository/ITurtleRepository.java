package com.dootie.turtles.repository;

import java.util.List;

public interface ITurtleRepository {
    List<Turtle> getTurtles();

    void removeTurtle(int x, int y, int z);

    Turtle createTurtle(int x, int y, int z);

    Turtle getTurtle(int x, int y, int z);
}
