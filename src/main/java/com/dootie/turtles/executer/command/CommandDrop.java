package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;
import com.dootie.turtles.repository.Turtle;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class CommandDrop extends Command {

    public void execute(Executor parser, String[] arguments) {
        boolean dropWholeStack = arguments.length == 1 && arguments[0].equals("stack");

        Turtle turtle = parser.getTurtle();
        ItemStack stack = parser.getTurtle().getInventory().getItem(parser.getCurrentSelectedSlot());
        if (stack != null) {
            ItemStack toDrop = stack.clone();
            toDrop.setAmount(dropWholeStack ? stack.getAmount() : 1);
            parser.getTurtle().getWorld().dropItem(new Location(parser.getTurtle().getWorld(), turtle.getX(), turtle.getY() - 1, turtle.getZ()), toDrop);
            stack.setAmount(stack.getAmount() - 1);
            if (stack.getAmount() <= 0 || dropWholeStack) {
                turtle.getInventory().setItem(parser.getCurrentSelectedSlot(), null);
            }
        }

    }
}
