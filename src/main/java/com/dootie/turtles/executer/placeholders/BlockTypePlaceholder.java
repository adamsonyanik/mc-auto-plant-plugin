package com.dootie.turtles.executer.placeholders;

import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.DirectionHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockTypePlaceholder extends Placeholder {
    public BlockTypePlaceholder() {
    }

    public String replace(Turtle turtle, String placeholder) {
        return switch (placeholder) {
            case "%block north type%" -> this.getBlockType(turtle, "north");
            case "%block east type%" -> this.getBlockType(turtle, "east");
            case "%block south type%" -> this.getBlockType(turtle, "south");
            case "%block west type%" -> this.getBlockType(turtle, "west");
            case "%block up type%" -> this.getBlockType(turtle, "up");
            case "%block down type%" -> this.getBlockType(turtle, "down");
            default -> Material.AIR.toString();
        };
    }

    public String getBlockType(Turtle turtle, String direction) {
        int[] coordinates = DirectionHelper.getCoordinates(direction, turtle.getX(), turtle.getY(), turtle.getZ());
        Block block = turtle.executer.getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]);
        return block.getType().toString();
    }
}
