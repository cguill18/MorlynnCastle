package MorlynnCastle.model.space;

import MorlynnCastle.model.characters.Character;
import MorlynnCastle.model.characters.NonPlayerCharacter;
import MorlynnCastle.model.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Place extends Interaction {

    private final String name;
    private final Map<String, Interaction> interactions = new HashMap<>();
    private final int ENEMY_DETECTION_THRESHOLD;
    private final Random dice = new Random();


    public Place(String name, String description, Map<String, Interaction> interactions, int enemy_detection) {
        super(description);
        this.name = name;
        this.ENEMY_DETECTION_THRESHOLD = enemy_detection;
        if (interactions != null) {
            this.interactions.putAll(interactions);
        }
    }

    public Map<String, Interaction> getInteractions() {
        return this.interactions;
    }

    public String getName() {
        return this.name;
    }

    public int getEnemyDetectionthreshold() {
        return ENEMY_DETECTION_THRESHOLD;
    }

    public void addCharacter(String name, Character character) {
        this.interactions.put(name, character);
    }

    public void freeCharacter(String name) {
        this.interactions.remove(name);
    }

    public boolean isInPlace(String name) {
        return this.getInteractions().containsKey(name);
    }

    @Override
    public void print() {
        super.print();
        System.out.println("In this place, you see :");
        this.getInteractions().forEach((k, v) -> {
            if (v instanceof Door) {
                System.out.println("a door leading to the " + k);
            } else if (!(k.equals("hero")))
                System.out.println("a " + k);
        });
    }

    public void takeOut(Item i) {
        this.interactions.remove(i.getName());
    }

    public Map<String, Character> getEnemiesInPlace() {
        Map<String, Character> enemies = new HashMap<>();
        this.interactions.forEach((k, v) -> {
            if (v instanceof NonPlayerCharacter && ((NonPlayerCharacter) v).isAlive() && ((NonPlayerCharacter) v).isHostile()) {
                enemies.put(((Character) v).getName(), (Character) v);
            }
        });
        return enemies;
    }

    public boolean randomEncoutner() {
        boolean result = false;
        if (this.getEnemiesInPlace().size() != 0) {
            int randomEnemyDetection = this.dice.nextInt(100) + 1;
            result = (randomEnemyDetection < this.ENEMY_DETECTION_THRESHOLD);
        }
        return result;
    }

    public String getAnEnemyName() {
        return this.getEnemiesInPlace().keySet().stream().findFirst().get();
    }


}






