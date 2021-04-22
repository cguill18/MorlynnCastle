package MorlynnCastle.model.item;

import MorlynnCastle.model.characters.Character;
import javafx.scene.image.Image;

public class Weapon extends Item implements Equipable{

    private static final String IMG_WEAPON = "weapon.png";
    private final int attackPower;

    public Weapon(String name, String description, int attackPower) {
        super(name, true, description);
        this.attackPower = attackPower;
        this.setImage(IMG_WEAPON);
    }

    public Weapon(String name, String description, int attackPower, int posx, int posy) {
        super(name, true, description, posx, posy);
        this.attackPower = attackPower;
        this.setImage(IMG_WEAPON);
    }

    public int getAttackPower() {
        return this.attackPower;
    }


    @Override
    public void equip(Character character) {
        character.equipWeapon(this);
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Attack power : " + this.attackPower);
    }
}
