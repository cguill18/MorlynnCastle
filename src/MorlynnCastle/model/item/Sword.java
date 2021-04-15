package MorlynnCastle.model.item;

public class Sword extends Weapon{

    private final static int DEFAULT_ATTACK_POWER = 8;

    public Sword(String name, String description) {
        super(name, description, Sword.DEFAULT_ATTACK_POWER);
    }

    public Sword(String name, String description, int attackPowerBonus) {
        super(name, description, Sword.DEFAULT_ATTACK_POWER + attackPowerBonus);
    }

    public Sword(String description, int posx, int posy, String name) {
        super(description, posx, posy, name, Sword.DEFAULT_ATTACK_POWER);
    }
}
