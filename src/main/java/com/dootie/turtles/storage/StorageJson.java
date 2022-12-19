package com.dootie.turtles.storage;

import com.dootie.turtles.repository.ITurtleRepository;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.InventorySerializer;
import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class StorageJson implements IStorage {
    private ITurtleRepository repository;
    private final File file;

    public StorageJson(ITurtleRepository repository, File file) {
        this.repository = repository;
        this.file = file;
    }

    public void read() throws StorageException {
        if (this.file.exists()) {
            try {
                String data = Joiner.on("").join(Files.readAllLines(this.file.toPath(), StandardCharsets.UTF_8));
                JSONArray turtles = (JSONArray) JSONValue.parse(data);

                for (JSONObject turtleObject : turtles) {
                    String world = (String) turtleObject.get("world");
                    long x = (Long) turtleObject.get("x");
                    long y = (Long) turtleObject.get("y");
                    long z = (Long) turtleObject.get("z");
                    Turtle turtle = this.repository.createTurtle(new Location(Bukkit.getWorld(world), x, y, z));
                    turtle.setInventory(InventorySerializer.fromBase64((String) turtleObject.get("inventory")));
                }

            } catch (IOException e) {
                throw new StorageException(e.toString());
            }
        }
    }

    public void write() throws StorageException {
        JSONArray turtles = new JSONArray();

        for (Turtle turtle : this.repository.getTurtles()) {
            JSONObject turtleObject = new JSONObject();
            turtleObject.put("world", turtle.getWorld().getName());
            turtleObject.put("x", turtle.getX());
            turtleObject.put("y", turtle.getY());
            turtleObject.put("z", turtle.getZ());
            turtleObject.put("inventory", InventorySerializer.toBase64(turtle.getInventory()));
            turtles.add(turtleObject);
        }

        try {
            Files.createDirectories(this.file.toPath().subpath(0, this.file.toPath().getNameCount() - 1));
            Files.write(this.file.toPath(), turtles.toJSONString().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException var5) {
            throw new StorageException(var5.toString());
        }
    }

    @Override
    public void save() {
        try {
            write();
        } catch (StorageException e) {
            getLogger().log(Level.SEVERE, "Could not save turtles to storage: {0}", e);
        }
    }
}
