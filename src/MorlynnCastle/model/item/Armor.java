package MorlynnCastle.model.item;

import MorlynnCastle.model.characters.Character;

public class Armor extends Item implements Equipable {

    private final int armorClass;

    public Armor(String name, String description, int armorClass) {
        super(name, true, description);
        this.armorClass = armorClass;
    }

    public Armor(String description, int posx, int posy, String name, int armorClass) {
        super(description, posx, posy, name, true);
        this.armorClass = armorClass;
    }

    public int getArmorClass() {
        return armorClass;
    }



    @Override
    public void equip(Character character) {
        character.equipArmor(this);
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Armor class : " +  this.armorClass);
    }
}
