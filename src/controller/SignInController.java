package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DataBase;

import java.io.IOException;

public class SignInController {

    @FXML
    private Button confirmButton;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    @FXML
    void login(ActionEvent event) throws Exception{

        if (login.getText().isEmpty() || password.getText().isEmpty()) {
            errorLabel.setText("Every field must be filled.");
            return;
        }

        if (DataBase.signin(login.getText(), password.getText()) == false) {
            errorLabel.setText("The login or password is incorrect.");
            return;
        }


        Parent MainPage = FXMLLoader.load(getClass().getResource("../view/mainPage.fxml"));
        Scene newScene = new Scene(MainPage);

        DataBase.setLoginedUser(login.getText());

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @FXML
    void backAction(ActionEvent event) throws IOException {
        Parent MainPage = FXMLLoader.load(getClass().getResource("../view/start.fxml"));
        Scene newScene = new Scene(MainPage);
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("Reservation system");
        primaryStage.setScene(newScene);
        primaryStage.show();
    }
}
