package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EndOfReservationController {

    @FXML
    public void backToMainPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mainPage.fxml"));
        Parent mainPage = loader.load();
        Scene newScene = new Scene(mainPage);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setScene(newScene);
        primaryStage.show();
    }
}
