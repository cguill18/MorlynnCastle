package MorlynnCastle.model.item;

public class Greatsword extends Weapon {

	private static final String IMG_GSWORD = "weapon.png";
	private final static int DEFAULT_ATTACK_POWER = 12;

	public Greatsword(String name, String description) {

		super(name, description, Greatsword.DEFAULT_ATTACK_POWER);
		this.setImage(IMG_GSWORD);
	}

	public Greatsword(String name, String description, int attackPowerBonus) {
		super(name, description, Greatsword.DEFAULT_ATTACK_POWER + attackPowerBonus);
		this.setImage(IMG_GSWORD);
	}

	public Greatsword(String name, String description, int posx, int posy) {
		super(name, description, Greatsword.DEFAULT_ATTACK_POWER, posx, posy);
		this.setImage(IMG_GSWORD);
	}
}
