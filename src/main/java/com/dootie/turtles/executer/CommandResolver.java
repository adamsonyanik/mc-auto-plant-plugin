package com.dootie.turtles.executer;

import com.dootie.turtles.executer.command.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandResolver {
    private final String name;
    private final String[] arguments;
    public static final Map<String, Command> commands = new HashMap();

    public CommandResolver(String name, String[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public Command resolve(Executor parser) {
        return (Command) commands.get(this.name);
    }
}
