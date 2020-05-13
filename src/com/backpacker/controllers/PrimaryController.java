package com.backpacker.controllers;

import com.backpacker.resources.utility.SQLiteJBDC;
import com.backpacker.resources.utility.tools;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    public ScrollPane tripView;
    public AnchorPane primaryView;
    public Pane welcomeView;
    public AnchorPane invView;
    public Button addNew;
    public TabPane invTabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeView.toFront();
        try {
            tools.readCategory();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        tools.refreshTabs(invTabPane);
        //tools.createTab(invTabPane, "Tools");
        //tools.setCategories("Tools");
    }

    public void tripClick(ActionEvent actionEvent) {
        //opens trip planner pane
        tripView.toFront();
    }

    public void invClick(ActionEvent actionEvent) {
        //open inventory pane
        invView.toFront();
    }

    public void addItemRelease(MouseEvent mouseEvent) throws IOException {
        //opens add item popup window
        Parent popup = FXMLLoader.load(getClass().getResource("/com/backpacker/resources/AddItem.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add New Item");
        stage.setScene(new Scene(popup));
        stage.showAndWait();
        //when user add items the table is refreshed from database
        System.out.println("window closed");

        for(TableView<ObservableList<String>> x : tools.tableViewNames) {
            x.getColumns().clear();
            tools.buildData(x, x.getId());
            }
        }

    public void tapPaneKeyPress(KeyEvent keyEvent) {
        SQLiteJBDC db = new SQLiteJBDC();
        if(keyEvent.getCode() == KeyCode.DELETE) {
            TableView<ObservableList<String>> x = (TableView<ObservableList<String>>) invTabPane.getSelectionModel().getSelectedItem().getContent();
            int id = Integer.parseInt(x.getSelectionModel().getSelectedItem().get(0));
            String table = invTabPane.getSelectionModel().getSelectedItem().getText();
            db.deleteItem(id, table);
            System.out.println(x.getSelectionModel().getSelectedItem() + " was deleted");
            x.getColumns().clear();
            tools.buildData(x, table);
        }
        else if(keyEvent.getCode() == KeyCode.Z) {
            String tab = invTabPane.getSelectionModel().getSelectedItem().getText();
            db.deleteTable(tab);
            tools.removeCategory(tab);
            tools.tabCount=0;
            invTabPane.getTabs().clear();
            tools.refreshTabs(invTabPane);
        }
    }

    public void tabPaneClick(MouseEvent mouseEvent) throws IOException {
        if(invTabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("+")) {
            Parent popup = FXMLLoader.load(getClass().getResource("/com/backpacker/resources/AddCategory.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Category");
            stage.setScene(new Scene(popup));
            stage.showAndWait();
            if(!(AddCategoryController.newCat == null)) {
                tools.createTab(invTabPane, AddCategoryController.newCat);
                AddCategoryController.newCat = null;
                tools.tabCount=0;
                invTabPane.getTabs().clear();
                tools.refreshTabs(invTabPane);
            }
        }
    }
}
