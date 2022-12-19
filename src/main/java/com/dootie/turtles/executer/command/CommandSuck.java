package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;
import com.dootie.turtles.util.DirectionHelper;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;

public class CommandSuck extends Command {

    public void execute(Executor executor, String[] args) {
        if (args.length == 1) {
            String direction = args[0];
            if (!DirectionHelper.isValidDirection(direction)) {
                executor.getTurtle().sendError("Invalid direction given: " + direction + ".");
            }

            int[] coordinates = DirectionHelper.getCoordinates(direction, executor.getTurtle().getX(), executor.getTurtle().getY(), executor.getTurtle().getZ());
            Iterator<Entity> var5 = executor.getTurtle().getWorld().getEntities().iterator();

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

                                e = var5.next();
                            } while (e.getType() != EntityType.DROPPED_ITEM);

                            item = (Item) e;
                            blockIsIn = executor.getTurtle().getWorld().getBlockAt(e.getLocation());
                        } while (blockIsIn.getX() != coordinates[0]);
                    } while (blockIsIn.getY() != coordinates[1]);
                } while (blockIsIn.getZ() != coordinates[2]);

                e.remove();
                HashMap<Integer, ItemStack> excess = executor.getTurtle().getInventory().addItem(item.getItemStack());

                for (Object excessItem : excess.entrySet()) {
                    executor.getTurtle().getWorld().dropItem(executor.getTurtle().getLocation(), (ItemStack) excessItem);
                }
            }
        }
    }
}
