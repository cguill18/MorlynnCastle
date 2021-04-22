package MorlynnCastle.model.characters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Dialog implements Serializable {

    private final List<String> playerChoices = new ArrayList<>();
    private final List<String> Dialogs = new ArrayList<>();

    public Dialog(List<String> playerChoices, List<String> Dialogs) {
        this.playerChoices.addAll(playerChoices);
        this.Dialogs.addAll(Dialogs);
    }

    public List<String> getPlayerChoices() {
        return playerChoices;
    }

    public List<String> getDialogs() {
        return Dialogs;
    }

    public void addPlayerDialog(String dialog) {
        this.playerChoices.add(dialog);
    }

    public int getPlayerChoice(Scanner input) {
        int choice = -1;
        System.out.println("Please input a number corresponding to the desired dialog.");
        while (choice < 1 || choice > this.playerChoices.size() + 1) {
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("This is not a number.");
            }
            System.out.println("Please input a number corresponding to the desired dialog.");
        }
        return choice;
    }

    public void printOptions() {
        int i = 1;
        System.out.println("Your dialog options : ");
        for (String choice : this.playerChoices) {
            System.out.println(i + ". " + choice);
            i++;
        }
        System.out.println(i + ". quit");
    }

    public void startDialog(Folk folk, Scanner input) {
        this.printOptions();
        int choice = this.getPlayerChoice(input) - 1;
        while (choice != this.playerChoices.size()) {
            System.out.println(this.Dialogs.get(choice));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                //ne devrait pas arriver pour une application en thread simple, mais pour éviter un problème de catch vide
                // ou faire : throw new RuntimeException("Unexpected interrupt", e);
                Thread.currentThread().interrupt();
            }
            this.printOptions();
            choice = this.getPlayerChoice(input) - 1;
        }
        System.out.println("You say goodbye to " + folk.getName());
    }
}
