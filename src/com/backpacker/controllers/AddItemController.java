package com.backpacker.controllers;

import com.backpacker.resources.utility.tools;
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
import java.text.DecimalFormat;
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
        ObservableList<String> category = FXCollections.observableArrayList(tools.getCategories());
        categoryMenu.setItems(category);
    }

    public void addItemClick(ActionEvent actionEvent) {
        float grams = 0;
        float pounds = 0;
        float oz = 0;


        String cat = categoryMenu.getValue();
        String iName = itemNameField.getText();
        int qty = Integer.parseInt(quantityField.getText());
        //add error catch for all fields
        try {
            oz = Float.parseFloat(weightField.getText());
            grams = (float) tools.ozToGrams(oz);
            pounds = (float) tools.ozToPounds(oz);
        }
        catch (NumberFormatException e) {
            if (e.getMessage().equals("empty String")) {
                oz = 0;
            }
        }
        //takes data from fields and inserts item to database
        SQLiteJBDC db = new SQLiteJBDC();
        System.out.println();
        db.insertItem(cat, iName, qty, oz, grams, pounds);
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
