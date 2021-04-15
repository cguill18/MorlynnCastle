package MorlynnCastle.model.item;

import MorlynnCastle.model.space.Interaction;

public abstract class Item extends Interaction {

    private final String name;
    private final boolean takable;

    public Item(String name, boolean takable, String description) {
        super(description);
        this.name = name;
        this.takable = takable;
    }

    public Item(String name, boolean takable, String description, int posx, int posy) {
        super(description, posx, posy);
        this.name = name;
        this.takable = takable;
    }

    public String getName() {
        return this.name;
    }

    public boolean isTakable() {
        return this.takable;
    }
}