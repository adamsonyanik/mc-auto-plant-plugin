package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executer;
import com.dootie.turtles.util.DirectionHelper;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;

public class CommandSuck extends Command {
    Executer parser;
    String[] arguments;

    public CommandSuck() {
    }

    public void execute(Executer parser, String[] arguments) {
        this.parser = parser;
        this.arguments = arguments;
        if (this.arguments.length == 1) {
            String direction = this.arguments[0];
            if (!DirectionHelper.isValidDirection(direction)) {
                parser.getTurtle().sendError("Invalid direction given: " + direction + ".");
            }

            int[] coordinates = DirectionHelper.getCoordinates(direction, this.parser.getTurtle().getX(), this.parser.getTurtle().getY(), this.parser.getTurtle().getZ());
            Iterator var5 = this.parser.getWorld().getEntities().iterator();

            while (true) {
                Entity e;
                Item item;
                Block blockIsIn;
                do {
                    do {
                        do {
                            do {
                                if (!var5.hasNext()) {
                                    return;
                                }

                                e = (Entity) var5.next();
                            } while (e.getType() != EntityType.DROPPED_ITEM);

                            item = (Item) e;
                            blockIsIn = this.parser.getWorld().getBlockAt(e.getLocation());
                        } while (blockIsIn.getX() != coordinates[0]);
                    } while (blockIsIn.getY() != coordinates[1]);
                } while (blockIsIn.getZ() != coordinates[2]);

                e.remove();
                HashMap excess = this.parser.getTurtle().getInventory().addItem(new ItemStack[]{item.getItemStack()});
                Iterator var10 = excess.entrySet().iterator();

                while (var10.hasNext()) {
                    Object excessItem = var10.next();
                    this.parser.getWorld().dropItem(this.parser.getTurtle().getLocation(this.parser.getWorld()), (ItemStack) excessItem);
                }
            }
        }
    }
}
