package commands;

import characters.Hero;

import java.util.List;

public class Look extends Command{

	private static final int NB_ARG_MAX = 1;
	private static final int NB_ARG_MIN = 0;

	private final Hero hero;
	
	public Look(Hero hero) {
		super();
		this.hero = hero;
	}

	@Override
	public void launchCommand(List<String> argument) throws NullPointerException, InvalidArgumentNumberException{
		if (argument.size() == Look.NB_ARG_MIN ){
			this.hero.look();
		} else if (argument.size() == Look.NB_ARG_MAX){
			this.hero.look(this.hero.getPlace().getInteractions().get(argument.get(0)));
		} else throw new InvalidArgumentNumberException();
		
	}

	@Override
	public void help() {
		System.out.println("look");
		System.out.println("look name_of_object");
	}


}
