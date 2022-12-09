package com.dootie.turtles.repository;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public Turtle createTurtle(int x, int y, int z) {
        Turtle turtle = new Turtle(x, y, z, null, this);
        Inventory inventory = Bukkit.createInventory(turtle, 27, "Turtle");
        turtle.setInventory(inventory);
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
