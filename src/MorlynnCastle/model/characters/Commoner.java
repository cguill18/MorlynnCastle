package MorlynnCastle.model.characters;

public class Commoner extends Folk{

    private static final boolean DEFAULT_HOSTILE = false;
    private static final int DEFAULT_HP = 4;
    private static final int DEFAULT_ATTACK_BONUS = 2;
    private static final int DEFAULT_DAMAGE_BONUS = 1;

    public Commoner(String name, String description, Dialog dialog) {
        super(name, description, Commoner.DEFAULT_HOSTILE, dialog, Commoner.DEFAULT_HP, Commoner.DEFAULT_ATTACK_BONUS, Commoner.DEFAULT_DAMAGE_BONUS);
    }


}
