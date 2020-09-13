package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class Reservation {
    private int resId;
    private Date dateFrom;
    private Date dateTill;
    private String hotelName;
    private int number;

    public Reservation(int resId, Date dateFrom, Date dateTill, String hotelName, int roomNumber) {
        this.resId = resId;
        this.dateFrom = dateFrom;
        this.dateTill = dateTill;
        this.hotelName = hotelName;
        this.number = roomNumber;
    }

    public int getResId() {
        return resId;
    }

    public IntegerProperty getResIdProperty() {
        return new SimpleIntegerProperty(resId);
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public StringProperty getdateFromProperty() {
        return new SimpleStringProperty(dateFrom.toString());
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTill() {
        return dateTill;
    }

    public StringProperty getdateTillProperty() {
        return new SimpleStringProperty(dateTill.toString());
    }

    public String getHotelName() {
        return hotelName;
    }

    public StringProperty getHotelNameProperty() {
        return new SimpleStringProperty(hotelName);
    }

    public IntegerProperty getNumberProperty() {
        return new SimpleIntegerProperty(number);
    }
}
