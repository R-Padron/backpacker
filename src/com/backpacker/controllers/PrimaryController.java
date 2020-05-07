package com.backpacker.controllers;

import com.backpacker.resources.utility.tools;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    public Button invButton;
    public Button tripButton;
    public AnchorPane primaryView;
    public ScrollPane inventoryView;
    public ScrollPane tripView;
    public Pane welcomeView;
    public Button addNew;
    public TableView<ObservableList<String>> clothingTable;
    public TableView<ObservableList<String>> sleepTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeView.toFront();
        tools.buildData(sleepTable, "Sleep");
        tools.buildData(clothingTable, "Clothing");
    }

    public void tripClick(ActionEvent actionEvent) {
        //opens trip planner pane
        tripView.toFront();
    }

    public void invClick(ActionEvent actionEvent) {
        //open inventory pane
        inventoryView.toFront();
    }

    public void addItemRelease(MouseEvent mouseEvent) throws IOException {
        //opens add item popup window
        Parent popup = FXMLLoader.load(getClass().getResource("/com/backpacker/resources/AddItem.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add new item");
        stage.setScene(new Scene(popup));
        stage.showAndWait();
        //when user add items the table is refreshed from database
        System.out.println("window closed");
        clothingTable.getColumns().clear();
        sleepTable.getColumns().clear();
        tools.buildData(sleepTable, "Sleep");
        tools.buildData(clothingTable, "Clothing");
    }

    public void clothingTableClicked(MouseEvent mouseEvent) {
        System.out.println(clothingTable.getSelectionModel().getSelectedItem());
    }
}
