package com.backpacker.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.backpacker.resources.utility.SQLiteJBDC;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

    public TextField itemNameField;
    public ComboBox<String> categoryMenu;
    public TextField weightField;
    public TextField quantityField;
    public Button addButton;
    public Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] catOptions = {"Clothing", "Sleep"};
        ObservableList<String> category = FXCollections.observableArrayList(catOptions);
        categoryMenu.setItems(category);
    }

    public void addItemClick(ActionEvent actionEvent) {
        String cat = categoryMenu.getValue();
        String iName = itemNameField.getText();
        int qty = Integer.parseInt(quantityField.getText());
        //add error catch for all fields
        float weight=0;
        try {
            weight = Float.parseFloat(weightField.getText());
        }
        catch (NumberFormatException e) {
            if (e.getMessage().equals("empty String")) {
                weight = 0;
            }
        }
        //takes data from fields and inserts item to database
        SQLiteJBDC db = new SQLiteJBDC();
        db.insert(cat, iName, qty, weight);
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
