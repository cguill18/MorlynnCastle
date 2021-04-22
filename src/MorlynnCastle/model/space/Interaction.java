package MorlynnCastle.model.space;

import java.io.Serializable;

public abstract class Interaction implements Serializable {

    private final String description;
    private int posx = 0;
    private int posy = 0;
    private String image;

    public Interaction(String description) {
        this.description = description;

    }

    public Interaction(String description, int posx, int posy){
        this.description = description;
        this.posx = posx;
        this.posy = posy;
    }

    public void print() {
        System.out.println(this.description);
    }

    public String getDescription(){
        return this.description;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public String getImage() { return this.image; }

    public void setImage(String url) {this.image = url;}
}