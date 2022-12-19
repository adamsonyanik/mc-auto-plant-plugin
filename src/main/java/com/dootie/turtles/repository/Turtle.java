package com.dootie.turtles.repository;

import com.dootie.turtles.executer.Executor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Iterator;

public class Turtle implements InventoryHolder {
    public static final int SCRIPT_SLOT = 0;
    private World world;
    private int x;
    private int y;
    private int z;
    private Inventory inventory;
    private final ITurtleRepository repository;

    private Executor executor;

    Turtle(Location location, Inventory inventory, ITurtleRepository repository) {
        this.world = location.getWorld();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.inventory = inventory;
        this.repository = repository;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public void removeExecutor() {
        this.executor = null;
        this.sendInfo("Turtle stopped the script.");
    }

    public boolean isBusy() {
        return this.executor != null;
    }

    public ITurtleRepository getTurtleRepository() {
        return this.repository;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
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
        return this.inventory.getItem(SCRIPT_SLOT);
    }

    public Location getLocation() {
        return new Location(world, this.x, this.y, this.z);
    }

    public String getScript() {
        String script = "";
        ItemStack itemStack = this.getScriptSlot();
        if (itemStack != null && itemStack.getType() == Material.WRITABLE_BOOK) {
            BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();

            String page;
            for (Iterator<String> var4 = bookMeta.getPages().iterator(); var4.hasNext(); script = script + page) {
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
        if (this.executor != null && this.executor.getPlayer() != null) {
            this.executor.getPlayer().sendMessage(ChatColor.BOLD + "[Turtle]" + ChatColor.RESET + " [" + this.x + ", " + this.y + ", " + this.z + "] " + message);
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

    public void stop() {
        if (this.executor != null) {
            this.executor.stop();
        }
    }
}
