package MorlynnCastle.controller;

import MorlynnCastle.model.characters.*;
import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.item.*;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.view.InteractionView;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.StageStyle;


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

    private Hero hero;

    private Usable launchCommandArg1;
    
    private Stage containerstage;
    
    private ContainerPaneController containerPaneController;

    @FXML
    public void initialize() throws IOException {
        this.launchCommandArg1 = null;
        this.game = new Game();
        this.hero = this.game.getHero();
        this.game.initGame();
        this.containerstage = this.setStageContainer();
        this.containerPaneController.setMorlynnCastleController(this);
        this.containerPaneController.setGame(this.game);
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

    public GridPane getGridPaneRoot() {
        return gridPaneRoot;
    }

    public void launchCommand(InteractionView interactionView) throws IOException {
        Interaction interaction = interactionView.getInteraction();
        switch (this.commandPaneController.getCommand()) {
            case TAKE:
                if (interaction instanceof Item) {
                    if (this.take((Item) interaction)){
                        this.sceneryPaneController.removeInteractionView(interactionView);
                        this.characterPaneController.displayInventory(this.hero.getInventory());
                    }
                }
                break;
            case LOOK:
                //this.dialogBoxController.addText(interaction.getDescription());
                if (interaction instanceof Container) {
                    this.containerPaneController.displayContainer(((Container) interaction).getContent());
                    this.containerstage.setTitle("Containts of the chest");
                    this.containerstage.show();
                }
                break;
            case USE: //affiche popup pour dire que c'est pas possible
                break;
            case EQUIP:
                break;
            case ATTACK:
                if (interaction instanceof Attackable){
                    this.attack((Attackable) interaction);
                }
                break;
            case TALK:
                if (interaction instanceof Talkable){
                    this.talk((Talkable) interaction);
                }
                break;
        }
    }

    public void launchCommandForInventory(InteractionView interactionView){
    /*    switch (this.commandPaneController.getCommand()) {
            case USE -> {
                Interaction interaction = interactionView.getInteraction();
                if (interaction instanceof Usable){
                    this.use((Usable)interaction);
                }
            }
        }*/
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
            this.hero.take(item);
            this.dialogBoxController.addText("You add this " + item.getName() + " to your inventory.\n");
            return true;
        }
        else{
            this.dialogBoxController.addText("You can't take this item.\n");
            return false;
        }
    }

    public void attack(Attackable attackable) throws IOException {
        if (attackable instanceof NonPlayerCharacter) {
            this.attackNonPlayerCharacter((NonPlayerCharacter) attackable);
        } else this.hero.attack(attackable);
    }

    public void attackNonPlayerCharacter(NonPlayerCharacter npc) throws IOException {
        if (npc.isAlive()) {
            if (this.hero.getOngoingCombat() == null) {
                this.startCombat(npc);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CombatPane.fxml"));
                Parent root = loader.load();
                CombatPaneController combatPaneController = loader.getController();
                combatPaneController.setMorlynnCastleController(this);
                combatPaneController.initCombat(this.hero);
                this.gridPaneRoot.getScene().setRoot(root);
            }
        }
    }


    public void startCombat(NonPlayerCharacter npc) {
        if (!npc.isHostile()) {
            npc.setHostile(true);
        }
        Combat c = new Combat(this.hero, this.hero.getPlace().getEnemiesInPlace());
        this.hero.setOngoingCombat(c);
    }

    private void talk(Talkable interaction) {
        this.commandPane.setDisable(true);
        this.directionPane.setDisable(true);
        this.sceneryPane.setDisable(true);
        this.characterPane.setDisable(true);
        Dialog dialog = interaction.getDialog();
        for (int i = 0 ; i < dialog.getPlayerChoices().size() ; i++){
            String answer = dialog.getDialogs().get(i);
            EventHandler eventHandler = new EventHandler() {
                @Override
                public void handle(Event event) {
                    dialogBoxController.addText(answer);
                }
            };
            this.dialogBoxController.addDialog(dialog.getPlayerChoices().get(i),eventHandler);
        }
        this.dialogBoxController.addDialog("Goodbye.", event -> {
            this.dialogBoxController.endDialog();
            this.commandPane.setDisable(false);
            this.directionPane.setDisable(false);
            this.sceneryPane.setDisable(false);
            this.characterPane.setDisable(false);
        });
        this.dialogBoxController.startDialog();
    }

public Stage setStageContainer() throws IOException{        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ContainerPane.fxml"));
        Parent root = (Parent) loader.load();
        this.containerPaneController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        return stage;
    }

//    public void openInventory(){
//        this.inventoryPaneController.displayInventory(this.hero.getInventory());
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

}
