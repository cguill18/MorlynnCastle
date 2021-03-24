package commands;

import characters.Hero;
import game.Game;

import java.util.*;

public class Interpreter {

    private final Map<String, Command> commands = new HashMap<>();

    public Interpreter(Hero hero, Game game) {
        Command go = new Go(hero);
        this.commands.put("go", go);
        Command use = new Use(hero);
        this.commands.put("use", use);
        Command look = new Look(hero);
        this.commands.put("look", look);
        Command take = new Take(hero);
        this.commands.put("take", take);
        Command quit = new Quit(game);
        this.commands.put("quit", quit);
        Command inventory = new Inventory(hero);
        this.commands.put("inventory", inventory);
        Command help = new Help(game,commands);
        this.commands.put("help", help);
        Command talk = new Talk(hero, game.getScanner());
        this.commands.put("talk", talk);
        Command attack = new Attack(hero, game.getScanner());
        this.commands.put("attack", attack);
        Command equip = new Equip(hero);
        this.commands.put("equip", equip);
        Command stat = new Stat(hero);
        this.commands.put("stat", stat);
        Command save = new Save(game);
        this.commands.put("save", save);
        Command load = new Load(game);
        this.commands.put("load", load);
    }

    public Interpreter(Hero hero, Scanner input) {
        Command attack = new Attack(hero,input);
        Command flee = new Flee(hero);
        this.commands.put("attack", attack);
        this.commands.put("flee", flee);

    }

    public boolean interpret(String input) {
        boolean executed_command = true;
        String[] parsedInput = input.split(" ");
        List<String> arguments = new LinkedList<>(Arrays.asList(parsedInput.clone()));
        if (arguments.size() != 0) {                //sinon erreur possible quand on entre juste un espace
            String command = arguments.get(0);
            arguments.remove(0);
            if (commands.containsKey(command)) {
                try {
                    commands.get(command).launchCommand(arguments);
                } catch (InvalidArgumentNumberException e) {
                    System.out.println("There is too few or too many arguments for this command.");
                    executed_command = false;
                } catch (NullPointerException e) {
                    if (command.equals("help"))
                        System.out.println("There is no command named " + arguments.get(0) + ".");
                    else System.out.println("There is nothing named " + arguments.get(0) + " here.");
                    executed_command = false;
                } catch (ClassCastException e) {
                    System.out.println("This is not a valid argument for this command.");
                    executed_command = false;
                }
            } else {
                System.out.println("I didn't understand that.");
                executed_command = false;
            }
        }
        return executed_command;
    }

}
