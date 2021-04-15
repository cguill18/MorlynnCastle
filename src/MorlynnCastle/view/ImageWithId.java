package MorlynnCastle.view;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

public class ImageWithId extends ImageView {

    private int id;

    /**
     * Creates a new view that represents an IMG element.
     *
     * @param elem the element to create a view for
     */
    public ImageWithId(Element elem, int id) {
        super(elem);
        this.id = id;
    }

    public int getId(){
        return this.getId();
    }
}
