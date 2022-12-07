package com.dootie.turtles.repository;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class TurtleRepositoryWorld implements ITurtleRepository {
    private List<Turtle> turtles = new ArrayList();

    public TurtleRepositoryWorld() {
    }

    public List<Turtle> getTurtles() {
        return this.turtles;
    }

    public void removeTurtle(int x, int y, int z) {
        Turtle turtle = this.getTurtle(x, y, z);
        if (turtle != null) {
            this.turtles.remove(turtle);
        }

    }

    public Turtle createTurtle(UUID owner, int x, int y, int z) {
        Turtle turtle = new Turtle(x, y, z, owner, Bukkit.createInventory(null, 27, "Turtle"), this);
        this.turtles.add(turtle);
        return turtle;
    }

    public Turtle getTurtle(int x, int y, int z) {
        Iterator<Turtle> var4 = this.turtles.iterator();

        Turtle turtle;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            turtle = var4.next();
        } while (turtle.getX() != x || turtle.getY() != y || turtle.getZ() != z);

        return turtle;
    }
}
