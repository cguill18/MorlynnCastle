package MorlynnCastle.model.item;

public class ScaleMail extends Armor {

    private static final String IMG_SCALEMAIL = "armor.png";
    private final static int DEFAULT_ARMOR_CLASS = 15;

    public ScaleMail(String name,String description) {

        super(name, description, ScaleMail.DEFAULT_ARMOR_CLASS);
        this.setImage(IMG_SCALEMAIL);
    }

    public ScaleMail(String name, String description, int armorClassBonus) {
        super(name, description, ScaleMail.DEFAULT_ARMOR_CLASS + armorClassBonus);
        this.setImage(IMG_SCALEMAIL);
    }

    public ScaleMail(String name,String description, int posx, int posy) {
        super(name, description, ScaleMail.DEFAULT_ARMOR_CLASS, posx, posy);
        this.setImage(IMG_SCALEMAIL);
    }
}
