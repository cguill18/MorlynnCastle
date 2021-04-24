package MorlynnCastle.model.characters;

public class Guard extends NonPlayerCharacter{

    private static final String IMG_GUARD = "guard.png";
    private static final boolean DEFAULT_HOSTILE = true;
    private static final int DEFAULT_HP = 10;
    private static final int DEFAULT_ATTACK_BONUS = 4;
    private static final int DEFAULT_DAMAGE_BONUS = 2;


    public Guard(String name, String description) {
        super(name, description, Guard.DEFAULT_HOSTILE, Guard.DEFAULT_HP, Guard.DEFAULT_ATTACK_BONUS, Guard.DEFAULT_DAMAGE_BONUS);
        this.setImage(IMG_GUARD);
    }

    public Guard(String name, String description, int posx, int posy) {
        super(name, description, Guard.DEFAULT_HOSTILE, Guard.DEFAULT_HP, Guard.DEFAULT_ATTACK_BONUS, Guard.DEFAULT_DAMAGE_BONUS, posx, posy);
        this.setImage(IMG_GUARD);
    }
}
