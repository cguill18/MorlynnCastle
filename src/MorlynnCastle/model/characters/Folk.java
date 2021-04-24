package MorlynnCastle.model.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Folk extends NonPlayerCharacter implements Talkable {

    private final Dialog dialog;

    public Folk(String name, String description, boolean isHostile, Dialog dialog, int maxHealthPoints, int attackBonus, int damageBonus) {
        super(name, description, isHostile, maxHealthPoints, attackBonus, damageBonus);
        this.dialog = dialog;
    }

    public Folk(String name, String description, boolean isHostile, Dialog dialog, int maxHealthPoints, int attackBonus, int damageBonus, int posx, int posy) {
        super(name, description, isHostile, maxHealthPoints, attackBonus, damageBonus, posx, posy);
        this.dialog = dialog;
    }

    @Override
    public Dialog getDialog() {
        return dialog;
    }

    @Override
    public void talk(Scanner input) {
        if (this.isAlive()) {
            if (!this.isHostile())
                this.dialog.startDialog(this, input);
            else System.out.println("This one doesn't want to talk to you.");
        } else {
            System.out.println("Now you're talking to dead people.");
        }
    }

    @Override
    public Dialog talk() {
        if (this.isAlive()){
            if (!this.isHostile()){
                return getDialog();
            } else {
                List<String> playerChoices = new ArrayList<>();
                List<String> dialogs = new ArrayList<>();
                dialogs.add("This one doesn't want to talk to you.");
                Dialog hostileDialog = new Dialog(playerChoices,dialogs);
                return hostileDialog;
            }
        } else {
            return null;
        }
    }
}
