package MorlynnCastle.model.item;

public class PlateArmor extends Armor {

    private final static int DEFAULT_ARMOR_CLASS = 18;

    public PlateArmor(String name, String description) {
        super(name, description, PlateArmor.DEFAULT_ARMOR_CLASS);
    }

    public PlateArmor(String name, String description, int armorClassBonus) {
        super(name, description, PlateArmor.DEFAULT_ARMOR_CLASS + armorClassBonus);
    }
}
