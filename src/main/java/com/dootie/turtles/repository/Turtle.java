package com.dootie.turtles.repository;

import com.dootie.turtles.executer.Executer;
import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Iterator;
import java.util.UUID;

public class Turtle implements InventoryHolder {
    public static final int SCRIPT_SLOT = 0;
    private int x;
    private int y;
    private int z;
    private boolean busy;
    private UUID owner;
    private Inventory inventory;
    private final ITurtleRepository repository;
    public Executer executer;

    Turtle(int x, int y, int z, Inventory inventory, ITurtleRepository repository) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.inventory = inventory;
        this.repository = repository;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isBusy() {
        return this.busy;
    }

    public ITurtleRepository getTurtleRepository() {
        return this.repository;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ItemStack getScriptSlot() {
        return this.inventory.getItem(0);
    }

    public Location getLocation(World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    public String getScript() {
        String script = "";
        ItemStack itemStack = this.getScriptSlot();
        if (itemStack != null && itemStack.getType() == Material.WRITABLE_BOOK) {
            BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();

            String page;
            for (Iterator var4 = bookMeta.getPages().iterator(); var4.hasNext(); script = script + page) {
                page = (String) var4.next();
            }
        }

        for (int a = 0; a < 16; ++a) {
            script = script.replace("§" + Integer.toHexString(a), "");
        }

        script = script.replace("\n", "");
        return script + "print §aTurtle finished the script.";
    }

    public void sendMessage(String message) {
        if (Bukkit.getPlayer(this.owner) != null) {
            Bukkit.getPlayer(this.owner).sendMessage(ChatColor.BOLD + "[Turtle]" + ChatColor.RESET + " [" + this.x + ", " + this.y + ", " + this.z + "] " + message);
        }

    }

    public void sendInfo(String info) {
        this.sendMessage(ChatColor.BLUE + info);
    }

    public void sendSuccess(String success) {
        this.sendMessage(ChatColor.GREEN + success);
    }

    public void sendError(String error) {
        this.sendMessage(ChatColor.RED + error);
    }
}
