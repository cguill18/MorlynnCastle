package MorlynnCastle.controller;

import MorlynnCastle.model.characters.*;
import MorlynnCastle.model.characters.Dialog;
import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.item.*;
import MorlynnCastle.model.space.Door;
import MorlynnCastle.model.space.DoorWithLock;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.view.InteractionView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
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

    /** attributs pour la liaison avec les différentes partie de la vue */
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
    private CharacterPaneController characterPaneController;

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

    private Stage containerstage;

    private ContainerPaneController containerPaneController;

    private MenuPaneController menuPaneController;

    /** attributs pour la liaison avec le modèle */
    private Game game;

    private Hero hero;

    /** attribut utilisé pour le drag and drop */
    private Usable launchCommandArg1;

    /** propriété pour la progressBar du personnage */
    private DoubleProperty currentHp = new SimpleDoubleProperty();
    private DoubleProperty maxHp = new SimpleDoubleProperty();
    private DoubleProperty ratioHp = new SimpleDoubleProperty();

    /*------------------------------------------- methodes ------------------------------------------------*/

    /** fonction d'initalisation de la vue */
    @FXML
    public void initialize() throws IOException {
        this.launchCommandArg1 = null;
        this.setStageContainer();
        this.containerPaneController.setMorlynnCastleController(this);
        this.sceneryPaneController.setMorlynnCastleController(this);
        this.characterPaneController.setMorlynnCastleController(this);
        this.directionPaneController.setMorlynnCastleController(this);
        this.gridPaneGame.styleProperty().bind(Bindings.concat("-fx-font-size:", gridPaneGame.widthProperty().divide(60).asString(), ";", gridPaneGame.getStyle()));
    }

    /** fonction de recuperation du modèle */
    public void initGame(Game game) {
        this.game = game;
        this.hero = this.game.getHero();
        this.currentHp.set(this.game.getHero().getCurrentHealthPoints());
        this.maxHp.set(this.game.getHero().getMaxHealthPoints());
        this.ratioHp.bind(Bindings.divide(this.currentHp, this.maxHp));
    }

    /** fonction de mise à jour de la vue */
    public void updateView() {
        this.characterPaneController.setName(this.hero.getName());
        this.sceneryPaneController.initScenery(this.hero.getPlace());
        this.characterPaneController.setProgress(this.ratioHp);
        this.mapPaneController.generateMap(this.hero.getPlace());
        this.characterPaneController.displayInventory(this.hero.getInventory());
        this.characterPaneController.updateEquipment(this.hero);
    }

    /** getter et setter */
    public BorderPane getBorderPaneRoot() {
        return borderPaneRoot;
    }

    public void setMenuPaneController(MenuPaneController menuPaneController) {
        this.menuPaneController = menuPaneController;
    }

    public Command getCommand() {
        return this.commandPaneController.getCommand();
    }

    /** met à null la commande */
    public void resetCommand() {
        this.commandPaneController.resetCommand();
    }

    /** mise à jour des points de vie du personnage dans la vue */
    public void updateHp() {
        this.currentHp.set(this.hero.getCurrentHealthPoints());
        this.maxHp.set(this.hero.getMaxHealthPoints());
        System.out.println(this.currentHp.get());
        System.out.println(this.maxHp.get());
        System.out.println(this.ratioHp.get());
    }

    /**
     * fonction qui analyse et appelle les bonnes methodes de commande
     * en fonction de la commande cliquée dans le CommandPane,
     * cette methode est appellée par le SceneryPaneController
     * */
    public void launchCommand(InteractionView<Interaction> interactionView) {
        Interaction interaction = interactionView.getInteraction();
        if (this.commandPaneController.getCommand() != null) {
            switch (this.commandPaneController.getCommand()) {
                case TAKE:
                    if (interaction instanceof Item) {
                        if (this.take((Item) interaction)) {
                            this.sceneryPaneController.removeInteractionView(interactionView);
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
            this.resetCommand();
        }
        this.checkEnd();
    }

    /**
     * fonction qui d'analyse des commande, comme celle ci dessus
     * cette methode est appellée par le CharacterPaneController
     * */
    public void launchCommandForInventory(InteractionView<Item> interactionView) {
        Interaction interaction = interactionView.getInteraction();
        if (this.commandPaneController.getCommand() != null) {
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
            this.resetCommand();
        }
    }

    /** fonction qui debute le drag and drop apres un "use", permet de stocker l'objet*/
    public void launchDrag(InteractionView<Item> interactionView) {
        if (this.commandPaneController.getCommand() == Command.USE) {
            Interaction inte = interactionView.getInteraction();
            if (inte instanceof Usable)
                this.launchCommandArg1 = (Usable) inte;
        }
    }

    /** fonction qui termine le drag and drop apres un "use", permet de récuperer l'objet */
    public void launchDrop(InteractionView<Interaction> interactionView) {
        Interaction inte = interactionView.getInteraction();
        if (inte instanceof Receiver) {
            if (this.launchCommandArg1 != null) {
                this.use(this.launchCommandArg1, (Receiver) inte);
                this.launchCommandArg1 = null;
            }
        }
    }

    /** --------- methodes pour le lancement des commandes ------- */

    private void look(Container interaction) {
        if (this.containerstage.getOwner() == null)
            this.containerstage.initOwner(this.borderPaneRoot.getScene().getWindow());
        this.containerPaneController.setContainerLooking(interaction);
        this.containerPaneController.displayContainer(interaction.getContent());
        this.containerstage.setWidth(this.gridPaneGame.getWidth() / 3);
        this.containerstage.setHeight(this.gridPaneGame.getHeight() / 3);
        this.containerstage.setOnCloseRequest(windowEvent -> this.containerPaneController.setContainerLooking(null));
        this.containerstage.setTitle("Content");
        this.containerstage.show();
    }

    private void equip(Equipable equipable) {
        this.hero.equip(equipable);
        this.characterPaneController.displayInventory(this.hero.getInventory());
        this.characterPaneController.updateEquipment(this.hero);
    }

    private void use(Usable usable) {
        boolean success = this.game.getHero().use(usable);
        if (success) {
            if (usable instanceof Book)
                this.dialogBoxController.addText(((Scroll) usable).getContent() + ".\n");
            else
                this.dialogBoxController.addText("You have used this object successfully.\n");
        } else {
            this.dialogBoxController.addText("You can't use this alone.\n");
        }
    }

    private void use(Usable usable, Receiver receiver) {
        boolean success = this.game.getHero().use(usable, receiver);
        if (success) {
            if (usable instanceof Key) {
                this.useKey(receiver);
            } else if (usable instanceof Scroll) {
                this.dialogBoxController.addText(((Scroll) usable).getEffect());
            } else this.dialogBoxController.addText("You have used this object successfully.\n");
        } else {
            if (usable instanceof Key)
                this.dialogBoxController.addText("Wrong key.\n");
            else this.dialogBoxController.addText("You can't use this item like this.\n");
        }

    }

    /** permet une mettre la vue à jour, la porte fermée devient ouverte etc */
    private void useKey(Receiver receiver) {
        if (receiver instanceof DoorWithLock) {
            if (((DoorWithLock) receiver).getIsLocked()) {
                ((DoorWithLock) receiver).setImage("locked_door.png");
                ((DoorWithLock) receiver).getMirrorDoorForDoorWithLock().setImage("locked_door.png");
            } else {
                ((DoorWithLock) receiver).setImage("unlocked_door.png");
                ((DoorWithLock) receiver).getMirrorDoorForDoorWithLock().setImage("unlocked_door.png");
            }
        } else {
            if (((ContainerWithLock) receiver).getIsLocked()) {
                ((ContainerWithLock) receiver).setImage("locked_chest.png");
            } else {
                ((ContainerWithLock) receiver).setImage("unlocked_chest.png");
            }
        }
        this.sceneryPaneController.displayRoomItems(this.hero.getPlace().getInteractions());
    }

    private boolean take(Item item) {
        if (item.isTakable()) {
            this.hero.take(item);
            this.dialogBoxController.addText("You add this " + item.getName() + " to your inventory.\n");
            this.characterPaneController.displayInventory(this.hero.getInventory());
            return true;
        } else {
            this.dialogBoxController.addText("You can't take this item.\n");
            return false;
        }
    }

    /** fonctions spécifiques aux coffres */
    public void takeFromContainer(Container container, Item item) {
        this.hero.takeFromContainer(container, item);
        this.dialogBoxController.addText("You add this " + item.getName() + " to your inventory.\n");
        this.characterPaneController.displayInventory(this.hero.getInventory());
    }

    /** permet de prendre l'entièreté du contenu d'un coffre */
    public void takeAllFromContainer(Container container) {
        this.hero.takeAllFromContainer(container);
        this.characterPaneController.displayInventory(this.hero.getInventory());
    }

    public void attack(Attackable attackable) {
        if (attackable instanceof NonPlayerCharacter) {
            this.attackNonPlayerCharacter((NonPlayerCharacter) attackable);
        } else this.hero.attack(attackable);
    }

    public void attackNonPlayerCharacter(NonPlayerCharacter npc) {
        this.hero.startCombat(npc);
        if (this.hero.getOngoingCombat() == null)
            this.dialogBoxController.addText("This character is dead.");
        else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CombatPane.fxml"));
                Parent root = loader.load();
                CombatPaneController combatPaneController = loader.getController();
                combatPaneController.setMorlynnCastleController(this);
                combatPaneController.initCombat(this.hero);
                this.gridPaneGame.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
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

    /** creation de la fenetre de l'interieur d'un coffre */
    public void setStageContainer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ContainerPane.fxml"));
        Parent root = loader.load();
        this.containerPaneController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        this.containerstage = stage;
    }

    /** fonction qui appelle la fonction de déplacement du héros dans le modèle et mise à jour de la vue */
    public void moveHero(String direction) {
        Door door = (Door) this.game.getHero().getPlace().getInteractions().get(direction);
        if (door != null) {
            if ((door instanceof DoorWithLock) && (((DoorWithLock) door).getIsLocked())) {
                this.dialogBoxController.addText("A locked door ? How surprising.");
            } else {
                this.game.getHero().go(door);
                this.mapPaneController.generateMap(this.hero.getPlace());
                this.sceneryPaneController.setBackground(this.game.getHero().getPlace());
                this.sceneryPaneController.displayRoomItems(this.game.getHero().getPlace().getInteractions());
                String description = this.game.getHero().getPlace().getDescription();
                this.dialogBoxController.addText(description);
                if (this.hero.getPlace().randomEncoutner()) {
                    this.attackNonPlayerCharacter(this.hero.getPlace().getAnEnemy());
                }
            }
        } else {
            this.dialogBoxController.addText("Are you sure that a door exists there ?");
        }
        this.checkEnd();
    }

    /** fonction appelée par le quit de la menuBar, quite le jeu ou revient à la page d'accueil */
    @FXML
    public void functionQuit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you really want to quit the game ?");
        ButtonType buttonTypeMenu = new ButtonType("Main Menu");
        ButtonType buttonTypeQuit = new ButtonType("Quit");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeMenu, buttonTypeQuit, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonTypeMenu)
                this.borderPaneRoot.getScene().setRoot(this.menuPaneController.getvBoxRoot());
            else if (result.get() == buttonTypeQuit)
                Platform.exit();
        }
    }

    /** fonction appelée par le help de la menuBar, affiche l'utilisation des commandes */
    @FXML
    public void functionHelp() {
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        TextArea textArea = new TextArea(this.game.helpText());
        textArea.setWrapText(true);
        helpStage.setScene(new Scene(textArea, 500, 300));
        helpStage.show();
    }

    /** fonction de sauvegarde de la partie, genere la fenetre de sauvegarde */
    @FXML
    public void functionSave() {
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
                ((Stage) textInputDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
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

    /** fonction pour charger une partie sauvegardée, ouvre la fenetre de sauvegarde */
    @FXML
    public void functionLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SavePane.fxml"));
            Parent root = loader.load();
            SavePaneController savePaneController = loader.getController();
            savePaneController.fillList(this.game.getSaveFiles());
            savePaneController.getBottomButton().setText("Load");
            Stage saveStage = new Stage();
            savePaneController.getBottomButton().setOnAction(event -> {
                String savename = savePaneController.getSelection();
                if (savename != null) {
                    this.load(savename);
                    saveStage.close();
                }
            });
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

    /** fonction auxiliaire le la fonction précedante, met à jour la vue pour correspondre à la sauvegarde */
    public void load(String filename) {
        this.game.load(filename);
        this.hero = this.game.getHero();
        this.sceneryPaneController.initScenery(this.hero.getPlace());
        this.mapPaneController.generateMap(this.hero.getPlace());
        this.characterPaneController.displayInventory(this.hero.getInventory());
        this.characterPaneController.clearEquipment();
        this.characterPaneController.updateEquipment(this.hero);

    }

    /** fonction qui verifie si le héros est mort ou si il a atteind l'objectif */
    public void checkEnd() {
        switch (this.game.checkEnd()) {
            case DEAD:
                this.gameOver();
                break;
            case END:
                this.end();
        }
    }

    /** fenetre qui marque la fin du jeu reussit */
    public void end() {
        Alert alert = new Alert(AlertType.NONE);
        alert.setContentText("Congratulations ! You reached the end of the game !");
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
        this.borderPaneRoot.getScene().setRoot(this.menuPaneController.getvBoxRoot());
    }

    /** fenetre qui marque la fin du jeu après avoir été tué */
    public void gameOver() {
        Alert alert = new Alert(AlertType.NONE);
        alert.setContentText("You died.\nGame Over.");
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
        this.borderPaneRoot.getScene().setRoot(this.menuPaneController.getvBoxRoot());
    }

}
