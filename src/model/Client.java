package model;

import java.sql.*;
import java.time.LocalDate;

public class Client {

    private int id ;
    private String first_name;
    private String last_name;
    private LocalDate birth_date;
    private String password;
    private String login;

    public Client (int id, String firstName, String lastName, LocalDate birthDate, String login, String password) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.birth_date = birthDate;
        this.password = password;
        this.login = login;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
