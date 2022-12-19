package com.dootie.turtles.executer;

import com.dootie.turtles.executer.command.Command;
import com.dootie.turtles.repository.Turtle;
import io.github.adamson.MCAutoPlant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Arrays;

public class Executor {
    private final Turtle turtle;
    private final Player player;
    private int currentSelectedSlot = 0;
    private int lineNumber = 0;
    private int task;
    private int speed;
    private int nextLine;

    public Executor(Turtle turtle, Player player) {
        this.turtle = turtle;
        this.player = player;
        this.speed = 1;
        this.nextLine = 0;
    }

    public void parse() {
        this.lineNumber = 0;
        if (this.getScript() == null) {
            this.turtle.sendError("No script is found.");
        }

        if (!this.turtle.isBusy()) {
            this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MCAutoPlant.getPlugin(MCAutoPlant.class), this::execute, 0L, 20 / this.speed);
        }
    }

    public void execute() {
        this.turtle.setExecutor(this);
        this.lineNumber = 0;
        try {
            this.nextLine = 0;
            String[] lines = this.turtle.getScript().split(";");
            String line = lines[this.lineNumber];

            String[] parts = line.split(" ");
            Command command = (new CommandResolver(parts[0], Arrays.copyOfRange(parts, 1, parts.length))).resolve(this);
            if (command != null) {
                command.execute(this, Arrays.copyOfRange(parts, 1, parts.length));
            } else {
                this.turtle.sendError("Command not found: " + parts[0] + ".");
                this.stop();
            }

            if (this.lineNumber < lines.length - 1) {
                if (this.nextLine == 0) {
                    ++this.lineNumber;
                } else {
                    this.lineNumber = this.nextLine - 1;
                }
            } else {
                this.stop();
            }
        } catch (IndexOutOfBoundsException e) {
            player.sendMessage("Error in the script. Missing ';'");
            this.stop();
        }

        if (this.turtle.getLocation().getBlock().getType() != Material.WITHER_SKELETON_SKULL) {
            this.stop();
        }

    }

    public void setCurrentSelectedSlot(int currentSelectedSlot) {
        this.currentSelectedSlot = currentSelectedSlot;
    }

    public int getCurrentSelectedSlot() {
        return this.currentSelectedSlot;
    }

    public void setLineNumber(int line) {
        this.nextLine = line + 1;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public Turtle getTurtle() {
        return this.turtle;
    }

    @Nullable
    public Player getPlayer() {
        return this.player;
    }

    public String getScript() {
        return this.turtle.getScript();
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void stop() {
        if (getTurtle().isBusy()) return;

        this.turtle.removeExecutor();
        Bukkit.getServer().getScheduler().cancelTask(this.task);
    }
}
