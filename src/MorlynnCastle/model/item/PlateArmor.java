package MorlynnCastle.model.item;

public class PlateArmor extends Armor {

    private static final String IMG_PARMOR = "plate_armor.png";
    private final static int DEFAULT_ARMOR_CLASS = 18;

    public PlateArmor(String name, String description) {

        super(name, description, PlateArmor.DEFAULT_ARMOR_CLASS);
        this.setImage(IMG_PARMOR);
    }

    public PlateArmor(String name, String description, int armorClassBonus) {
        super(name, description, PlateArmor.DEFAULT_ARMOR_CLASS + armorClassBonus);
        this.setImage(IMG_PARMOR);
    }

    public PlateArmor(String name, String description, int posx, int posy) {
        super(name, description, PlateArmor.DEFAULT_ARMOR_CLASS, posx, posy);
        this.setImage(IMG_PARMOR);
    }
}
