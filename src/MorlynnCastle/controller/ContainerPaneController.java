/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MorlynnCastle.controller;

import MorlynnCastle.model.item.Item;
import MorlynnCastle.view.InteractionView;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
public class ContainerPaneController implements Initializable {
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private Button take;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML public void handleTake() {
        
    }
    
    public void displayContainer(Map<String, Item> container){
        final int[] i = {0};
        container.forEach((name,item)->{
            InteractionView interactionView = new InteractionView(item);
            interactionView.setStyle(interactionView.getStyle()+"-fx-background-image:url(\"/res/gold.png\")");
            gridPane.add(interactionView, i[0]%this.gridPane.getColumnConstraints().size(), i[0]/this.gridPane.getRowConstraints().size());
            i[0]++;
        });        
    }
/*
    public void removeInteractionView(InteractionView interactionView){
        this.container.getChildren().remove(interactionView);
    }
    */
    
}
