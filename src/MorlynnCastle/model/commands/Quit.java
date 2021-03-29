package MorlynnCastle.model.commands;


import MorlynnCastle.model.game.Game;

import java.util.List;

public class Quit extends Command{

	private static final int NB_ARG = 0;
	
	private final Game game;

	public Quit(Game game) {
		super();
		this.game = game;
	}

	@Override
	public void launchCommand(List<String> argument) throws InvalidArgumentNumberException {
		if (argument.size() == Quit.NB_ARG)
			this.game.quit();
		else throw new InvalidArgumentNumberException();
	}

	@Override
	public void help() {
		System.out.println("quit");
	}

	

}
