package MorlynnCastle.model.space;

import MorlynnCastle.model.item.Key;
import MorlynnCastle.model.item.Receiver;

public interface Lockable extends Receiver {

	boolean unlock(Key key);

	boolean lock(Key key);

}