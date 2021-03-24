package space;

import item.Key;
import item.Receiver;

public interface Lockable extends Receiver {

	boolean unlock(Key key);

	boolean lock(Key key);

}