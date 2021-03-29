package MorlynnCastle.model.characters;


import MorlynnCastle.model.commands.Interpreter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Combat implements Serializable {

    private final Map<String, Character> enemies = new HashMap<>();
    private final Interpreter combatInterpreter;
    private boolean running = true;
    private final Scanner scanner;

    public Combat(Hero hero, Map<String, Character> enemies, Scanner input) {
        this.combatInterpreter = new Interpreter(hero,input);
        this.enemies.putAll(enemies);
        scanner = input;
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

    public void enemyTurn(Hero hero, Character enemy) {
        if (enemy.isAlive())
            enemy.attack(hero);
    }

    public void combatTurn(Hero hero) {
        this.heroTurn();
        this.enemies.forEach((k, v) -> this.enemyTurn(hero, v));
    }

    public void printCombatInfo(Hero hero) {
        System.out.println(hero.getName() + " - HP : " + hero.getCurrentHealthPoints() + "/" + hero.getMaxHealthPoints());
        this.enemies.forEach((k, v) -> System.out.println(k + " - HP : " + v.getCurrentHealthPoints() + "/" + v.getMaxHealthPoints()));
    }

    public boolean endCombat(Hero hero) {
        AtomicBoolean enemiesStillAlive = new AtomicBoolean(false); //besoin d'un atomic boolean pour le foreach, aurait sinon pu utiliser  map.entrySet
        this.enemies.forEach((k, v) -> enemiesStillAlive.set(enemiesStillAlive.get() || v.isAlive()));
        return hero.isAlive() && enemiesStillAlive.get();
    }

    public void runCombat(Hero hero) {
        System.out.println("Start of combat");
        while (this.running) {
            this.printCombatInfo(hero);
            this.combatTurn(hero);
            this.running = this.running && this.endCombat(hero);
        }
        System.out.println("End of combat");
        hero.setOngoingCombat(null);
    }


}
