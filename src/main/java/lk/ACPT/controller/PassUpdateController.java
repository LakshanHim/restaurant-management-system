package lk.ACPT.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ACPT.model.PasswordModel;

import java.io.IOException;

public class PassUpdateController {

    @FXML
    private AnchorPane rootpassUpdate;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private TextField txtOldPassword;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootpassUpdate.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    void btnReset(ActionEvent event) throws IOException {
        String setPassword = txtNewPassword.getText();
        String oldPassword = txtOldPassword.getText();

        boolean UpdatePassword =  PasswordModel.setPassword(oldPassword,setPassword);
        if(UpdatePassword){
            Stage stage = (Stage) this.rootpassUpdate.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        }

    }

}
