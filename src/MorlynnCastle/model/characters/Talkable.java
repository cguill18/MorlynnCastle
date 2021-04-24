package MorlynnCastle.model.characters;

import java.util.Scanner;

public interface Talkable {
    void talk(Scanner input);
    Dialog talk();
    Dialog getDialog();
}
