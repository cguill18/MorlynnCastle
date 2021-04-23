package MorlynnCastle.model.item;


import MorlynnCastle.model.space.Lockable;

public class ContainerWithLock extends Container implements Lockable {

	private static final String IMG_CONTAINER_LOCK = "locked_chest.png";
	private boolean isLocked;
    private final Key key;

	public ContainerWithLock(String name, String description, Key key) {
		super(name, description);
		this.key = key;
        this.isLocked = true;
        this.setImage(IMG_CONTAINER_LOCK);
	}

	public ContainerWithLock(String name, String description, Key key, int posx, int posy) {
		super(name,description, posx, posy);
		this.isLocked = true;
		this.key = key;
		this.setImage(IMG_CONTAINER_LOCK);
	}

	public boolean getIsLocked() {
		return this.isLocked;
	}

	@Override
    public boolean receive(Usable u) throws ClassCastException{
    	if(u instanceof Key) {
    		return this.receiveForKey((Key) u);
    	} else {
    		return this.receiveForScroll((Scroll) u);
    	} 
    }

	public boolean receiveForScroll(Scroll b) {
    	this.printKeyForThisChest();
    	return true;
	}
    
	public boolean receiveForKey(Key k) {
    	if (this.isLocked) {
    		return !(this.unlock(k));
    	} else {
    		return !(this.lock(k));
    	}
	}

	@Override
	public boolean unlock(Key key) {
		if (this.key == key) {	
			this.isLocked = false;
			System.out.println("You unlock the" + this.getName() + " using the " + key.getName() + ".");
		} else {
			System.out.println("Wrong key.");
		}
		return this.isLocked;
	}

	@Override
	public boolean lock(Key key) {
		if (this.key == key) {	
			this.isLocked = true;
			System.out.println("You lock the" + this.getName() + " using the " + key.getName() + ".");
		} else {
			System.out.println("Wrong key.");
		}
		return this.isLocked;
	}

	@Override
	public void printContent() {
		if(!this.isLocked) {
			System.out.println("There are :");
			if (!(this.getContent().isEmpty())) {
				this.getContent().forEach((k, v) -> System.out.println(k));
			} else {
				System.out.println("really nothing.");
			}
		} else {
			System.out.println("This chest is locked, please unlock it to look this content.");
		}
	}
	
	@Override
	public void removeItem(String name) {
		if(!this.isLocked) {
			this.getContent().remove(name);
		} else {
			System.out.println("This chest is locked, please unlock it to remove an object.");
		}
	}
	
	@Override
	public void addItem(String name, Item object) {
		if(!this.isLocked) {
			this.getContent().put(name, object);
		} else {
			System.out.println("This chest is locked, please unlock it to add object.");
		}
	}
	
	public void printKeyForThisChest() {
        System.out.println("This chest can be unlocked with a " + this.key.getName() + ".");
    }
	
}
