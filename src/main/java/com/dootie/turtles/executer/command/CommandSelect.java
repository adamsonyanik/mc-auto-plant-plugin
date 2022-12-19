package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;

public class CommandSelect extends Command {

    public void execute(Executor executor, String[] args) {
        if (args.length == 1) {
            int slot = 0;

            try {
                slot = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                executor.getTurtle().sendMessage("Invalid number: " + args[0] + ".");
            }

            if (slot < 0 || slot > executor.getTurtle().getInventory().getSize() - 1) {
                executor.getTurtle().sendError("Slot out of range: " + slot + ".");
            }

            executor.setCurrentSelectedSlot(slot);
        }
    }
}
