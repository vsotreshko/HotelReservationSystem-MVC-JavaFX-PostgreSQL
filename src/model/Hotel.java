package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.sql.Date;
import java.time.LocalDate;

public class Hotel {
    private String country;
    private String city;
    private String hotelname;
    private String address;
    private LocalDate dateFrom;
    private LocalDate dateTill;
    private double rating;
    private int clss;

    public Hotel (String Country, String City, String Hotelname, String Address, double rating) {
        this.country = Country;
        this.city = City;
        this.hotelname = Hotelname;
        this.address = Address;
        this.rating = rating;
    }

    public Hotel (String country, String city, String hotelname, LocalDate from, LocalDate till, int clss){
        this.country = country;
        this.city = city;
        this.hotelname = hotelname;
        this.dateFrom = from;
        this.dateTill = till;
        this.clss = clss;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry(){
        return country;
    }

    public String getHotelname() {
        return hotelname;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTill() {
        return dateTill;
    }

    public int getClss() {
        return clss;
    }

    public ObservableValue<String> getAddressProperty() {
        return new SimpleStringProperty(address);
    }

    public ObservableValue<String> getCityProperty() {
        return new SimpleStringProperty(city);
    }

    public ObservableValue<String> getNameProperty() {
        return new SimpleStringProperty(hotelname);
    }

    public ObservableValue<String> getCountryProperty() {
        return new SimpleStringProperty(country);
    }

    public ObservableValue<Double> getRatingProperty() {
        return new SimpleDoubleProperty(rating).asObject();
    }
}

