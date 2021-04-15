package MorlynnCastle.model.item;

public class Key extends Item implements Usable {

    public Key(String name, String description) {
        super(name, true, description);
    }

    public Key(String name, String description, int posx, int posy) {
        super(name, true, description, posx, posy);
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