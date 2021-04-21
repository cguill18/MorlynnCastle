package MorlynnCastle.model.characters;


import MorlynnCastle.model.item.Armor;
import MorlynnCastle.model.item.Item;
import MorlynnCastle.model.item.Weapon;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.model.space.Place;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Character extends Interaction implements Attackable, Attacker {

    private final static int MIN_DICE = 1;
    private final static int MAX_DICE = 20;
    private final static int DEFAULT_ARMOR_CLASS = 10;
    private final static int DEFAULT_ATTACK_POWER = 2;


    private final String name;
    private Place place;
    private final Map<String, Item> inventory = new HashMap<>();
    private boolean alive = true;
    private int currentHealthPoints;
    private int maxHealthPoints;
    private int armorClass = Character.DEFAULT_ARMOR_CLASS;
    private int attackPower = Character.DEFAULT_ATTACK_POWER;
    private int attackBonus;
    private int damageBonus;
    private Armor armor = null;
    private Weapon weapon = null;
    private final Random dice = new Random();

    public Character(String name, String description, int maxHealthPoints, int attackBonus, int damageBonus) {
        super(description);
        this.name = name;
        this.maxHealthPoints = maxHealthPoints;
        this.currentHealthPoints = maxHealthPoints;
        this.attackBonus = attackBonus;
        this.damageBonus = damageBonus;
    }

    public Character(String name, String description, int maxHealthPoints, int attackBonus, int damageBonus, int posx, int posy) {
        super(description, posx, posy);
        this.name = name;
        this.currentHealthPoints = maxHealthPoints;
        this.maxHealthPoints = maxHealthPoints;
        this.attackBonus = attackBonus;
        this.damageBonus = damageBonus;
    }

    public String getName() {
        return this.name;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getCurrentHealthPoints() {
        return this.currentHealthPoints;
    }

    public void setCurrentHealthPoints(int currentHealthPoints) {
        this.currentHealthPoints = currentHealthPoints;
    }

    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public int getAttackBonus() {
        return this.attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public Place getPlace() {
        return this.place;
    }

    public Armor getArmor() {
        return armor;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setPlace(Place p) {
        if (this.place != null)
            this.freePlace();
        this.place = p;
        p.addCharacter(this.name, this);
    }

    public Map<String, Item> getInventory() {
        return inventory;
    }

    public void freePlace() {
        this.place.freeCharacter(this.name);
        this.place = null;
    }

    public boolean isInInventory(String name) {
        return this.getInventory().containsKey(name);
    }

    public void printInventory() {
        System.out.println("You have :");
        if (!(this.inventory.isEmpty()))
            this.inventory.forEach((k, v) -> System.out.println(k));
        else {
            System.out.println("nothing J.S.");
        }
    }

    @Override
    public int isAttacked(int attackRoll, int damage) {
        if (attackRoll >= this.armorClass) {
            this.currentHealthPoints -= damage;
            System.out.println(" hit " + this.name + ".");
            System.out.println(this.name + " takes " + damage + " points of damage.");
            if (this.currentHealthPoints <= 0) {
                this.currentHealthPoints = 0;
                this.alive = false;
                System.out.println(this.name + " is dead.");
            }
            return damage;
        } else {
            System.out.println(" miss " + this.name + ".");
            return 0;
        }
    }

    @Override
    public int attack(Attackable attackable) {
        int attackRoll = this.dice.nextInt(Character.MAX_DICE) + Character.MIN_DICE;
        int damageRoll = this.dice.nextInt(this.attackPower) + Character.MIN_DICE;
        System.out.print(this.name);
        return attackable.isAttacked(attackRoll + this.attackBonus, damageRoll + this.damageBonus);
    }

    public void equipArmor(Armor armor) {
        if (this.armor != null) {
            this.inventory.put(this.armor.getName(), this.armor);
        }
        this.armor = armor;
        this.armorClass = armor.getArmorClass();
        this.inventory.remove(armor.getName());
    }


    public void equipWeapon(Weapon weapon) {
        if (this.weapon != null) {
            this.inventory.put(this.weapon.getName(), this.weapon);
        }
        this.weapon = weapon;
        this.attackPower = weapon.getAttackPower();
        this.inventory.remove(weapon.getName());
    }

    @Override
    public void print() {
        super.print();
        if (!this.alive)
            System.out.println("But now he's dead.");
    }
}