package MorlynnCastle.model.item;

public class Dagger extends Weapon{

    private final static int DEFAULT_ATTACK_POWER = 4;

    public Dagger(String name, String description) {
        super(name, description, Dagger.DEFAULT_ATTACK_POWER);
    }

    public Dagger(String name, String description, int attackPowerBonus) {
        super(name, description, Dagger.DEFAULT_ATTACK_POWER + attackPowerBonus);
    }

    public Dagger(String description, int posx, int posy, String name, int attackPower) {
        super(description, posx, posy, name, attackPower);
    }
}
