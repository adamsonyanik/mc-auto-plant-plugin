package com.dootie.turtles.storage;

import com.dootie.turtles.repository.ITurtleRepository;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.InventorySerializer;
import com.google.common.base.Joiner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class StorageJson implements IStorage {
    private ITurtleRepository repository;
    private File file;

    public StorageJson(ITurtleRepository repository, File file) {
        this.repository = repository;
        this.file = file;
    }

    public void read() throws StorageException {
        if (this.file.exists()) {
            try {
                String data = Joiner.on("").join(Files.readAllLines(this.file.toPath(), Charset.forName("UTF-8")));
                if (data != null) {
                    JSONArray turtles = (JSONArray) JSONValue.parse(data);
                    Iterator var3 = turtles.iterator();

                    while (var3.hasNext()) {
                        Object object = var3.next();
                        JSONObject turtleObject = (JSONObject) object;
                        long x = (Long) turtleObject.get("x");
                        long y = (Long) turtleObject.get("y");
                        long z = (Long) turtleObject.get("z");
                        Turtle turtle = this.repository.createTurtle(UUID.fromString((String) turtleObject.get("owner")), (int) x, (int) y, (int) z);
                        turtle.setInventory(InventorySerializer.fromBase64((String) turtleObject.get("inventory")));
                    }
                }

            } catch (IOException var13) {
                throw new StorageException(var13.toString());
            }
        }
    }

    public void write() throws StorageException {
        JSONArray turtles = new JSONArray();
        Iterator var2 = this.repository.getTurtles().iterator();

        while (var2.hasNext()) {
            Turtle turtle = (Turtle) var2.next();
            JSONObject turtleObject = new JSONObject();
            turtleObject.put("x", turtle.getX());
            turtleObject.put("y", turtle.getY());
            turtleObject.put("z", turtle.getZ());
            turtleObject.put("owner", turtle.getOwner().toString());
            turtleObject.put("inventory", InventorySerializer.toBase64(turtle.getInventory()));
            turtles.add(turtleObject);
        }

        try {
            Files.write(this.file.toPath(), turtles.toJSONString().getBytes());
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
