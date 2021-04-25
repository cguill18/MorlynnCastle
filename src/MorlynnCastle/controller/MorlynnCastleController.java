package MorlynnCastle.controller;

import MorlynnCastle.model.characters.*;
import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.item.*;
import MorlynnCastle.model.space.Door;
import MorlynnCastle.model.space.DoorWithLock;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.view.InteractionView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import javafx.event.Event;
import javafx.event.EventHandler;


public class MorlynnCastleController {

    @FXML
    private BorderPane borderPaneRoot;

    @FXML
    private GridPane gridPaneGame;

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

    @FXML
    CharacterPaneController characterPaneController;

    @FXML
    private GridPane directionPane;

    @FXML
    private DirectionPaneController directionPaneController;

    @FXML
    private VBox dialogBox;

    @FXML
    private DialogBoxController dialogBoxController;

    @FXML
    private GridPane mapPane;

    @FXML
    private MapPaneController mapPaneController;

    private Game game;

    private Hero hero;

    private Usable launchCommandArg1;

    private Stage containerstage;

    private ContainerPaneController containerPaneController;

    private DoubleProperty currentHp = new SimpleDoubleProperty();
    private DoubleProperty maxHp = new SimpleDoubleProperty();
    private DoubleProperty ratioHp = new SimpleDoubleProperty();

