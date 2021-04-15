package MorlynnCastle.model.item;

import MorlynnCastle.model.space.Lockable;

public class Scroll extends Book{

	public Scroll(String name, String description, String content) {
		super(name, description, content);
	}

	public Scroll(String description, int posx, int posy, String name, String content) {
		super(description, posx, posy, name, content);
	}

	@Override
	public void use(Receiver obj) {
		if (obj instanceof Lockable) {
            obj.receive(this);
        } else {
            System.out.println("You can't use this with this object.");
        }
	}
}
