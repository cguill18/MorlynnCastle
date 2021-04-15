package MorlynnCastle.model.item;

import MorlynnCastle.model.space.Lockable;

public class Scroll extends Book{

	public Scroll(String name, String description, String content) {
		super(name, description, content);
	}

	public Scroll(String name, String description, String content, int posx, int posy) {
		super(name, description, content, posx, posy);
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
