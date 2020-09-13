package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import model.DataBase;
import java.time.LocalDate;

public class SignUpController {
    @FXML
    private DatePicker dateofBirth;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    public void confirmAction(ActionEvent event){
        String name = firstName.getText();
        String surname = lastName.getText();
        LocalDate birthday = dateofBirth.getValue();
        String loginStr = login.getText();
        String pass = password.getText();

        Client client = new Client(0, name, surname, birthday, loginStr, pass);

        int id = DataBase.addNewClient(client);
        client.setID( id );
    }

    @FXML
    void backOn(ActionEvent event) throws  Exception{
        Parent logOut = FXMLLoader.load(getClass().getResource("../view/start.fxml"));
        Scene newScene = new Scene(logOut);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("New reservation");
        primaryStage.setScene(newScene);
        primaryStage.show();
    }
}
