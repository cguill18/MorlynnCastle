package item;

public class ScaleMail extends Armor {

    private final static int DEFAULT_ARMOR_CLASS = 15;

    public ScaleMail(String name,String description) {
        super(name, description, ScaleMail.DEFAULT_ARMOR_CLASS);
    }

    public ScaleMail(String name, String description, int armorClassBonus) {
        super(name, description, ScaleMail.DEFAULT_ARMOR_CLASS + armorClassBonus);
    }
}
