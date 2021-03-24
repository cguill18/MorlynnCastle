package item;

public class Book extends Item implements Usable {
	
	private String content;

	public Book(String name, String description, String content) {
		super(name, true, description);
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
