package com.backpacker.resources.utility;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.*;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class tools {
    public static int tabCount = 0;
    public static ArrayList<String> categories = new ArrayList<>();
    public static ArrayList<TableView<ObservableList<String>>> tableViewNames = new ArrayList<>();

    //dynamically builds tables from database values
    public static void buildData(TableView<ObservableList<String>> a, String table){
        SQLiteJBDC connect = new SQLiteJBDC();
        ObservableList<ObservableList<String>> data;
        data = FXCollections.observableArrayList();

        try{
            ResultSet rs = connect.selectAll(table);
            //generates dynamic table column names in passed table based on database column names
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnLabel(i + 1));
                col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                a.getColumns().add(col);
                if (col.getText().equals("ID")){
                    col.setVisible(false);
                }
                //System.out.println(col.getText());
            }
            //adds rows to table from database
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //System.out.println(rs.getString(i));
                    if (i==4 || i==5 || i==6){
                        row.add(formatDecimal(rs.getString(i)));
                    }
                    else {
                        row.add(rs.getString(i));
                    }
                }
                data.add(row);
            }
            a.setItems(data);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    //formatting weight functions
    public static double ozToGrams(float oz) {
        return oz * 28.35;
    }

    public static double ozToPounds(float oz) {
        return oz * 0.0625;
    }

    public static String formatDecimal(String x) {
        DecimalFormat dFormat = new DecimalFormat();
        dFormat.setMaximumFractionDigits(2);
        float i = Float.parseFloat(x);
        return dFormat.format(i);
    }

    //category arraylist getters and setter
    public static ArrayList<String> getCategories() {
        return categories;
    }

    public static void setCategories(String s) {
        categories.add(s);
        saveCategory(categories);
    }

    public static void removeCategory(String s) {
        categories.remove(s);
        saveCategory(categories);
    }

    //function to create tab and tableview within it
    public static void createTab(TabPane tabPane, String table){
        SQLiteJBDC db = new SQLiteJBDC();
        tabPane.getTabs().add(new Tab(table));

        TableView<ObservableList<String>> tableView = new TableView<ObservableList<String>>();

        db.createTable(table);
        tabCount ++;
        for (int i=0; i<tabCount; i++){
            if (tabPane.getTabs().get(i).getText().equalsIgnoreCase(table)){
                System.out.println("Found " + tabPane.getTabs().get(i).getText());
                buildData(tableView, table);
                tabPane.getTabs().get(i).setContent(tableView);
                tableView.setId(table);
                tableViewNames.add(tableView);
            }
        }
    }

    //creates + tab and sets it to right most position
    public static void addCatTab(TabPane tabPane) {
        try {
            System.out.println("Last tab is: " + tabPane.getTabs().get(tabCount).getText());
            if(tabPane.getTabs().get(tabCount).getText().equalsIgnoreCase("+")) {
                System.out.println("Trying to remove: " + tabPane.getTabs().get(tabCount).getText());
                tabPane.getTabs().remove(tabCount);
            }
            else if (tabPane.getTabs().get(tabCount-1).getText().equalsIgnoreCase("+")) {
                System.out.println("Trying to remove: " + tabPane.getTabs().get(tabCount-1).getText());
                tabPane.getTabs().remove(tabCount-1);
            }
        }
        catch(Exception e){
            System.out.println("No tab available: " + e.getMessage());
        }
        tabPane.getTabs().add(new Tab("+"));
    }

    //refreshes data on all tabs
    public static void refreshTabs(TabPane tabPane) {
        tabPane.getTabs().clear();
        for(String x : categories) {
            tools.createTab(tabPane, x);
        }
        tools.addCatTab(tabPane);
    }

    //saves categories to file
    public static void saveCategory(ArrayList<String> x) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\com\\backpacker\\resources\\data\\categories.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(x);

            objOut.close();
            fileOut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    //reads categories from file
    public static void readCategory() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("src\\com\\backpacker\\resources\\data\\categories.ser");
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        categories = (ArrayList<String>) objIn.readObject();
        objIn.close();
        fileIn.close();
    }
}
