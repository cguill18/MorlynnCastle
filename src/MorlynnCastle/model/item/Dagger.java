package MorlynnCastle.model.item;

public class Dagger extends Weapon{

    private static final String IMG_DAGGER = "weapon.png";
    private final static int DEFAULT_ATTACK_POWER = 4;

    public Dagger(String name, String description) {

        super(name, description, Dagger.DEFAULT_ATTACK_POWER);
        this.setImage(IMG_DAGGER);
    }

    public Dagger(String name, String description, int attackPowerBonus) {
        super(name, description, Dagger.DEFAULT_ATTACK_POWER + attackPowerBonus);
        this.setImage(IMG_DAGGER);
    }

    public Dagger(String name, String description, int posx, int posy) {
        super(name, description, Dagger.DEFAULT_ATTACK_POWER, posx, posy);
        this.setImage(IMG_DAGGER);
    }
}
