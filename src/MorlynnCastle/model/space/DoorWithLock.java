package MorlynnCastle.model.space;

import MorlynnCastle.model.item.Key;
import MorlynnCastle.model.item.Scroll;
import MorlynnCastle.model.item.Usable;

public class DoorWithLock extends Door implements Lockable {

    private static final String IMG_DOOR_LOCK = "locked_door.png";
    private boolean isLocked;
    private final Key key;

    public DoorWithLock(Key key, String description) {
        super(description);
        this.key = key;
        this.isLocked = true;
        this.setImage(IMG_DOOR_LOCK);
    }
    public DoorWithLock(Key key, String description, int posx, int posy) {
        super(description, posx, posy);
        this.key = key;
        this.isLocked = true;
        this.setImage(IMG_DOOR_LOCK);
    }

    @Override
    public void setMirrorDoor(Door mirrorDoor) {
    	if(mirrorDoor instanceof DoorWithLock) {
    		super.setMirrorDoor(mirrorDoor);   
    	} 
    }
    
    public DoorWithLock getMirrorDoorForDoorWithLock() {
    	return (DoorWithLock) this.getMirrorDoor();
    }
    
    public void printKeyForThisDoor() {
        System.out.println("This door can be unlocked with a " + this.key.getName() + ".");
    }

    public boolean getIsLocked() {
        return this.isLocked;
    }

    public boolean changeMirrorDoor() {
    	if(this.getMirrorDoorForDoorWithLock() != null) {
    		if (this.getMirrorDoorForDoorWithLock().getIsLocked() != this.isLocked) {
    			this.getMirrorDoorForDoorWithLock().switchLockedForMirrorDoor();
    		}
    		return this.getMirrorDoorForDoorWithLock().getIsLocked();
    	} else return false;
    }

    public boolean switchLockedForMirrorDoor() {
        this.isLocked = !this.isLocked;
        return this.isLocked;
    }

    @Override
    public boolean unlock(Key key) {
        if (this.key == key) {
            this.isLocked = false;
            this.changeMirrorDoor();
            System.out.println("You unlock the door using the " + key.getName() + ".");
        } else {
            System.out.println("Wrong key.");
        }
        return this.isLocked;
    }

    @Override
    public boolean lock(Key key) {
        if (this.key == key) {
            this.isLocked = true;
            this.changeMirrorDoor();
            System.out.println("You lock the door using the " + key.getName() + ".");
        } else {
            System.out.println("Wrong key.");
        }
        return this.isLocked;
    }

    @Override
    public Place cross() {
        if (this.isLocked) {
            System.out.println("This door is locked, maybe you could use something to unlock it.");
            return this.getEntrance();
        } else {
            return this.getExit();
        }
    }

    @Override
    public boolean receive(Usable u) throws ClassCastException{
    	if(u instanceof Key) {
    		return this.receiveForKey((Key) u);
    	} else {
    		return this.receiveForScroll((Scroll) u);
    	}
    }

	public boolean receiveForScroll(Scroll scroll) {
    	this.printKeyForThisDoor();
        scroll.setEffect("This door can be unlocked with a " + this.key.getName() + ".");
    	return true;
	}
    
	public boolean receiveForKey(Key k) {
    	if (this.isLocked) {
    		return !(this.unlock(k));
    	} else {
    		return !(this.lock(k));
    	}
	}

}