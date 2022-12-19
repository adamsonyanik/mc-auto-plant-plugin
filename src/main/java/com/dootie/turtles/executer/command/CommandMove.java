package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.DirectionHelper;
import io.github.adamson.Turtles;
import org.bukkit.Material;

public class CommandMove extends Command {

    public void execute(Executor executor, String[] args) {
        if (args.length == 1) {
            Turtle turtle = executor.getTurtle();
            String direction = args[0];
            if (!DirectionHelper.isValidDirection(direction)) {
                executor.getTurtle().sendError("Invalid direction given: " + direction + ".");
            }

            int[] coordinates = DirectionHelper.getCoordinates(direction, turtle.getX(), turtle.getY(), turtle.getZ());
            if (executor.getTurtle().getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]).getType() == Material.AIR) {
                executor.getTurtle().getWorld().getBlockAt(turtle.getX(), turtle.getY(), turtle.getZ()).setType(Material.AIR);
                turtle.setX(coordinates[0]);
                turtle.setY(coordinates[1]);
                turtle.setZ(coordinates[2]);
                executor.getTurtle().getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]).setType(Turtles.turtleItem.getType());
            }
        }
    }
}
