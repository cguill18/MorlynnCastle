package MorlynnCastle.model.item;

import java.util.HashMap;
import java.util.Map;

public class Container extends Item {

	private static final String IMG_CONTAINER = "unlocked_chest.png";
	private final Map<String, Item> content = new HashMap<>();
	
	public Container(String name, String description) {
		super(name, false, description);
		this.setImage(IMG_CONTAINER);
	}

	public Container(String name, String description, int posx, int posy) {
		super(name, false, description, posx, posy);
		this.setImage(IMG_CONTAINER);
	}

	public Map<String, Item> getContent() {
		return this.content;
	}
	
	public void removeItem(String name) {
		this.content.remove(name);
	}
	
	public void addItem(String name, Item object) {
		this.content.put(name, object);
	}
	
	@Override 
	public void print() {
		super.print();
		this.printContent();
	}
	
	public void printContent() {
		System.out.println("There are :");
		if (!(this.content.isEmpty()))
			this.content.forEach((k, v) -> System.out.println(k));
		else {
			System.out.println("nothing J.S.");
		}
	}
}
