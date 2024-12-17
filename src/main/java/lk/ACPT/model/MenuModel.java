package lk.ACPT.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import lk.ACPT.db.DBConnection;
import lk.ACPT.dto.MenuDto;
import lk.ACPT.tm.MenuTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MenuModel {
    public static ArrayList<MenuTM> LoadForm(){
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
                        resultSet.getDouble(3)));
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Something went wrong. Please try again.");
            errorAlert.showAndWait();
        }
        return menu;

    }

    public static MenuDto SearchForm(int id) {
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("select * from menu where id=?");
            preparedStatement.setObject(1, id);

            //update quary eke anne resultSet ekek
            //return wenne boolean value ekek
            ResultSet resultSet = preparedStatement.executeQuery();

            MenuDto menu = new MenuDto();

            if (resultSet.next()) {
                menu.setName(resultSet.getString(2));
                menu.setUnitPrice(resultSet.getDouble(3));
            }
            return menu;
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Something went wrong. Please try again.");
            errorAlert.showAndWait();
        }
        return null;
    }

    public static boolean UpdateForm(MenuDto menu) {
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();

            //dynamic quary is
            // write sql quary
            PreparedStatement preparedStatement = connection.prepareStatement("update menu set itemName=?, unitPrice=? where id=? ");
            preparedStatement.setObject(1,menu.getName());
            preparedStatement.setObject(2,menu.getUnitPrice());
            preparedStatement.setObject(3,menu.getId());

            //execute Quary
            int i = preparedStatement.executeUpdate();

            if(i>0){
                return true;
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Something went wrong. Please try again.");
            errorAlert.showAndWait();
        }
        return false;
    }
}
