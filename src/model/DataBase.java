package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    private static Connection connection = null;
    private static Client loggedInClient = null;
    private static DataBase ourInstance = new DataBase();

    public static DataBase getInstance() {
        return ourInstance;
    }

    private DataBase() {
        connection = PGDatabaseConnection.connect();
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }

    public static int addNewClient(Client client){
        int id = 0;
        PreparedStatement prep;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "INSERT INTO clients (clientid, first_name, last_name, birth_date, login, password) "
                    + "VALUES (default, " +
                    "'" + client.getFirst_name() + "', " +
                    "'" + client.getLast_name() + "', " +
                    "'" + client.getBirth_date() + "', " +
                    "'" + client.getLogin() + "', " +
                    "'" + client.getPassword() + "');";
            stmt.executeUpdate(sql);
            connection.commit();

            sql = "SELECT clientid FROM clients WHERE login = ?";
            prep = connection.prepareStatement(sql);
            prep.setString(1, client.getLogin());

            ResultSet rs = prep.executeQuery();
            rs.next();
            id = rs.getInt("clientid");

            stmt.close();
            prep.close();
        }
        catch(SQLException e){
            System.err.println("Error: " + e.getErrorCode());
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e1) {
                    System.err.println(e1.getErrorCode());
                }
            }
        }

        return id;
    }

    public static void makeReservation(Hotel hotel) {
        PreparedStatement stmt = null;

        try {
            List<Integer> hotelID = DataBase.selectColumn("SELECT hotelid FROM hotels WHERE name = '" + hotel.getHotelname() + "';", "hotelid", Integer.class);
            int hID = hotelID.get(0);

            List<Integer> freeRoom = DataBase.selectColumn("" +
                    "SELECT r.roomid FROM rooms r " +
                    "WHERE r.hotelid = " + hID + " and r.class = " + hotel.getClss() + " and r.roomid NOT IN (" +
                    "SELECT res.roomid from reservations res WHERE (res.fromdate, res.tilldate) " +
                    "OVERLAPS (DATE '" + hotel.getDateFrom().toString() + "', DATE '" + hotel.getDateTill().toString() + "')) LIMIT 1;", "roomid", Integer.class);

            if (freeRoom.isEmpty()) {
                System.out.println("DEBUG: There is no free rooms for this dates.");
                return;
            }

            String sql = "INSERT INTO reservations VALUES (DEFAULT, ?, ?, ?, current_date, LOCALTIMESTAMP, ?, ?);";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, hID);
            stmt.setInt(2, freeRoom.get(0));
            stmt.setInt(3, loggedInClient.getID());
            stmt.setDate(4, Date.valueOf(hotel.getDateFrom()));
            stmt.setDate(5, Date.valueOf(hotel.getDateTill()));

            stmt.executeUpdate();
            connection.commit();
            System.out.println("DEBUG: Reservation has been made.");
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(1);
        }
        finally {
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                }
            }
        }
    }

    public static <T> List<T> selectColumn(String sql, String column, Class<T> tClass) {
        try {
            Statement stmt;
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(sql);

            List<T> retval = new LinkedList<>();
            while (result.next()) {
                retval.add(result.getObject(column, tClass));
            }

            stmt.close();
            return retval;
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(1);
        }

        return null;
    }

    public static List<Reservation> selectReservationList() {

        List<Reservation> reservations = new LinkedList<>();
        PreparedStatement stmt = null;

        try{
            String sql = "SELECT resvid, h.name, res.fromdate, res.tilldate, r.number  from reservations res\n" +
                    "JOIN rooms r on res.roomid = r.roomid\n" +
                    "JOIN hotels h on r.hotelid = h.hotelid\n" +
                    "WHERE clientid = ? and fromdate > current_date;";

            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, loggedInClient.getID());
            ResultSet rs =  stmt.executeQuery();

            while(rs.next()){
                reservations.add(new Reservation(
                        rs.getInt(1),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getString(2),
                        rs.getInt(5)
                ));
            }

            stmt.close();
        } catch (Exception e){
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("ERROR: " + e.getMessage());
                }
            }
        }

        return  reservations;
    }

    public static List<Hotel> selectHotels(String likeRequest, int rating) {

        List<Hotel> hotels = new LinkedList<>();
        PreparedStatement stmt = null;

        try{
            String sql;
            ResultSet rs;
            if (likeRequest == null) {
                sql = "SELECT cou.name, c.name, h.name, h.address, h.rating FROM hotels h " +
                        "JOIN cities c ON c.cityid = h.cityid " +
                        "JOIN countries cou ON cou.countryid = h.countryid " +
                        "where h.rating > ?;";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, rating);
                rs =  stmt.executeQuery();
            } else {
                    sql = "SELECT cou.name, c.name, h.name, h.address, h.rating FROM hotels h\n" +
                            "    JOIN cities c ON c.cityid = h.cityid\n" +
                            "    JOIN countries cou ON cou.countryid = h.countryid\n" +
                            "WHERE (cou.name like ? or c.name like ? or h.name like ? or h.address like ?) and h.rating > ?;";
                likeRequest += '%';
                stmt = connection.prepareStatement(sql);
                for (int i = 1; i <=4; ++i)
                    stmt.setString(i, likeRequest);
                stmt.setInt(5, rating);
                rs =  stmt.executeQuery();
            }

            while(rs.next()){
                hotels.add(new Hotel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5)
                ));
            }

            stmt.close();
        } catch (Exception e){
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("ERROR: " + e.getMessage());
                }
            }
        }

        return  hotels;
    }

    public static boolean signin(String login, String password){
        String sql = "SELECT * FROM clients WHERE login = '" +login+ "'" ;

        List client = selectColumn(sql, "login", String.class);
        if (client.isEmpty())
            return false;

        List pass = selectColumn(sql, "password", String.class);
        if (password.compareTo( (String) pass.get(0)) != 0)
            return false;

        return true;
    }

    public static void setLoginedUser(String login) {
        try {
            String sql = "SELECT * FROM clients WHERE login = ?";
            PreparedStatement stmt;
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, login);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                loggedInClient = new Client(
                        rs.getInt("clientid"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        null,
                        rs.getString("login"),
                        rs.getString("password")
                );
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException e) {
            System.err.println("Error is setLoginedUser");
        }
    }

    public static void deleteReservation(int id){
        PreparedStatement stmt = null;

        try{
            String sql = "DELETE FROM reservations WHERE resvid = ?";

            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            connection.commit();
            stmt.close();
        } catch (Exception e){
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("ERROR: " + e.getMessage());
                }
            }
        }

    }
}
