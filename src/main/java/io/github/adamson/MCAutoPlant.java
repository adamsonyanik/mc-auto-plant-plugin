package io.github.adamson;

import org.bukkit.plugin.java.JavaPlugin;

public class MCAutoPlant extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DispenseEventListener(), this);
    }
}
