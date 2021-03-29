package MorlynnCastle.model.space;

import java.io.Serializable;

public abstract class Interaction implements Serializable {

    private final String description;

    public Interaction(String description) {
        this.description = description;
    }

    public void print() {
        System.out.println(this.description);
    }

}