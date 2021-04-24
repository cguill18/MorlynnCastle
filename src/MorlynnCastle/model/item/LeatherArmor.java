package MorlynnCastle.model.item;

public class LeatherArmor extends Armor {

    private static final String IMG_LARMOR = "leather_armor.png";
    private final static int DEFAULT_ARMOR_CLASS = 13;


    public LeatherArmor(String name, String description) {

        super(name, description, LeatherArmor.DEFAULT_ARMOR_CLASS);
        this.setImage(IMG_LARMOR);
    }

    public LeatherArmor(String name, String description, int armorClassBonus) {
        super(name,  description, LeatherArmor.DEFAULT_ARMOR_CLASS + armorClassBonus);
        this.setImage(IMG_LARMOR);
    }

    public LeatherArmor(String name, String description, int posx, int posy) {
        super(name, description, LeatherArmor.DEFAULT_ARMOR_CLASS, posx, posy);
        this.setImage(IMG_LARMOR);
    }


}
