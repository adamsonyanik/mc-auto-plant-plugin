package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executer;

public abstract class Command {
    public Command() {
    }

    public abstract void execute(Executer var1, String[] var2);
}
