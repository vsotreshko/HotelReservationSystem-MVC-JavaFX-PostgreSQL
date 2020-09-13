package model;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class PGDatabaseConnection {
    public static Connection connect() {
        Connection c = null;
        FileInputStream configFile;
        Properties prop = new Properties();
        try {
            configFile = new FileInputStream("config/database.conf");
            prop.load(configFile);

            // Load database properties
            String host = prop.getProperty("db.host");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            String database = prop.getProperty("db.database");
            String port = prop.getProperty("db.port");

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://"+ host +":"+ port +"/"+ database, username, password);
            c.setAutoCommit(false);

            configFile.close();
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            return null;
        }
    }
}
