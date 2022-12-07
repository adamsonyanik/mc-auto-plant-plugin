package com.dootie.turtles.executer.placeholders;

import com.dootie.turtles.repository.Turtle;

public abstract class Placeholder {
    public Placeholder() {
    }

    public abstract String replace(Turtle var1, String var2);
}
