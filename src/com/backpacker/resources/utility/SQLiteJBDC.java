package com.backpacker.resources.utility;

import java.sql.*;

public class SQLiteJBDC {

    public Connection SQLConnect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src\\com\\backpacker\\resources\\data\\test.db");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void createTable(String table) {
        String sql = "CREATE TABLE IF NOT EXISTS " + table +
                "(ID INTEGER PRIMARY KEY," +
                " ItemName TEXT NOT NULL," +
                " Quantity INTEGER NOT NULL," +
                " oz REAL," +
                " g REAL," +
                " lbs REAL )";
        try (Connection connection = this.SQLConnect();
                Statement st = connection.createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertItem(String table, String item, int quantity, float oz, float g, float lbs) {
        String sql = "INSERT INTO " + table +  " (ItemName, Quantity, oz, g, lbs) VALUES(?,?,?,?,?)";

        try (Connection connection = this.SQLConnect();
                PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, item);
            pstmt.setInt(2, quantity);
            pstmt.setFloat(3, oz);
            pstmt.setFloat(4, g);
            pstmt.setFloat(5, lbs);
            pstmt.executeUpdate();
            System.out.println("Item added");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteItem(int id, String table) {
        String sql = "DELETE FROM " + table + " WHERE ID = ?";

        try (Connection connection = this.SQLConnect();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet selectAll(String table) throws SQLException {
        String sql = "SELECT ID, ItemName, Quantity, oz, g, lbs FROM " + table;
        Connection connection = this.SQLConnect();
        PreparedStatement pstmt = connection.prepareStatement(sql);

        return pstmt.executeQuery();
    }

    public void deleteTable(String table) {
        String sql = "DROP TABLE IF EXISTS " + table;
        try (Connection connection = this.SQLConnect();
                PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
