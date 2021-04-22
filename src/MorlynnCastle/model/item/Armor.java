package MorlynnCastle.model.item;

import MorlynnCastle.model.characters.Character;

public class Armor extends Item implements Equipable {

    private static final String IMG_ARMOR = "armor.png";
    private final int armorClass;

    public Armor(String name, String description, int armorClass) {
        super(name, true, description);
        this.armorClass = armorClass;
        this.setImage(IMG_ARMOR);
    }

    public Armor(String name, String description, int armorClass, int posx, int posy) {
        super(name, true, description, posx, posy);
        this.armorClass = armorClass;
        this.setImage(IMG_ARMOR);
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
