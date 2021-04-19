package MorlynnCastle.controller;

import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.item.Item;
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

import java.io.IOException;


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

    private ScrollPane inventoryPane;

    private InventoryPaneController inventoryPaneController;

    private Game game;

    @FXML
    public void initialize() throws IOException {
        this.game = new Game();
        this.game.initGame();
        this.sceneryPaneController.setGame(this.game);
        this.sceneryPaneController.setMorlynnCastleController(this);
        this.directionPaneController.setGame(this.game);
        this.sceneryPaneController.initScenery();
        this.directionPaneController.setSceneryPaneController(sceneryPaneController);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InventoryPane.fxml"));
        this.inventoryPane = loader.load();
        this.inventoryPaneController = loader.getController();
        this.characterPaneController.setMorlynnCastleController(this);
        this.inventoryPane.prefHeightProperty().bind(this.characterPane.heightProperty());
        this.inventoryPane.prefWidthProperty().bind(this.characterPane.widthProperty());
        gridPaneRoot.styleProperty().bind(Bindings.concat("-fx-font-size:", gridPaneRoot.widthProperty().divide(60).asString(), ";", gridPaneRoot.getStyle()));
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
                        this.inventoryPaneController.displayInventory(this.game.getHero().getInventory());
                    }
                }
                break;
            case LOOK:
                break;
            case USE:
                break;
            case EQUIP:
                break;
            case ATTACK:
                break;
            case TALK:
                break;
        }
    }

    public boolean take(Item item) {
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

    public void openInventory(){
        this.inventoryPaneController.displayInventory(this.game.getHero().getInventory());
        Stage inventoryStage = new Stage();
        inventoryStage.initOwner(this.gridPaneRoot.getScene().getWindow());
        if (this.inventoryPane.getScene() == null){
            inventoryStage.setScene(new Scene(this.inventoryPane));
        }
        else
            inventoryStage.setScene(this.inventoryPane.getScene());
        inventoryStage.setTitle("Inventory");
        inventoryStage.show();
    }
}
