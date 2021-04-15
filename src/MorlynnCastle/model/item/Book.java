package MorlynnCastle.model.item;

public class Book extends Item implements Usable {
	
	private String content;

	public Book(String name, String description, String content) {
		super(name, true, description);
		this.content = content;
	}

	public Book(String name, String description, String content, int posx, int posy) {
		super(name, true, description, posx, posy);
		this.content = content;
	}

	@Override
	public void use() {
		System.out.println(this.content);	
	}

	@Override
	public void use(Receiver obj) {
		System.out.println("You cannot use this item on another.");
	}
	
}
