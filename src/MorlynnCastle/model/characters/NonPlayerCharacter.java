package MorlynnCastle.model.characters;

public class NonPlayerCharacter extends Character {
    private boolean isHostile;

    public NonPlayerCharacter(String name, String description, boolean isHostile, int maxHealthPoints, int attackBonus, int damageBonus) {
        super(name, description, maxHealthPoints, attackBonus, damageBonus);
        this.isHostile = isHostile;
    }

    public NonPlayerCharacter(String description, int posx, int posy, String name, int maxHealthPoints, int attackBonus, int damageBonus, boolean isHostile) {
        super(description, posx, posy, name, maxHealthPoints, attackBonus, damageBonus);
        this.isHostile = isHostile;
    }

    public boolean isHostile() {
        return isHostile;
    }

    public void setHostile(boolean hostile) {
        isHostile = hostile;
    }

}
