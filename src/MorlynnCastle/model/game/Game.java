package game;

import characters.Hero;
import commands.Interpreter;
import space.World;

import java.io.*;
import java.util.Scanner;

public class Game {

    private boolean running = true;
    private Hero hero;
    private World world;
    private final Scanner scanner;
    private Interpreter interpreter;

    public Game(Scanner input) {
        this.hero = new Hero();
        this.world = new World();
        this.scanner = input;
        this.interpreter = new Interpreter(this.hero,this);
    }


    public Scanner getScanner() {
        return scanner;
    }

    public void history() {
        System.out.println();
        System.out.println("Hello hero and welcome to Morlynn Castle.");
        System.out.println("No time for pleasantries.");
        System.out.println("The military are carrying out a coup d'état and have taken the king hostage just now.");
        System.out.println("Your mission, should you choose to accept it, is to kill the guards in the throne room to save the king and exit the castle.");
        System.out.println("I bet the king knows the way out, because the main gate has closed just behind you.");
        System.out.println("You managed to sneak into the castle through this one, but in order not to attract attention you didn't carry any weapons or armor.");
        System.out.println("You will have to equip yourself by finding what you can in the castle. ");
        System.out.println("Some of the doors will be locked, luckily you can find a way to open them, keys should do the trick.");
        System.out.println("Be careful, the guards might attack you.");
        System.out.println("Good luck, you might need it. ");
        System.out.println();
    }

    public void help() {
        System.out.println("To play this game, you will have to type combination of words in the command line.");
        System.out.println("When you play, the \"..._name\" in the description of the usable commands below must be replaced with the name of what you want to interact with.");
        System.out.println("You can use the commands :");
        System.out.println("go \"room_name\" : to move to the room with the name you put, if it exists and if it is adjacent to yours.");
        System.out.println("help : to get this text.");
        System.out.println("help \"command_name\" : to get the syntax of a specific command.");
        System.out.println("look : to get information about the room you are in.");
        System.out.println("look \"thing_name\" : to get information about a specific thing.");
        System.out.println("take \"item_name\" : to put the object in your inventory.");
        System.out.println("take \"container_name\" \"item_name\" : to put an object stored in a container (like a chest) in your inventory.");
        System.out.println("use \"thing_name\" : to use an object.");
        System.out.println("use \"thing1_name\" \"thing2_name\" : to use an object on something.");
        System.out.println("inventory : allows you to see the objects in our inventory.");
        System.out.println("inventory \"item_name\" : allows you to look at a specific object in your inventory.");
        System.out.println("talk  \"someone_name\" : to talk to someone to get more information.");
        System.out.println("quit : if you want to quit the game.");
        System.out.println("attack \"someone_name\" : to attack someone. If you are in combat, it will attack an enemy, \n" +
                "else it will start a combat with all enemies in the room.");
        System.out.println("flee : to flee a combat. Be careful, even if you flee, your enemies can attack you.");
        System.out.println("equip \"item_name\": to equip an item in your inventory.");
        System.out.println("stat : displays the hero's characteristics.");
        System.out.println("save : to save your progression.");
        System.out.println("load : to load a save.");
        System.out.println();
    }

    public void quit() {
        System.out.println("Do you really want to quit the game ?");
        System.out.println("Answer with yes or no.");
        switch (this.scanner.nextLine()) {
            case "yes":
                this.running = false;
                break;
            case "no":
                System.out.println("You continue to play.");
                break;
            default:
                System.out.println("I didn't understand that.");
                quit();
        }
    }

    public void save() {
        System.out.println("Type the name of the save file you wish to create, without extension, and press Return.");
        String fileName = this.scanner.nextLine();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName + ".sav"));
            oos.writeObject(this.running);
            oos.writeObject(this.hero);
            oos.writeObject(this.world);
            oos.flush();
            oos.close();
            System.out.println("your progress has been successfully saved in" + fileName + ".");
        } catch (FileNotFoundException e){
            System.out.println("It seems you can't save in this directory.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSaveFiles(){
        String pathname = System.getProperty("user.dir");
        File file = new File(pathname);
        FilenameFilter filter = (f, name) -> name.endsWith(".sav");
        String[] filesNames = file.list(filter);
        for (String fileName : filesNames) {
            System.out.println(fileName);
        }
    }


    public boolean load(){
        boolean success = false;
        this.showSaveFiles();
        System.out.println("Type the name of the save file you wish to load, without extension, and press Return.");
        String fileName = this.scanner.nextLine();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName + ".sav"));
            this.running = (boolean) ois.readObject();
            this.hero = (Hero) ois.readObject();
            this.world = (World) ois.readObject();
            this.interpreter = new Interpreter(this.hero,this);
            success = true;
            System.out.println("The save has been successfully loaded.");
        } catch (FileNotFoundException e){
            System.out.println("This file doesn't exist.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error, the file you wish to load might not be a save or is corrupted.");
        }
        return success;
    }

    public void initGame(){
        this.history();
        System.out.println("Press Return to continue.");
        scanner.nextLine();
        this.help();
        System.out.println("Press Return to continue.");
        scanner.nextLine();
        this.world.setStart(this.hero);
    }

    public void launchGame() {
        System.out.println("To start a fresh game, please type \"start\" and press Return.");
        System.out.println("If you wish to load a save, type \"load\" and press Return.");
        String input = scanner.nextLine();
        while (!(input.equals("start") || input.equals("load")) && scanner.hasNext()) {
            System.out.println("I didn't understand your command.");
        }
        if (!(input.equals("load") && this.load())){
            this.initGame();
        }
        this.hero.look();
    }


    public void runGame() {
        String input;
        boolean executed_command;
        while (this.running && this.hero.isAlive() && !this.hero.isGoalAchieved()) {
            if (this.hero.getPlace().randomEncoutner()) {
                //attaque le "premier" ennemi dans la hashmap pour déclencher le combat
                input = ("attack " + this.hero.getPlace().getAnEnemyName());
                System.out.println("You're being attacked !");
                this.interpreter.interpret(input);
            } else {
                do {
                    input = this.scanner.nextLine();
                    executed_command = this.interpreter.interpret(input);
                } while (!executed_command);
            }
        }
    }

    public void ending() {
        if (this.hero.isAlive()) {
            if (this.hero.isGoalAchieved()) {
                System.out.println("You have completed the story !");
            }
        } else System.out.println("You died.");
        System.out.println("Thank you for playing Morlynn Castle.");
        System.out.println("This game was made by :");
        System.out.println("FRADET Amandine");
        System.out.println("GUILLOT Clémentine");
        System.out.println("FORESTIER Louis");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Game g = new Game(input);
        g.launchGame();
        g.runGame();
        g.ending();
    }


}