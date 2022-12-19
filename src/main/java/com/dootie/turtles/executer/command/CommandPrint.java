package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;
import com.google.common.base.Joiner;

public class CommandPrint extends Command {

    public void execute(Executor executor, String[] args) {
        if (args.length >= 1) {
            executor.getTurtle().sendMessage(Joiner.on(" ").join(args));
        }
    }
}
