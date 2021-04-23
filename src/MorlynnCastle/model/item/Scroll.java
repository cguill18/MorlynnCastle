package MorlynnCastle.model.item;

import MorlynnCastle.model.space.Lockable;

public class Scroll extends Book{

	private static final String IMG_SCROLL = "scroll.png";

	public Scroll(String name, String description, String content) {
		super(name, description, content);
	}

	public Scroll(String name, String description, String content, int posx, int posy) {
		super(name, description, content, posx, posy);
		this.setImage(IMG_SCROLL);
	}

	@Override
	public boolean use(Receiver obj) {
		if (obj instanceof Lockable) {
            return obj.receive(this);
        } else {
            System.out.println("You can't use this with this object.");
            return false;
        }
	}
}
