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
        try {
            String cat = categoryMenu.getValue();
            String iName = itemNameField.getText();
            float weight = Float.parseFloat(weightField.getText());
            int qty = Integer.parseInt(quantityField.getText());
        }
        catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        /*
        SQLiteJBDC db = new SQLiteJBDC();
        db.insert(cat, iName, qty, weight);

         */
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();


    }
}
