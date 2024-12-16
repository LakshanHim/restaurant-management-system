package lk.ACPT.model;

import javafx.scene.control.Alert;
import lk.ACPT.db.DBConnection;
import lk.ACPT.dto.PasswordDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PasswordModel {

    public static boolean Login(PasswordDto passwordDto) {
        boolean login = false;
        try {
            Connection connection= DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from loginPassword where id=1");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String dbAdmin = resultSet.getString(2);
                String dbPassword = resultSet.getString(3);

                if(dbAdmin.equals(passwordDto.getUsername()) && dbPassword.equals(passwordDto.getPassword())) {
                    login = true;
                }
                else {
                    login = false;
                }
                return login;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return login;
    }

    public static boolean setPassword(String oldPassword, String newPassword) {
        boolean UpdatePassword = false;
        try {
            Connection connection= DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from loginPassword where id=1");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String dbPassword = resultSet.getString(3);

                if(dbPassword.equals(oldPassword)) {
                    PreparedStatement preparedStatement2 = connection.prepareStatement("update loginPassword set password=? where id=1");
                    preparedStatement2.setObject(1, newPassword);
                    int set = preparedStatement2.executeUpdate();
                    if(set > 0) {
                        UpdatePassword = true;
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Password Update Failed");
                        alert.setHeaderText("Password Not Updated");
                        alert.setContentText("There was an issue updating your password. Please try again.");
                        alert.showAndWait();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Password Update Failed");
                    alert.setHeaderText("Password Not Updated");
                    alert.setContentText("There was an issue updating your password. Please try again.");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return UpdatePassword;
    }

}
