package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class ShowHotelsController {
    private int lastRating;
    private String lastRequest;

    @FXML
    public TextField searchField;

    @FXML
    public ComboBox<String> ratingBox;

    @FXML
    private TableView<Hotel> hotelsTable;

    @FXML
    private TableColumn<Hotel, String> hotelName;

    @FXML
    private TableColumn<Hotel, String> country;

    @FXML
    private TableColumn<Hotel, String> city;

    @FXML
    private TableColumn<Hotel, String> address;

    @FXML
    private TableColumn<Hotel, Double> rating;


    @FXML
    void backToMainPage(ActionEvent event) throws Exception{
        Parent mainPage = FXMLLoader.load(getClass().getResource("../view/mainPage.fxml"));
        Scene newScene = new Scene(mainPage);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(newScene);

        primaryStage.show();
    }

    public void initShowHotelsWindow () {

        List<Hotel> hotels = DataBase.selectHotels(null, 0);
        lastRating = 0;
        lastRequest = null;
        ObservableList<Hotel> list = FXCollections.observableArrayList(hotels);

        hotelName.setCellValueFactory(name -> name.getValue().getNameProperty() );
        country.setCellValueFactory(country -> country.getValue().getCountryProperty());
        city.setCellValueFactory(city -> city.getValue().getCityProperty());
        address.setCellValueFactory(address -> address.getValue().getAddressProperty());
        rating.setCellValueFactory(rating -> rating.getValue().getRatingProperty());

        hotelsTable.setItems(list);

        List<String> boxList = new LinkedList<>();
        boxList.add("4+");
        boxList.add("3+");
        boxList.add("2+");
        boxList.add("All");
        ratingBox.getItems().addAll(boxList);
    }

    @FXML
    void makeReservationAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/makeReservation.fxml"));
        Parent makeReservation = loader.load();
        Scene newScene = new Scene(makeReservation);

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
        primaryStage.setTitle("Make reservation");
        primaryStage.setScene(newScene);

        MakeReservationController makeResController = loader.getController();
        Hotel hotel = hotelsTable.getSelectionModel().getSelectedItem();
        makeResController.initFromShowHotels(hotel);

        primaryStage.show();
    }

    @FXML
    void searchAction(ActionEvent event){
        String request = searchField.getText();
        lastRequest = request;
        List<Hotel> hotels = DataBase.selectHotels(request, lastRating);
        ObservableList<Hotel> list = FXCollections.observableArrayList(hotels);

        hotelsTable.getItems().clear();
        hotelsTable.setItems(list);
        hotelsTable.refresh();
    }

    @FXML
    void ratingChanged(ActionEvent event){
        String selected = ratingBox.getSelectionModel().getSelectedItem();

        if (selected.compareTo("All") == 0)
            lastRating = 0;
        else
            lastRating = Integer.parseInt(selected.substring(0,1));

        List<Hotel> hotels = DataBase.selectHotels(lastRequest, lastRating);
        ObservableList<Hotel> list = FXCollections.observableArrayList(hotels);

        hotelsTable.getItems().clear();
        hotelsTable.setItems(list);
        hotelsTable.refresh();
    }
}