    @FXML
    public void initialize() throws IOException {
        this.launchCommandArg1 = null;
        this.game = new Game();
        this.hero = this.game.getHero();
        this.currentHp.set(this.game.getHero().getCurrentHealthPoints());
        this.maxHp.set(this.game.getHero().getMaxHealthPoints());
        this.ratioHp.bind(Bindings.divide(this.currentHp,this.maxHp));
        this.game.initGame();
        this.containerstage = this.setStageContainer();
        this.containerPaneController.setMorlynnCastleController(this);
        this.containerPaneController.setCharacterPaneController(characterPaneController);
        this.containerPaneController.setDialogBoxController(dialogBoxController);
        this.containerPaneController.setGame(this.game);
        this.sceneryPaneController.setGame(this.game);
        this.sceneryPaneController.setMorlynnCastleController(this);
        this.sceneryPaneController.initScenery(this.hero.getPlace());
        this.characterPaneController.setMorlynnCastleController(this);
        this.characterPaneController.setProgress(this.ratioHp);
        this.mapPaneController.setGame(this.game);
        this.mapPaneController.generateMap();
        this.directionPaneController.setGame(this.game);
        this.directionPaneController.setMorlynnCastleController(this);
        this.gridPaneGame.styleProperty().bind(Bindings.concat("-fx-font-size:", gridPaneGame.widthProperty().divide(60).asString(), ";", gridPaneGame.getStyle()));
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GridPane getGridPaneGame() {
        return gridPaneGame;
    }

    public BorderPane getBorderPaneRoot() {
        return borderPaneRoot;
    }

    public Usable getLaunchCommandArg1() {
        return this.launchCommandArg1;
    }

    public void updateHp(){
        this.currentHp.set(this.hero.getCurrentHealthPoints());
        this.maxHp.set(this.hero.getMaxHealthPoints());
        System.out.println(this.currentHp.get());
        System.out.println(this.maxHp.get());
        System.out.println(this.ratioHp.get());
    }

    public void launchCommand(InteractionView interactionView) throws IOException {
        Interaction interaction = interactionView.getInteraction();
        if (this.commandPaneController.getCommand() != null) {
            switch (this.commandPaneController.getCommand()) {
                case TAKE:
                    if (interaction instanceof Item) {
                        if (this.take((Item) interaction)) {
                            this.sceneryPaneController.removeInteractionView(interactionView);
                            this.characterPaneController.displayInventory(this.hero.getInventory());
                        }
                    }
                    break;
                case LOOK:
                    this.dialogBoxController.addText(interaction.getDescription());
                    if (interaction instanceof Container) {
                        if ((interaction instanceof ContainerWithLock) && (((ContainerWithLock) interaction).getIsLocked())) {
                            this.dialogBoxController.addText("This chest is locked. Maybe a key could help.");
                        } else {
                            this.look((Container) interaction);
                        }
                    }
                    break;
                case USE:
                    this.dialogBoxController.addText("Please use an item in your inventory.\n");
                    break;
                case EQUIP:
                    break;
                case ATTACK:
                    if (interaction instanceof Attackable) {
                        this.attack((Attackable) interaction);
                    }
                    break;
                case TALK:
                    if (interaction instanceof Talkable) {
                        this.talk((Talkable) interaction);
                    }
                    break;
            }
        } else {
            this.dialogBoxController.addText("Please click a command before.\n");
        }
    }

    private void look(Container interaction) {
        if (this.containerstage.getOwner() == null)
            this.containerstage.initOwner(this.borderPaneRoot.getScene().getWindow());
        this.containerPaneController.setContainerLooking(interaction);
        this.containerPaneController.displayContainer(interaction.getContent());
        this.containerstage.setTitle("Containts of the chest");
        this.containerstage.show();
    }

    public void launchCommandForInventory(InteractionView interactionView) {
        Interaction interaction = interactionView.getInteraction();
        switch (this.commandPaneController.getCommand()) {
            case USE:
                if (interaction instanceof Usable) {
                    this.use((Usable) interaction);
                }
                break;
            case EQUIP:
                if (interaction instanceof Equipable)
                    this.equip((Equipable) interaction);
                break;
        }
    }

    private void equip(Equipable equipable) {
        this.hero.equip(equipable);
        this.characterPaneController.displayInventory(this.hero.getInventory());
        this.characterPaneController.updateEquipment(this.hero);
    }

    public void launchDrop(InteractionView interactionView) {
        Interaction inte = interactionView.getInteraction();
        if (inte instanceof Receiver) {
            if (this.launchCommandArg1 != null) {
                this.use(this.launchCommandArg1, (Receiver) inte);
                this.launchCommandArg1 = null;
            }
        }
    }

    public void launchDrag(InteractionView interactionView) {
        if (this.commandPaneController.getCommand() == Command.USE) {
            Interaction inte = interactionView.getInteraction();
            if (inte instanceof Usable)
                this.launchCommandArg1 = (Usable) inte;
        }
    }


    private boolean use(Usable usable) {
        boolean success = this.game.getHero().use(usable);
        if (success) {
            if (usable instanceof Book)
                this.dialogBoxController.addText(((Scroll) usable).getContent()+".\n");
            else
                this.dialogBoxController.addText("You have used this object successfully.\n");
            return true;
        } else {
            this.dialogBoxController.addText("You can't use this alone.\n");
            return false;
        }
    }

    private boolean use(Usable usable, Receiver receiver) {
        boolean success = this.game.getHero().use(usable, receiver);
        if (success) {
            if (usable instanceof Key && receiver instanceof DoorWithLock) {
                ((DoorWithLock) receiver).setImage("unlocked_door.png");
                ((DoorWithLock)receiver).getMirrorDoorForDoorWithLock().setImage("unlocked_door.png");
                this.sceneryPaneController.generateRoomItems();
            }
            else this.dialogBoxController.addText("You have used this object successfully.\n");
            return true;
        } else {
            if (usable instanceof Key)
                this.dialogBoxController.addText("Wrong key.\n");
            else
                this.dialogBoxController.addText("You can't use this item on an another.\n");
            return false;
        }
    }

    private boolean take(Item item) {
        if (item.isTakable()) {
            this.hero.take(item);
            this.dialogBoxController.addText("You add this " + item.getName() + " to your inventory.\n");
            return true;
        } else {
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
                this.gridPaneGame.getScene().setRoot(root);
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
        Dialog dialog = interaction.talk();
        if (dialog == null)
            this.dialogBoxController.addText("This character is dead.");
        else {
            this.commandPane.setDisable(true);
            this.directionPane.setDisable(true);
            this.sceneryPane.setDisable(true);
            this.characterPane.setDisable(true);
            for (int i = 0; i < dialog.getPlayerChoices().size(); i++) {
                String answer = dialog.getDialogs().get(i);
                EventHandler eventHandler = new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        dialogBoxController.addText(answer);
                    }
                };
                this.dialogBoxController.addDialog(dialog.getPlayerChoices().get(i), eventHandler);
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
    }

    public Stage setStageContainer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ContainerPane.fxml"));
        Parent root = (Parent) loader.load();
        this.containerPaneController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);

        return stage;
    }

