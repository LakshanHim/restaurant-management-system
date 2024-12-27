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
            ResultSet resultSet = preparedStatement.executeQuery();

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

    public static void updateMenu(MenuTM menu) {
        try {
            Connection connection = getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE menu SET itemName = ?, unitPrice = ?, description = ? WHERE id = ?");
            preparedStatement.setString(1, menu.getItemName());
            preparedStatement.setDouble(2, menu.getUnitPrice());
            preparedStatement.setString(3, menu.getDescription());
            preparedStatement.setInt(4, menu.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Update Failed");
                errorAlert.setContentText("No rows were updated. Please try again.");
                errorAlert.showAndWait();
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Failed to update the database. Please try again.");
            errorAlert.showAndWait();
        }
    }


    public static MenuDto SearchForm(String itemName) {
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
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

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE menu SET unitPrice=?, description=?, imagePath=? WHERE itemName=?");

            // Set the values from the MenuDto object
            preparedStatement.setObject(1, menu.getUnitPrice());  // unitPrice
            preparedStatement.setObject(2, menu.getDescription()); // description
            preparedStatement.setObject(3, menu.getImagePath());   // imagePath
            preparedStatement.setObject(4, menu.getName());        // itemName (used in WHERE clause)

            // Execute the update and check if any rows were affected
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }







    //add save form in menusavecontroller
    public static boolean SaveForm(MenuDto menu) {
        // Load driver class to RAM
        try{
            Connection connection = DBConnection.getDBConnection().getConnection();

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

