package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DataBase;
import model.Reservation;
import java.util.List;

public class CancelReservationController {

    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    private  TableColumn<Reservation, Integer> resIdColumn;

    @FXML
    private  TableColumn<Reservation, String> dateFromColumn;

    @FXML
    private TableColumn<Reservation, String> dateTillColumn;

    @FXML
    private TableColumn<Reservation, String> hotelColumn;

    @FXML
    private TableColumn<Reservation, Integer> roomColumn;

    @FXML
    void cancelCancellingReservation(ActionEvent event) throws Exception {
        Parent mainPage = FXMLLoader.load(getClass().getResource("../view/mainPage.fxml"));
        Scene newScene = new Scene(mainPage);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(newScene);

        primaryStage.show();
    }

    public void initCancelReservationWindow () {

        List<Reservation> reservations = DataBase.selectReservationList();
        ObservableList<Reservation> list = FXCollections.observableArrayList(reservations);

        resIdColumn.setCellValueFactory(id -> id.getValue().getResIdProperty().asObject());
        dateFromColumn.setCellValueFactory(date -> date.getValue().getdateFromProperty());
        dateTillColumn.setCellValueFactory(date -> date.getValue().getdateTillProperty());
        hotelColumn.setCellValueFactory(hotel -> hotel.getValue().getHotelNameProperty());
        roomColumn.setCellValueFactory(num -> num.getValue().getNumberProperty().asObject());

        reservationsTable.setItems(list);
    }

    @FXML
    public void cancelReservation(ActionEvent event){
        Reservation reservation = reservationsTable.getSelectionModel().getSelectedItem();
        DataBase.deleteReservation(reservation.getResId());
        reservationsTable.getItems().remove(reservation);
        reservationsTable.refresh();
    }
}
