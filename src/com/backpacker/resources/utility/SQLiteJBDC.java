package com.backpacker.resources.utility;

import java.sql.*;

public class SQLiteJBDC {

    public Connection SQLConnect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
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
                " oz REAL )";
        try (Connection connection = this.SQLConnect();
                Statement st = connection.createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String table, String item, int quantity, float oz) {
        String sql = "INSERT INTO " + table +  " (ItemName, Quantity, oz) VALUES(?,?,?)";

        try (Connection connection = this.SQLConnect();
                PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, item);
            pstmt.setInt(2, quantity);
            pstmt.setFloat(3, oz);
            pstmt.executeUpdate();
            System.out.println("Item added");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet selectAll(String table) throws SQLException {
        String sql = "SELECT ItemName, Quantity, oz FROM " + table;
        Connection connection = this.SQLConnect();
        PreparedStatement pstmt = connection.prepareStatement(sql);

        return pstmt.executeQuery();
    }
}
