package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;

public abstract class Command {
    public Command() {
    }

    public abstract void execute(Executor executor, String[] args);
}
