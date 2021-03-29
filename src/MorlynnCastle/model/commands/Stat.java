package MorlynnCastle.model.commands;

import MorlynnCastle.model.characters.Hero;

import java.util.List;

public class Stat extends Command {

    private static final int NB_ARG = 0;

    private final Hero hero;

    public Stat(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void launchCommand(List<String> argument) throws InvalidArgumentNumberException {
        if (argument.size() == Stat.NB_ARG) {
            System.out.println("Your attributes : ");
            System.out.println("HP : " + hero.getCurrentHealthPoints() + "/" + this.hero.getMaxHealthPoints());
            String armorClass = "Armor class : " + this.hero.getArmorClass();
            if (this.hero.getArmor() != null) {
                armorClass = armorClass + " ("+ this.hero.getArmor().getName() + ")";
            }
            System.out.println(armorClass);
            String attackPower = "Attack power : " + this.hero.getAttackPower();
            if (this.hero.getWeapon() != null) {
                attackPower = attackPower + " ("+ this.hero.getWeapon().getName() + ")";
            }
            System.out.println(attackPower);
        } else throw new InvalidArgumentNumberException();
    }

    @Override
    public void help() {
        System.out.println("stat");
    }
}
