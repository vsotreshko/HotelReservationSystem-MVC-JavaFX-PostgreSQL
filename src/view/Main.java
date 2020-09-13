package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DataBase;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
            primaryStage.setTitle("Reservation system");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            DataBase.closeConnection();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
