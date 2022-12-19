package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;
import com.dootie.turtles.util.DirectionHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class CommandDig extends Command {

    public void execute(Executor executor, String[] args) {
        if (args.length == 1) {
            String direction = args[0];
            if (!DirectionHelper.isValidDirection(direction)) {
                executor.getTurtle().sendError("Invalid direction given: " + direction + ".");
            }

            int[] coordinates = DirectionHelper.getCoordinates(direction, executor.getTurtle().getX(), executor.getTurtle().getY(), executor.getTurtle().getZ());
            Block block = executor.getTurtle().getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]);
            executor.getTurtle().getTurtleRepository().removeTurtle(coordinates[0], coordinates[1], coordinates[2]);
            ItemStack tool = executor.getTurtle().getInventory().getItem(executor.getCurrentSelectedSlot());
            if (tool != null && (tool.getType() == Material.WOODEN_AXE || tool.getType() == Material.WOODEN_SWORD || tool.getType() == Material.WOODEN_PICKAXE || tool.getType() == Material.WOODEN_SHOVEL || tool.getType() == Material.WOODEN_HOE || tool.getType() == Material.STONE_AXE || tool.getType() == Material.STONE_SWORD || tool.getType() == Material.STONE_PICKAXE || tool.getType() == Material.STONE_SHOVEL || tool.getType() == Material.STONE_HOE || tool.getType() == Material.IRON_AXE || tool.getType() == Material.IRON_SWORD || tool.getType() == Material.IRON_PICKAXE || tool.getType() == Material.IRON_SHOVEL || tool.getType() == Material.IRON_HOE || tool.getType() == Material.DIAMOND_AXE || tool.getType() == Material.DIAMOND_SWORD || tool.getType() == Material.DIAMOND_PICKAXE || tool.getType() == Material.DIAMOND_SHOVEL || tool.getType() == Material.DIAMOND_HOE || tool.getType() == Material.GOLDEN_AXE || tool.getType() == Material.GOLDEN_SWORD || tool.getType() == Material.GOLDEN_PICKAXE || tool.getType() == Material.GOLDEN_SHOVEL || tool.getType() == Material.GOLDEN_HOE)) {
                tool.setDurability((short) (tool.getDurability() + 1));
                if (tool.getDurability() == tool.getType().getMaxDurability()) {
                    executor.getTurtle().getInventory().setItem(executor.getCurrentSelectedSlot(), null);
                }
            }

            block.breakNaturally(tool);
            BlockState state = block.getState();
            state.setData(new MaterialData(Material.AIR));
            state.update();
        }
    }
}
