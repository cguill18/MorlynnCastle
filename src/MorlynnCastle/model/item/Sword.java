package MorlynnCastle.model.item;

public class Sword extends Weapon{

    private static final String IMG_SWORD = "weapon.png";
    private final static int DEFAULT_ATTACK_POWER = 8;

    public Sword(String name, String description) {

        super(name, description, Sword.DEFAULT_ATTACK_POWER);
        this.setImage(IMG_SWORD);
    }

    public Sword(String name, String description, int attackPowerBonus) {
        super(name, description, Sword.DEFAULT_ATTACK_POWER + attackPowerBonus);
        this.setImage(IMG_SWORD);
    }

    public Sword(String name, String description, int posx, int posy) {
        super(name, description, Sword.DEFAULT_ATTACK_POWER, posx, posy);
        this.setImage(IMG_SWORD);
    }
}
