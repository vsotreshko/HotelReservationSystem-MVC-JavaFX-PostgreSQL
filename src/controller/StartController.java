package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    private Button signUp;

    @FXML
    private Button signIn;

    @FXML
    public void onSignUp(ActionEvent event) throws IOException {
        Parent signUpScene = FXMLLoader.load(getClass().getResource("../view/signUp.fxml"));
        Scene newScene = new Scene(signUpScene);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @FXML
    public void onSignIn(ActionEvent event) throws IOException{
        Parent signInScene = FXMLLoader.load(getClass().getResource("../view/signIn.fxml"));
        Scene newScene = new Scene(signInScene);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setScene(newScene);
        primaryStage.show();
    }
}
