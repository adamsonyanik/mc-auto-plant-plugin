package io.github.adamson;

import com.dootie.turtles.executer.CommandResolver;
import com.dootie.turtles.executer.PlaceholderResolver;
import com.dootie.turtles.executer.command.*;
import com.dootie.turtles.executer.placeholders.BlockTypePlaceholder;
import com.dootie.turtles.repository.ITurtleRepository;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.repository.TurtleRepositoryWorld;
import com.dootie.turtles.storage.IStorage;
import com.dootie.turtles.storage.StorageException;
import com.dootie.turtles.storage.StorageJson;
import com.dootie.turtles.util.Skull;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

public class MCAutoPlant extends JavaPlugin implements Listener {
    public static Plugin plugin;
    public static ITurtleRepository repository;
    public static IStorage storage;
    public static ShapedRecipe recipeTurtle;
    public static ItemStack turtleItem;

    public void onEnable() {
        this.enable();
    }

    public void enable() {
        plugin = this;
        repository = new TurtleRepositoryWorld();
        storage = new StorageJson(repository, new File("plugins/AutoPlant/turtles.json"));
        turtleItem = Skull.getCustomTextureHead("Turtle", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTIyODRlMTMyYmZkNjU5YmM2YWRhNDk3YzRmYTMwOTRjZDkzMjMxYTZiNTA1YTEyY2U3Y2Q1MTM1YmE4ZmY5MyJ9fX0=");
        recipeTurtle = new ShapedRecipe(turtleItem);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.addRecipes();
        this.readStorage();
        this.registerCommands();
        this.registerPlaceholders();
        this.getLogger().info("[Turtles] Turtles is enabled.");
    }

    private void addRecipes() {
        System.out.println("[Turtles] Adding recipes...");
        recipeTurtle.shape("sns", "srs", "scs").setIngredient('s', Material.STONE).setIngredient('n', Material.NETHER_STAR).setIngredient('r', Material.REDSTONE_BLOCK).setIngredient('c', Material.CHEST);
        this.getServer().addRecipe(recipeTurtle);
        System.out.println("[Turtles] Added recipes.");
    }

    private void readStorage() {
        System.out.println("[Turtles] Loading turtles...");

        try {
            storage.read();
        } catch (StorageException e) {
            this.getLogger().log(Level.SEVERE, "Could not read turtles to storage: {0}", e);
        }

        System.out.println("[Turtles] Turtles loaded.");
    }

    private void registerCommands() {
        System.out.println("[Turtles] Registering commands...");
        CommandResolver.commands.put("dig", new CommandDig());
        CommandResolver.commands.put("drop", new CommandDrop());
        CommandResolver.commands.put("move", new CommandMove());
        CommandResolver.commands.put("place", new CommandPlace());
        CommandResolver.commands.put("print", new CommandPrint());
        CommandResolver.commands.put("select", new CommandSelect());
        CommandResolver.commands.put("suck", new CommandSuck());
        CommandResolver.commands.put("goto", new CommandGoto());
        CommandResolver.commands.put("#", new CommandComment());
        System.out.println("[Turtles] Commands registered.");
    }

    private void registerPlaceholders() {
        System.out.println("[Turtles] Registering placeholders...");
        PlaceholderResolver.placeholders.put("%block north type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block east type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block south type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block west type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block up type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block down type%", new BlockTypePlaceholder());

        System.out.println("[Turtles] Placeholders registered.");
    }

    public void onDisable() {
        storage.save();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equals("turtles")) {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_PURPLE + "Turtles plugin by Dootie");
            sender.sendMessage(ChatColor.BLUE + "Plugin avaiable in: " + ChatColor.GRAY + "https://www.spigotmc.org/resources/16666/");
            sender.sendMessage(ChatColor.BLUE + "Github: " + ChatColor.GRAY + "https://github.com/Dootie/Turtles");
            sender.sendMessage(ChatColor.BLUE + "Wiki: " + ChatColor.GRAY + "https://www.spigotmc.org/wiki/turtles/");
            sender.sendMessage(ChatColor.BLUE + "Twitter: " + ChatColor.GRAY + "https://twitter.com/Dootie_");
        }

