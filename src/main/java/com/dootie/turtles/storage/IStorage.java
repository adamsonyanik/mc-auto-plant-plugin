package com.dootie.turtles.storage;

public interface IStorage {
    void read() throws StorageException;

    void write() throws StorageException;

    /**
     * IStorage.write() with handled Exception
     */
    void save();
}
