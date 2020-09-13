package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.util.List;


public class MakeReservationController {

        private Hotel hotel;

        @FXML
        private RadioButton economRadio;

        @FXML
        private RadioButton standartRadio;

        @FXML
        private RadioButton luxRadio;

        @FXML
        private ComboBox<String> countryBox;

        @FXML
        private ComboBox<String> cityBox;

        @FXML
        private ComboBox<String> hotelBox;

        @FXML
        private Label errorLabel;

        @FXML
        private DatePicker DateFrom;

        @FXML
        private DatePicker DateTill;

        @FXML
        void cancelMakingReservation(ActionEvent event) throws Exception{
            Parent cancelReservation = FXMLLoader.load(getClass().getResource("../view/mainPage.fxml"));
            Scene newScene = new Scene(cancelReservation);

            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
            primaryStage.setTitle("Main Page");
            primaryStage.setScene(newScene);
            primaryStage.show();
        }

        @FXML
        void makeReservation (ActionEvent event) throws Exception {
            /* Tests if fields are fullfilled */
            if( cityBox.getValue() == null || countryBox.getValue() == null || hotelBox.getValue() == null ||
                    DateFrom.getValue() == null || DateTill.getValue() == null || ! economRadio.isSelected() &&
                    ! luxRadio.isSelected() && ! standartRadio.isSelected()
            ){
                errorLabel.setText("Everything must be entered.");
                return;
            }

            int clss = 0;
            if (economRadio.isSelected()) clss = 1;
            if (standartRadio.isSelected()) clss = 2;
            if (luxRadio.isSelected()) clss = 3;

            this.hotel = new Hotel(countryBox.getValue(), cityBox.getValue(), hotelBox.getValue(), DateFrom.getValue(), DateTill.getValue(), clss);
            DataBase.makeReservation(this.hotel);

            Parent endOfReservation = FXMLLoader.load(getClass().getResource("../view/endOfReservation.fxml"));
            Scene newScene = new Scene(endOfReservation);

            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the primary stage from Main class
            primaryStage.setScene(newScene);
            primaryStage.show();

        }

        @FXML
        void countrySelected(ActionEvent event){
            /*Extract cities, that are part of a selected country, from the database*/
            String country = countryBox.getValue();
            List citiesArr = DataBase.selectColumn("SELECT cities.name FROM cities\n" +
                                            "JOIN countries c on cities.countryid = c.countryid AND c.name = '"+ country +"'\n" +
                                            "ORDER BY name;", "name", String.class);
            cityBox.getItems().clear();
            cityBox.getItems().addAll(citiesArr);

            /*Extract hotels, that are in a selected country, from the database*/
            List hotelsArr = DataBase.selectColumn("SELECT hotels.name FROM hotels\n" +
                                            "JOIN countries c on hotels.countryid = c.countryid AND c.name = '"+ country +"'\n" +
                                            "ORDER BY name;", "name", String.class);
            hotelBox.getItems().clear();
            hotelBox.getItems().addAll(hotelsArr);
        }

        @FXML
        void citySelected(ActionEvent event){
            /*Extract hotels, that are part of a selected city, from the database*/
            String city = cityBox.getValue();

            List hotelsArr = DataBase.selectColumn("SELECT hotels.name FROM hotels\n" +
                                            "JOIN cities c ON c.cityid = hotels.cityid and c.name = '" + city + "'\n" +
                                            "ORDER BY name;", "name", String.class);
            hotelBox.getItems().clear();
            hotelBox.getItems().addAll(hotelsArr);
        }

    public void initMakeReservation() {
        countryBox.setPromptText("Select country");
        List countriesArr = DataBase.selectColumn("SELECT name FROM countries ORDER BY name;", "name", String.class);
        countryBox.getItems().addAll(countriesArr);

        cityBox.setPromptText("Select city");
        List citiesArr = DataBase.selectColumn("SELECT name FROM cities ORDER BY name;", "name", String.class);
        cityBox.getItems().addAll(citiesArr);

        hotelBox.setPromptText("Select hotel");
        List hotelsArr = DataBase.selectColumn("SELECT name FROM hotels ORDER BY name;", "name", String.class);
        hotelBox.getItems().addAll(hotelsArr);
    }

    public void initFromShowHotels(Hotel hotel){
            countryBox.getSelectionModel().select(hotel.getCountry());
            cityBox.getSelectionModel().select(hotel.getCity());
            hotelBox.getSelectionModel().select(hotel.getHotelname());

            countryBox.setDisable(true);
            cityBox.setDisable(true);
            hotelBox.setDisable(true);
    }
}