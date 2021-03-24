package commands;

import game.Game;

import java.util.List;

public class Load extends Command{

    private static final int NB_ARG = 0;

    private final Game game;

    public Load(Game game) {
        this.game = game;
    }

    @Override
    public void launchCommand(List<String> argument) throws InvalidArgumentNumberException{
        if (argument.size() == Load.NB_ARG)
            this.game.load();
        else throw new InvalidArgumentNumberException();

    }

    @Override
    public void help() {
        System.out.println("load");
    }
}