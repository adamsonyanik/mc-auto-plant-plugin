package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executer;
import com.google.common.base.Joiner;

public class CommandPrint extends Command {
    Executer parser;
    String[] arguments;

    public CommandPrint() {
    }

    public void execute(Executer parser, String[] arguments) {
        this.parser = parser;
        this.arguments = arguments;
        if (this.arguments.length >= 1) {
            this.parser.getTurtle().sendMessage(Joiner.on(" ").join(this.arguments));
        }
    }
}
