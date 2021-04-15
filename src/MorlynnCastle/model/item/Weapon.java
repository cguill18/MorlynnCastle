package MorlynnCastle.model.item;

import MorlynnCastle.model.characters.Character;

public class Weapon extends Item implements Equipable{

    private final int attackPower;

    public Weapon(String name, String description, int attackPower) {
        super(name, true, description);
        this.attackPower = attackPower;
    }

    public Weapon(String description, int posx, int posy, String name, int attackPower) {
        super(description, posx, posy, name, true);
        this.attackPower = attackPower;
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
