package MorlynnCastle.model.commands;

import MorlynnCastle.model.game.Game;

import java.util.List;

public class Save extends Command{

    private static final int NB_ARG = 0;

    private final Game game;

    public Save(Game game) {
        this.game = game;
    }

    @Override
    public void launchCommand(List<String> argument) throws InvalidArgumentNumberException{
        if (argument.size() == Save.NB_ARG)
            this.game.save();
        else throw new InvalidArgumentNumberException();

    }

    @Override
    public void help() {
        System.out.println("save");
    }
}
