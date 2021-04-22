package MorlynnCastle.model.item;

public class Gold extends Item {

    private static final String IMG_GOLD = "gold.png";
    
    public Gold(String name, String description){

        super(name, true, description);
        this.setImage(IMG_GOLD);
    }

    public Gold(String name, String description, int posx, int posy) {

        super(name, true, description, posx, posy);
        this.setImage(IMG_GOLD);
    }
}
