package com.dootie.turtles.executer.placeholders;

import com.dootie.turtles.repository.Turtle;
import org.bukkit.Bukkit;

public class OwnerPlaceholder extends Placeholder {
    public OwnerPlaceholder() {
    }

    public String replace(Turtle turtle, String placeholder) {
        return switch (placeholder) {
            case "%owner name%" -> Bukkit.getPlayer(turtle.getOwner()).getName();
            case "%owner uuid%" -> turtle.getOwner().toString();
            default -> "";
        };
    }
}
