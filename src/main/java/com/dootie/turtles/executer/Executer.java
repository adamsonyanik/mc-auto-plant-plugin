package com.dootie.turtles.executer;

import com.dootie.turtles.executer.command.Command;
import com.dootie.turtles.repository.Turtle;
import io.github.adamson.MCAutoPlant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Iterator;

public class Executer {
    private final World world;
    private final Turtle turtle;
    private int currentSelectedSlot = 0;
    private int lineNumber = 0;
    private Executer parser;
    private int task;
    private int speed;
    private int nextLine;

    public Executer(JavaPlugin plugin, World world, Turtle turtle) {
        this.world = world;
        this.turtle = turtle;
        this.speed = 1;
        this.nextLine = 0;
        this.turtle.executer = this;
    }

    public void parse(Player executor) {
        this.lineNumber = 0;
        this.parser = this;
        if (this.getScript() == null) {
            this.turtle.sendError("No script is found.");
        }

        if (!this.turtle.isBusy()) {
            this.turtle.setBusy(true);
            this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MCAutoPlant.plugin, () -> this.execute(executor), 0L, 20 / this.speed);
            this.lineNumber = 0;
        }
    }

    public void execute(Player executor) {
        try {
            this.nextLine = 0;
            String[] lines = this.turtle.getScript().split(";");
            String line = lines[this.lineNumber];

            String placeholder;
            for (Iterator<String> var3 = PlaceholderResolver.placeholders.keySet().iterator(); var3.hasNext(); line = line.replace(placeholder, (new PlaceholderResolver(placeholder)).resolve().replace(this.turtle, placeholder))) {
                placeholder = var3.next();
            }

            String[] parts = line.split(" ");
            Command command = (new CommandResolver(parts[0], Arrays.copyOfRange(parts, 1, parts.length))).resolve(this.parser);
            if (command != null) {
                command.execute(this.parser, Arrays.copyOfRange(parts, 1, parts.length));
            } else {
                this.turtle.setBusy(false);
                this.turtle.sendError("Command not found: " + parts[0] + ".");
                this.turtle.setBusy(false);
                this.stop();
            }

            if (this.lineNumber < lines.length - 1) {
                if (this.nextLine == 0) {
                    ++this.lineNumber;
                } else {
                    this.lineNumber = this.nextLine - 1;
                }
            } else {
                this.turtle.setBusy(false);
                Bukkit.getServer().getScheduler().cancelTask(this.task);
            }
        } catch (IndexOutOfBoundsException e) {
            executor.sendMessage("Error in the script. Missing ';'");
            this.turtle.setBusy(false);
            this.stop();
        }

        if (this.turtle.getLocation(this.world).getBlock().getType() != Material.WITHER_SKELETON_SKULL) {
            this.turtle.setBusy(false);
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

    public World getWorld() {
        return this.world;
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
        this.turtle.sendError("Turtle stopped the script.");
        Bukkit.getServer().getScheduler().cancelTask(this.task);
    }
}

/*
package com.dootie.turtles.executer;

class Executer$1 implements Runnable {
    Executer$1(Executer this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.execute();
    }
}

package com.dootie.turtles.executer;

import java.util.TimerTask;

class Executer$2 extends TimerTask {
    Executer$2(Executer this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.execute();
    }
}
 */
