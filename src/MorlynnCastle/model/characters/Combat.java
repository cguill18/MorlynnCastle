package MorlynnCastle.model.characters;


import MorlynnCastle.model.commands.Interpreter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Combat implements Serializable {

    private Hero hero;
    private final Map<String, Character> enemies = new HashMap<>();
    private Interpreter combatInterpreter = null;
    private boolean running = true;
    private Scanner scanner = null;

    public Combat(Hero hero, Map<String, Character> enemies, Scanner input) {
        this.combatInterpreter = new Interpreter(hero, input);
        this.enemies.putAll(enemies);
        scanner = input;
    }

    public Combat(Hero hero, Map<String, Character> enemies) {
        this.enemies.putAll(enemies);
        this.hero = hero;
    }

    public Map<String, Character> getEnemies() {
        return this.enemies;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void heroTurn() {
        boolean executed_command;
        do {
            String input = this.scanner.nextLine();
            executed_command = this.combatInterpreter.interpret(input);
        } while (!executed_command);
    }

    public int enemyTurn(Hero hero, Character enemy) {
        return enemy.attack(hero);
    }

    public void combatTurn() {
        this.enemies.values().stream().filter(enemy -> enemy.isAlive()).forEach(enemy -> this.enemyTurn(hero, enemy));
    }

    public void printCombatInfo(Hero hero) {
        System.out.println(hero.getName() + " - HP : " + hero.getCurrentHealthPoints() + "/" + hero.getMaxHealthPoints());
        this.enemies.forEach((k, v) -> System.out.println(k + " - HP : " + v.getCurrentHealthPoints() + "/" + v.getMaxHealthPoints()));
    }

    public boolean enemiesStillAlive() {
        return enemies.values().stream().anyMatch(enemy -> enemy.isAlive());
    }

    public void runCombat(Hero hero) {
        System.out.println("Start of combat");
        while (this.running) {
            this.printCombatInfo(hero);
            this.heroTurn();
            this.combatTurn();
            this.running = this.running && this.enemiesStillAlive() && hero.isAlive();
        }
        System.out.println("End of combat");
        hero.setOngoingCombat(null);
    }


}
