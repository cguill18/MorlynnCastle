package MorlynnCastle.model.item;

public class Key extends Item implements Usable {
    private static final String IMG_KEY = "key.png";

    public Key(String name, String description) {
        super(name, true, description);
        this.setImage(IMG_KEY);
    }

    public Key(String name, String description, int posx, int posy) {

        super(name, true, description, posx, posy);
        this.setImage(IMG_KEY);
    }

    @Override
    public void use() {
        System.out.println("You can't use this alone.");
    }

    @Override
    public void use(Receiver obj) throws ClassCastException, NullPointerException {
        obj.receive(this);
    }
}