package MorlynnCastle.model.characters;

public class King extends Folk {
	
	private static final boolean DEFAULT_HOSTILE = false;
	private static final int DEFAULT_HP = 12;
	private static final int DEFAULT_ATTACK_BONUS = 3;
	private static final int DEFAULT_DAMAGE_BONUS = 2;

	public King(String name, String description, Dialog dialog){
		super(name, description, King.DEFAULT_HOSTILE, dialog, King.DEFAULT_HP, King.DEFAULT_ATTACK_BONUS, King.DEFAULT_DAMAGE_BONUS);
	}

	public King(String name, String description, Dialog dialog, int posx, int posy) {
		super(name, description, King.DEFAULT_HOSTILE, dialog, King.DEFAULT_HP, King.DEFAULT_ATTACK_BONUS, King.DEFAULT_DAMAGE_BONUS, posx, posy);
	}
}
