package MorlynnCastle.model.item;

public class Greatsword extends Weapon {

	private final static int DEFAULT_ATTACK_POWER = 12;

	public Greatsword(String name, String description) {
		super(name, description, Greatsword.DEFAULT_ATTACK_POWER);
	}

	public Greatsword(String name, String description, int attackPowerBonus) {
		super(name, description, Greatsword.DEFAULT_ATTACK_POWER + attackPowerBonus);
	}

}
