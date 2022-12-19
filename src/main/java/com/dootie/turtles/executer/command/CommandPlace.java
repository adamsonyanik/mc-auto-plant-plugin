package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.DirectionHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CommandPlace extends Command {
    private static final Map<Material, Material> ITEM_CONVERSIONS = new HashMap<>();

    static {
        ITEM_CONVERSIONS.put(Material.POTATO, Material.POTATOES);
        ITEM_CONVERSIONS.put(Material.CARROT, Material.CARROTS);
        ITEM_CONVERSIONS.put(Material.WHEAT_SEEDS, Material.WHEAT);
        ITEM_CONVERSIONS.put(Material.BEETROOT_SEEDS, Material.BEETROOTS);
        ITEM_CONVERSIONS.put(Material.MELON_SEEDS, Material.MELON_STEM);
        ITEM_CONVERSIONS.put(Material.PUMPKIN_SEEDS, Material.PUMPKIN_STEM);
    }

    public void execute(Executor executor, String[] args) {
        if (args.length == 1) {
            Turtle turtle = executor.getTurtle();
            ItemStack stack = executor.getTurtle().getInventory().getItem(executor.getCurrentSelectedSlot());
            String direction = args[0];
            if (!DirectionHelper.isValidDirection(direction)) {
                executor.getTurtle().sendError("Invalid direction given: " + direction + ".");
            }

            int[] coordinates = DirectionHelper.getCoordinates(direction, turtle.getX(), turtle.getY(), turtle.getZ());
            Block block = executor.getTurtle().getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]);
            if (block.getType() == Material.AIR && stack != null && (stack.getType().isBlock() || ITEM_CONVERSIONS.containsKey(stack.getType()))) {
                Material typeToPlace = stack.getType();
                if (ITEM_CONVERSIONS.containsKey(stack.getType())) {
                    typeToPlace = ITEM_CONVERSIONS.get(stack.getType());
                }

                block.setType(typeToPlace);
                stack.setAmount(stack.getAmount() - 1);
                if (stack.getAmount() <= 0) {
                    turtle.getInventory().setItem(executor.getCurrentSelectedSlot(), null);
                }
            }

        }
    }
}
