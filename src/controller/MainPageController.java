package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainPageController {

    @FXML
    void startMakingReservation(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/makeReservation.fxml"));
        Parent makeReservation = loader.load();
        Scene newScene = new Scene(makeReservation);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("New reservation");
        primaryStage.setScene(newScene);

        MakeReservationController cancelResController = loader.getController();
        cancelResController.initMakeReservation();

        primaryStage.show();
    }

    @FXML
    void startCancelingReservation(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("../view/cancelReservation.fxml") );
        Parent cancelReservation = loader.load();
        Scene newScene = new Scene(cancelReservation);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("Cancel reservation");
        primaryStage.setScene(newScene);

        CancelReservationController cancelResController = loader.getController();
        cancelResController.initCancelReservationWindow();

        primaryStage.show();
    }

    @FXML
    void showHotelsOn(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/showHotels.fxml"));
        Parent showHotels = loader.load();
        Scene newScene = new Scene(showHotels);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("Our Hotels");
        primaryStage.setScene(newScene);

        ShowHotelsController showHotelsController = loader.getController();
        showHotelsController.initShowHotelsWindow();

        primaryStage.show();
    }

    @FXML
    void logout(ActionEvent event) throws Exception{
        Parent logOut = FXMLLoader.load(getClass().getResource("../view/start.fxml"));
        Scene newScene = new Scene(logOut);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("Reservation system");
        primaryStage.setScene(newScene);
        primaryStage.show();
    }
}
