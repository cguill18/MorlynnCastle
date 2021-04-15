package MorlynnCastle.model.item;

public class Gold extends Item {
    
    public Gold(String name, String description){
        super(name, true, description);
    }

    public Gold(String description, int posx, int posy, String name, boolean takable) {
        super(description, posx, posy, name, takable);
    }
}
