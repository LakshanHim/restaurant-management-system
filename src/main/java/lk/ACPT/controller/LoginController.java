package lk.ACPT.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ACPT.dto.PasswordDto;
import lk.ACPT.model.PasswordModel;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane rootLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnChangePassword(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PassUpdate-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        boolean isLogin = PasswordModel.Login(new PasswordDto(userName,password));
        if (isLogin) {
            Stage stage = (Stage) this.rootLogin.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Login Failed");
            alert.setContentText("Incorrect username or password.");
            alert.showAndWait();
        }

    }

}
