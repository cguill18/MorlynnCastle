package commands;

import java.util.List;

public abstract class Command {
	
	public Command() {
	}

	public abstract void launchCommand(List<String> argument);

	public abstract void help();
}
