package MorlynnCastle.model.item;

public class Greatsword extends Weapon {

	private final static int DEFAULT_ATTACK_POWER = 12;

	public Greatsword(String name, String description) {
		super(name, description, Greatsword.DEFAULT_ATTACK_POWER);
	}

	public Greatsword(String name, String description, int attackPowerBonus) {
		super(name, description, Greatsword.DEFAULT_ATTACK_POWER + attackPowerBonus);
	}

	public Greatsword(String description, int posx, int posy, String name) {
		super(description, posx, posy, name, Greatsword.DEFAULT_ATTACK_POWER);
	}
}
