package com.dootie.turtles.repository;

import java.util.List;
import java.util.UUID;

public interface ITurtleRepository {
    List<Turtle> getTurtles();

    void removeTurtle(int var1, int var2, int var3);

    Turtle createTurtle(UUID var1, int var2, int var3, int var4);

    Turtle getTurtle(int var1, int var2, int var3);
}
