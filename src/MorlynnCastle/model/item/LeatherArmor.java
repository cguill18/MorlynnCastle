package item;

public class LeatherArmor extends Armor {

    private final static int DEFAULT_ARMOR_CLASS = 13;


    public LeatherArmor(String name, String description) {
        super(name, description, LeatherArmor.DEFAULT_ARMOR_CLASS);
    }

    public LeatherArmor(String name, String description, int armorClassBonus) {
        super(name,  description, LeatherArmor.DEFAULT_ARMOR_CLASS + armorClassBonus);
    }
}
