package commands;

import characters.Hero;
import item.Equipable;

import java.util.List;

public class Equip extends Command {

    private static final int NB_ARG = 1;

    private final Hero hero;

    public Equip(Hero hero) {
        this.hero = hero;
    }

    public Equipable stringtoEquipable(String name) throws ClassCastException {
        return (Equipable) this.hero.getInventory().get(name);
    }

    @Override
    public void launchCommand(List<String> argument) throws InvalidArgumentNumberException, ClassCastException, NullPointerException {
        if (argument.size() == Equip.NB_ARG) {
            this.hero.equip(stringtoEquipable(argument.get(0)));
            System.out.println("You equip " + argument.get(0));
        }
        else throw new InvalidArgumentNumberException();
    }

    @Override
    public void help() {
        System.out.println("equip armor_name");
        System.out.println("or equip weapon_name");
    }
}
