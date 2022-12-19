package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executor;

public class CommandGoto extends Command {

    public void execute(Executor parser, String[] arguments) {
        if (arguments.length == 1) {
            parser.setLineNumber(Integer.parseInt(arguments[0]));
        }

    }
}
