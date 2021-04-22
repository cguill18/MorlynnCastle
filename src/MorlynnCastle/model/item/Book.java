package MorlynnCastle.model.item;

public class Book extends Item implements Usable {

	private static final String IMG_BOOK = "scroll.png";
	private String content;

	public Book(String name, String description, String content) {
		super(name, true, description);
		this.content = content;
		this.setImage(IMG_BOOK);
	}

	public Book(String name, String description, String content, int posx, int posy) {
		super(name, true, description, posx, posy);
		this.content = content;
		this.setImage(IMG_BOOK);
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
