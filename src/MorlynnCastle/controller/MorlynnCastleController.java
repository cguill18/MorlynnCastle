package MorlynnCastle.controller;

import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.item.*;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.view.InteractionView;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.Popup;


public class MorlynnCastleController {

    @FXML
    private GridPane gridPaneRoot;

    @FXML
    private GridPane sceneryPane;

    @FXML
    private SceneryPaneController sceneryPaneController;

    @FXML
    private GridPane commandPane;

    @FXML
    private CommandPaneController commandPaneController;

    @FXML
    private VBox characterPane;

    @FXML CharacterPaneController characterPaneController;

    @FXML
    private GridPane directionPane;

    @FXML
    private DirectionPaneController directionPaneController;

    @FXML
    private VBox dialogBox;

    @FXML
    private DialogBoxController dialogBoxController;

    private Game game;

    private Usable launchCommandArg1;

    @FXML
    public void initialize() throws IOException {
        this.launchCommandArg1 = null;
        this.game = new Game();
        this.game.initGame();
        this.sceneryPaneController.setGame(this.game);
        this.sceneryPaneController.setMorlynnCastleController(this);
        this.characterPaneController.setMorlynnCastleController(this);
        this.directionPaneController.setGame(this.game);
        this.sceneryPaneController.initScenery();
        this.directionPaneController.setSceneryPaneController(sceneryPaneController);
        this.directionPaneController.setDialogBoxController(dialogBoxController);
        this.gridPaneRoot.styleProperty().bind(Bindings.concat("-fx-font-size:", gridPaneRoot.widthProperty().divide(60).asString(), ";", gridPaneRoot.getStyle()));
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void launchCommand(InteractionView interactionView) {
        switch (this.commandPaneController.getCommand()) {
            case TAKE:
                Interaction interaction = interactionView.getInteraction();
                if (interaction instanceof Item) {
                    if (this.take((Item) interaction)){
                        this.sceneryPaneController.removeInteractionView(interactionView);
                        this.characterPaneController.displayInventory(this.game.getHero().getInventory());
                    }
                }
                break;
            case LOOK:
                Interaction interaction01 = interactionView.getInteraction();
                //this.dialogBoxController.addText(interaction01.getDescription());
                this.lookTooltip(interactionView);
                if (interaction01 instanceof Container) {
                  //  this.lookContainer(interaction01);
                }
                break;
            case USE: //affiche popup pour dire que c'est pas possible
                break;
            case EQUIP:
                break;
            case ATTACK:
                break;
            case TALK:
                break;
        }
    }

    public void launchCommandForInventory(InteractionView interactionView){
        switch (this.commandPaneController.getCommand()) {
            case USE -> {
                Interaction interaction = interactionView.getInteraction();
                if (interaction instanceof Usable){
                    this.use((Usable)interaction);
                }
            }
        }
    }

    public void launchDrop(InteractionView interactionView){
        Interaction inte = interactionView.getInteraction();
        if (inte instanceof Receiver) {
            this.use(this.launchCommandArg1, (Receiver)inte);
            this.launchCommandArg1 = null;
        }
    }

    public void launchDrag(InteractionView interactionView){
        Interaction inte = interactionView.getInteraction();
        if (inte instanceof Usable)
            this.launchCommandArg1 = (Usable)inte;
    }

    /*public void launchCommandForInventory(InteractionView interactionView1, InteractionView interactionView2){
        if (this.commandPaneController.getCommand() == Command.USE) {
            Interaction inte1 = interactionView1.getInteraction();
            Interaction inte2 = interactionView2.getInteraction();
            if (inte1 instanceof Usable && inte2 instanceof Receiver){}
        }
    }*/



    private boolean use(Usable usable) {
        this.game.getHero().use(usable);
        this.dialogBoxController.addText("You use an item.\n");
        return true;
    }

    private boolean use(Usable usable, Receiver receiver){
        this.game.getHero().use(usable,receiver);
        this.dialogBoxController.addText("You use an item on an another.\n");
        return true;
    }

    private boolean take(Item item) {
        if (item.isTakable()) {
            this.game.getHero().take(item);
            this.dialogBoxController.addText("You add this" + item.getName() + " to your inventory.\n");
            return true;
        }
        else{
            this.dialogBoxController.addText("You can't take this item.\n");
            return false;
        }
    }


//    public void openInventory(){
//        this.inventoryPaneController.displayInventory(this.game.getHero().getInventory());
//        Stage inventoryStage = new Stage();
//        inventoryStage.initOwner(this.gridPaneRoot.getScene().getWindow());
//        if (this.inventoryPane.getScene() == null){
//            inventoryStage.setScene(new Scene(this.inventoryPane));
//        }
//        else
//            inventoryStage.setScene(this.inventoryPane.getScene());
//        inventoryStage.setTitle("Inventory");
//        inventoryStage.show();
//    }
    
    public void lookTooltip(InteractionView interactionView) {
        Tooltip tooltip = new Tooltip();
        tooltip.styleProperty().bind(gridPaneRoot.styleProperty());
        tooltip.setText(interactionView.getInteraction().getDescription());   
        Tooltip.install(interactionView, tooltip);
    }
    
    public void lookContainer(Interaction container){        
    /*    stage.setTitle("Contents of the chest");
        container.s(this);
*/
    }
  /*  
    public void handle(Event event) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        GridPane gridPane = new GridPane();
        popup.getContent().add(gridPane);
        if (!popup.isShowing())
                    popup.show(stage); 
    }*/
}
