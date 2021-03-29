package MorlynnCastle.model.commands;


import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.item.Receiver;
import MorlynnCastle.model.item.Usable;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.model.space.Place;

import java.util.List;
import java.util.Map;

public class Use extends Command {

	private static final int NB_ARG_MAX = 2;
	private static final int NB_ARG_MIN = 1;

	private final Hero hero;

	public Use(Hero hero) {
		super();
		this.hero = hero;
	}

	public Map<String, Interaction> objectsInPlaceOfHero() {
		return this.hero.getPlace().getInteractions();
	}

	public Place getPlaceOfHero() {
		return this.hero.getPlace();
	}

	public Usable convertStringToUsable(String name) throws ClassCastException{
		Usable u;
		if (this.getPlaceOfHero().isInPlace(name)) {
			u = (Usable) this.objectsInPlaceOfHero().get(name);
		} else {
			u = (Usable) this.hero.getInventory().get(name);
		}
		return u;
	}


	public Receiver convertStringToReceiver(String name) throws ClassCastException{
		Receiver r;
		if (this.getPlaceOfHero().isInPlace(name)) {
			r = (Receiver) this.objectsInPlaceOfHero().get(name);
		} else {
			r = (Receiver) this.hero.getInventory().get(name);
		}
		return r;
	}

	@Override
	public void launchCommand(List<String> argument) throws ClassCastException, InvalidArgumentNumberException, NullPointerException {
		if(argument.size() == Use.NB_ARG_MIN) {
                    this.hero.use(this.convertStringToUsable(argument.get(0)));
		} else if (argument.size() == Use.NB_ARG_MAX) {			
                    this.hero.use(this.convertStringToUsable(argument.get(0)), this.convertStringToReceiver(argument.get(1)));
		} else throw new InvalidArgumentNumberException();
	}

	@Override
	public void help() {
		System.out.println("use name_of_object");
		System.out.println("use object_you_want_to_use object_on_which_you_use");
	}

}


