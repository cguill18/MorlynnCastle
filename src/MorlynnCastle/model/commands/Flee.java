package MorlynnCastle.model.commands;


import MorlynnCastle.model.characters.Hero;

import java.util.List;

public class Flee extends Command {

    private final static int NB_ARG = 0;

    private final Hero hero;

    public Flee(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void launchCommand(List<String> argument) throws InvalidArgumentNumberException {
        if (argument.size() == Flee.NB_ARG){
            this.hero.getOngoingCombat().setRunning(false);
        } else throw new InvalidArgumentNumberException();    }

    @Override
    public void help() {
    	System.out.println("flee");
    }
}
