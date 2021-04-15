package MorlynnCastle.model.item;

public class Gold extends Item {
    
    public Gold(String name, String description){
        super(name, true, description);
    }

    public Gold(String name, String description, int posx, int posy) {
        super(name, true, description, posx, posy);
    }
}
