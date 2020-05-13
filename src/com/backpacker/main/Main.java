package com.backpacker.main;

import com.backpacker.resources.utility.SQLiteJBDC;
import com.backpacker.resources.utility.tools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/backpacker/resources/Primary.fxml"));
        stage.setTitle("Backpacker Planner");
        stage.getIcons().add(new Image("com/backpacker/resources/css/icons/title.png"));
        stage.setScene(new Scene(root, 1200, 700));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


