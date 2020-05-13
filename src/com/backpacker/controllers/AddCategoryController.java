package com.backpacker.controllers;

import com.backpacker.resources.utility.tools;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCategoryController implements Initializable {

    public TextField addCatText;
    public Button enterButton;
    public static String newCat;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void enterClick(ActionEvent actionEvent) {
        newCat = addCatText.getText();
        tools.setCategories(newCat);
        Stage stage = (Stage) enterButton.getScene().getWindow();
        stage.close();
    }
}
