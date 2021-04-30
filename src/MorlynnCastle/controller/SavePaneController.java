package MorlynnCastle.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavePaneController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<File> tableView;

    @FXML
    private Button bottomButton;

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public Button getBottomButton() {
        return bottomButton;
    }

    /**
     * Initialise la TableView qui prend des fichiers File en contenu
     * Génère les colonnes File name et Date
     * Pour la colonne File name, les valeurs correspondent au nom du fichier sans l'extension
     * Pour la colonne Date, on transforme la valeur lastModified qui est un Long en un String
     * à partir d'un objet Date et d'un SimpleDateFormat
     */
    @FXML
    public void initialize() {
        tableView.setPlaceholder(new Label("No save detected."));
        TableColumn<File, String> fileNameCol = new TableColumn<>("File name");
        TableColumn<File, Long> fileLastModCol = new TableColumn<>("Date");
        tableView.getColumns().addAll(fileNameCol, fileLastModCol);
        fileNameCol.setCellValueFactory(file -> {
            SimpleStringProperty property = new SimpleStringProperty(file.getValue().getName().replaceFirst("[.][^.]+$", ""));
            return property;
        });
        fileLastModCol.setCellFactory(column -> {
            TableCell<File, Long> cell = new TableCell<File, Long>() {

                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty)
                        setText(null);
                    else {
                        Date date = new Date(item);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                        setText(format.format(date));
                    }
                }
            };
            return cell;
        });
        fileLastModCol.setCellValueFactory(file -> {
            SimpleLongProperty property = new SimpleLongProperty(file.getValue().lastModified());
            return property.asObject();
        });
    }

    /**
     * Remplit le contenu de la TableView avec les fichiers en paramètres
     * @param files Tableau de fichiers de sauvegarde
     */
    public void fillList(File[] files) {
        ObservableList<File> content = FXCollections.observableArrayList(files);
        tableView.setItems(content);
    }

    /**
     * Récupère la sélection de la TableView et renvoie uniquement le nom du fichier de sauvegarde
     */
    public String getSelection() {
        File selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null)
            return selectedItem.getName().replaceFirst("[.][^.]+$", "");
        else
            return null;
    }
}
