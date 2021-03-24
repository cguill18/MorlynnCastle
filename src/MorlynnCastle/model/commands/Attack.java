package commands;

import characters.Attackable;
import characters.Combat;
import characters.Hero;
import characters.NonPlayerCharacter;

import java.util.List;
import java.util.Scanner;


public class Attack extends Command {

    private static final int NB_ARG = 1;

    private final Hero hero;
    private final Scanner scanner;

    public Attack(Hero hero, Scanner input) {
        this.hero = hero;
        this.scanner = input;
    }

    public Attackable stringToAttackableInPlace(String name) throws ClassCastException {
        return (Attackable) hero.getPlace().getInteractions().get(name);
    }

    public void startCombat(NonPlayerCharacter npc) {
        if (!npc.isHostile()) {
            npc.setHostile(true);
            System.out.println("Hey ! What have I done to deserve this ?!");
        }
        Combat c = new Combat(this.hero, this.hero.getPlace().getEnemiesInPlace(), this.scanner);
        this.hero.setOngoingCombat(c);
    }

    public void attackInCombat(NonPlayerCharacter npc) throws NullPointerException{
        this.hero.attack(this.hero.getOngoingCombat().getEnemies().get(npc.getName()));
    }

    public void attackNonPlayerCharacter(NonPlayerCharacter npc) throws NullPointerException {
        if (npc.isAlive()) {
            if (this.hero.getOngoingCombat() == null) {
                this.startCombat(npc);
                this.hero.getOngoingCombat().runCombat(this.hero);
            } else this.attackInCombat(npc);
        } else {
            System.out.println("You're attacking a dead body...");
            System.out.println("God, what is wrong with you ?");
            System.out.println("Are you trying to get on my nerves ?! Because you're successfull !");
        }
    }

    @Override
    public void launchCommand(List<String> argument) throws InvalidArgumentNumberException, ClassCastException, NullPointerException {
        if (argument.size() == Attack.NB_ARG) {
            if (!(argument.get(0).equals(hero.getName()))) {
                Attackable a = stringToAttackableInPlace(argument.get(0));
                if (a instanceof NonPlayerCharacter) {
                    this.attackNonPlayerCharacter((NonPlayerCharacter) a);
                } else hero.attack(a);
            } else {
                System.out.println("You want to hurt yourself ?");
                System.out.println("I mean, I'm not judging or anything but that's pretty messed up.");
            }
        } else throw new InvalidArgumentNumberException();
    }

    @Override
    public void help() {
        System.out.println("attack npc_name");
    }
}
