package main;
/*
configuration -> add vm option -> copy paste: --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
 */
/**
 * @author John Nguyen 2/28/2022
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    static Stage stage;
    static Parent scene;

    /**
     * @param stage the stage to run the app
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param event the event to create a stage
     * @param fxml the fxml file to navigate to
     * @throws IOException
     */
    public static void navigate(ActionEvent event, String fxml) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Main.class.getResource("/view/" + fxml));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