        return true;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getItemInHand() != null && e.getItemInHand().getItemMeta() != null && e.getItemInHand().getItemMeta().hasDisplayName() && e.getItemInHand().getItemMeta().getDisplayName().equals(turtleItem.getItemMeta().getDisplayName())) {
            Location location = e.getBlockPlaced().getLocation();
            repository.createTurtle(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            e.getPlayer().sendMessage("The turtle is succesfully placed.");
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Location location = e.getBlock().getLocation();
        Turtle turtle = repository.getTurtle(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (turtle != null) {
            e.setCancelled(true);
            try {
                turtle.executer.stop();
            } catch (NullPointerException ignored) {
            }

            repository.removeTurtle(turtle.getX(), turtle.getY(), turtle.getZ());
            World world = e.getBlock().getWorld();
            world.getBlockAt(turtle.getX(), turtle.getY(), turtle.getZ()).setType(Material.AIR);
            ItemStack[] inventoryStacks = turtle.getInventory().getContents();

            for (ItemStack item : inventoryStacks) {
                if (item != null) {
                    world.dropItemNaturally(turtle.getLocation(e.getBlock().getWorld()), item.clone());
                }
            }

            if (e.getPlayer() == null || e.getPlayer() == null || e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                world.dropItemNaturally(turtle.getLocation(world), turtleItem.clone());
            }

            if (e.getPlayer() != null) {
                e.getPlayer().sendMessage("You broke a turtle.");
            }
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Location location;
        Turtle turtle;
        if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && !(e.getItem() != null && e.getPlayer().isSneaking()) && (turtle = repository.getTurtle((location = e.getClickedBlock().getLocation()).getBlockX(), location.getBlockY(), location.getBlockZ())) != null) {
            e.getPlayer().openInventory(turtle.getInventory());
            e.setUseInteractedBlock(Event.Result.ALLOW);
            e.setUseItemInHand(Event.Result.DENY);
        }
    }

    @EventHandler
    public void onEntityExploded(EntityExplodeEvent e) {
        for (Block block : e.blockList()) {
            repository.removeTurtle(block.getX(), block.getY(), block.getZ());
        }
    }

    @EventHandler
    public void onWorldSaveEvent(WorldSaveEvent e) {
        storage.save();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        Inventory inv = holder.getInventory();

        if (event.getView().getTitle().equals("Turtle")) {
            if (event.getSlot() >= 27) {
                event.setCancelled(true);

                if (event.getSlot() == 27) {
                    ItemStack item = new ItemStack(Material.LIME_WOOL);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§fStart");
                    meta.setLore(List.of("O holy stone", "Ye who protects me from evil"));
                    item.setItemMeta(meta);
                    event.getInventory().setItem(26, item);
                }
            }
        }
    }
}

/*
package com.dootie.turtles;

class Turtles$1 implements Runnable {
    Turtles$1(Turtles this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.enable();
    }
}

package com.dootie.turtles;

class Turtles$2 implements Runnable {
    Turtles$2(Turtles this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        Turtles.access$000(this.this$0);
    }
}

package com.dootie.turtles;

class Turtles$3 implements Runnable {
    Turtles$3(Turtles this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        Turtles.access$100(this.this$0);
    }
}

package com.dootie.turtles;

class Turtles$4 implements Runnable {
    Turtles$4(Turtles this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        Turtles.access$200(this.this$0);
    }
}

package com.dootie.turtles;

class Turtles$5 implements Runnable {
    Turtles$5(Turtles this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        Turtles.access$300(this.this$0);
    }
}
 */


/*
public class MCAutoPlant extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DispenseEventListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
*/
