package lk.ACPT.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
//import lk.ACPT.db.DBConnection;
import lk.ACPT.db.*;

import lk.ACPT.dto.MenuDto;
import lk.ACPT.tm.MenuTM;

import java.sql.*;
import java.util.ArrayList;

import static lk.ACPT.db.DBConnection.*;

public class MenuModel {

    public static ArrayList<MenuTM> LoadForm() {
        ArrayList<MenuTM> menu = new ArrayList<>();
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("select * from menu");
            //update quary eke anne resultSet ekek
            //return wenne boolean value ekek
            ResultSet resultSet = preparedStatement.executeQuery();

            //table object model danw

            while (resultSet.next()) {
                menu.add(new MenuTM(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4)));
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Something went wrong. Please try again.");
            errorAlert.showAndWait();
        }
        return menu;

    }

    public static MenuDto SearchForm(String itemName) {
        try (Connection connection = DBConnection.getDBConnection().getConnection()) {
            String sql = "SELECT * FROM menu WHERE itemName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, itemName);

            ResultSet resultSet = preparedStatement.executeQuery();
            MenuDto menu = new MenuDto();

            if (resultSet.next()) {
                menu.setName(resultSet.getString("itemName"));
                menu.setUnitPrice(resultSet.getDouble("unitPrice"));
                menu.setDescription(resultSet.getString("description"));
                menu.setImagePath(resultSet.getString("imagePath"));
            } else {
                return null;
            }
            return menu;
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Something went wrong while searching. Please try again.");
            errorAlert.showAndWait();
            return null;
        }
    }


    public static boolean UpdateForm(MenuDto menu) {
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();

            // Check if the connection is valid before proceeding
            if (connection == null || connection.isClosed()) {
                System.out.println("Connection is not valid or already closed.");
            } else {
                System.out.println("Connection is valid.");
            }

            // Print out the menu details for debugging
            System.out.println("Updating item: " + menu.getName());
            System.out.println("Price: " + menu.getUnitPrice());
            System.out.println("Description: " + menu.getDescription());

            // SQL query for updating the menu item
            String sql = "UPDATE menu SET unitPrice = ?, description = ? imagePath=? WHERE itemName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            // Set the values from the MenuDto object
            preparedStatement.setObject(1, menu.getUnitPrice());  // unitPrice
            preparedStatement.setObject(2, menu.getDescription()); // description
            preparedStatement.setObject(3, menu.getName());// itemName to identify the item
            preparedStatement.setObject(4, menu.getImagePath());

            // Execute the update and check if any rows were affected
            int rowsUpdated = preparedStatement.executeUpdate();

            // Return true if at least one row was updated
            return rowsUpdated > 0;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }







    //add save form in menusavecontroller
    public static boolean SaveForm(MenuDto menu) {
        // Load driver class to RAM
        try (Connection connection = DBConnection.getDBConnection().getConnection();){

            // Prepare the SQL query to insert the menu item
            String sql = "INSERT INTO menu (itemName, unitPrice, description, imagePath) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, menu.getName());
            preparedStatement.setDouble(2, menu.getUnitPrice());
            preparedStatement.setString(3, menu.getDescription());
            preparedStatement.setString(4, menu.getImagePath());  // Save the image path

            // Execute the query
            int i = preparedStatement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

