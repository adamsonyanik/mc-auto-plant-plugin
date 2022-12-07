package com.dootie.turtles.executer.placeholders;

import com.dootie.turtles.repository.Turtle;
import org.bukkit.inventory.ItemStack;

public class SlotPlaceholder extends Placeholder {
    public SlotPlaceholder() {
    }

    public String replace(Turtle turtle, String placeholder) {
        ItemStack item = turtle.getInventory().getItem(turtle.executer.getCurrentSelectedSlot());
        if (placeholder.equals("%slot type%")) {
            return item.getType().name();
        } else if (placeholder.equals("%slot amount%")) {
            return Integer.toString(item.getAmount());
        } else if (placeholder.equals("%slot damage%")) {
            return Integer.toString(item.getDurability());
        } else {
            return placeholder.equals("%slot maxdamage%") ? Integer.toString(item.getType().getMaxDurability()) : "ERROR";
        }
    }
}