    public void moveHero(String direction) {
        Door door = (Door) this.game.getHero().getPlace().getInteractions().get(direction);
        if (door != null) {
            if ((door instanceof DoorWithLock) && (((DoorWithLock) door).getIsLocked())) {
                this.dialogBoxController.addText("A locked door ? How surprising.");
            } else {
                this.game.getHero().go(door);
                this.sceneryPaneController.generateRoomItems();
                this.mapPaneController.generateMap();
                this.sceneryPaneController.setBackground(this.game.getHero().getPlace());
                String description = this.game.getHero().getPlace().getDescription();
                this.dialogBoxController.addText(description);
            }
        } else {
            this.dialogBoxController.addText("Are you sure that a door exists there ?");
        }
    }

    @FXML
    public void functionQuit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you really want to quit the game ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            Platform.exit();
    }

    @FXML
    public void functionHelp(ActionEvent actionEvent) {
        Alert alert = new Alert(AlertType.NONE);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.setTitle("Help");
        alert.setContentText(this.game.helpText());
        alert.showAndWait();
    }

    @FXML
    public void functionSave(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SavePane.fxml"));
            Parent root = loader.load();
            SavePaneController savePaneController = loader.getController();
            savePaneController.fillList(this.game.getSaveFiles());
            savePaneController.getBottomButton().setText("Overwrite");
            Button newSaveButton = new Button("New save");
            BorderPane.setAlignment(newSaveButton, Pos.CENTER);
            newSaveButton.setOnAction(event -> {
                TextInputDialog textInputDialog = new TextInputDialog("");
                textInputDialog.setTitle("New save");
                textInputDialog.setHeaderText("Type the name of the save file you wish to create, without extension.");
                ((Stage)textInputDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
                Optional<String> result = textInputDialog.showAndWait();
                result.ifPresent(name -> {
                    this.game.save(name);
                    savePaneController.fillList(this.game.getSaveFiles());
                });
            });
            savePaneController.getBorderPane().setTop(newSaveButton);
            savePaneController.getBottomButton().setOnAction(event -> {
                String savename = savePaneController.getSelection();
                if (savename != null) {
                    this.game.save(savename);
                    savePaneController.fillList(this.game.getSaveFiles());
                }

            });
            Stage saveStage = new Stage();
            saveStage.setTitle("Save");
            saveStage.initOwner(this.borderPaneRoot.getScene().getWindow());
            saveStage.setAlwaysOnTop(true);
            saveStage.initModality(Modality.WINDOW_MODAL);
            saveStage.setScene(new Scene(root));
            saveStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void functionLoad(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SavePane.fxml"));
            Parent root = loader.load();
            SavePaneController savePaneController = loader.getController();
            savePaneController.fillList(this.game.getSaveFiles());
            savePaneController.getBottomButton().setText("Load");
            savePaneController.getBottomButton().setOnAction(event -> {
                String savename = savePaneController.getSelection();
                if (savename != null)
                    this.game.load(savename);
            });
            Stage saveStage = new Stage();
            saveStage.setTitle("Load");
            saveStage.initOwner(this.borderPaneRoot.getScene().getWindow());
            saveStage.setAlwaysOnTop(true);
            saveStage.initModality(Modality.WINDOW_MODAL);
            saveStage.setScene(new Scene(root));
            saveStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String filename){
        this.game.load(filename);
        this.hero = this.game.getHero();
        System.out.println(this.game.getHero().getArmor());
        System.out.println(this.hero.getArmor());
        this.sceneryPaneController.initScenery(this.hero.getPlace());
        this.mapPaneController.generateMap();
        this.characterPaneController.displayInventory(this.hero.getInventory());
        this.characterPaneController.clearEquipment();
        this.characterPaneController.updateEquipment(this.hero);

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
