package MorlynnCastle.model.commands;


import MorlynnCastle.model.game.Game;

import java.util.List;
import java.util.Map;

public class Help extends Command{
	
	private static final int NB_ARG_MIN = 0;
	private static final int NB_ARG_MAX = 1;
	
	private final Map<String, Command> commands;  
	private final Game game;

	public Help(Game game, Map<String, Command> commands) {
		super();
		this.commands = commands;
		this.game = game;
	}

	@Override
	public void launchCommand(List<String> argument) throws InvalidArgumentNumberException, NullPointerException {
		if (argument.size() == NB_ARG_MIN) {
			this.game.help();
		} else if(argument.size() == NB_ARG_MAX) {
			Command c = this.stringToCommand(argument.get(0));
			c.help();
		} else throw new InvalidArgumentNumberException();
	}

	@Override
	public void help() {
		System.out.println("help");
		System.out.println("help command");
	}

	public Command stringToCommand(String name) {
		return this.commands.get(name);
	}
}
