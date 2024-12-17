package lk.ACPT.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ACPT.dto.MenuDto;
import lk.ACPT.model.MenuModel;

import java.io.IOException;

public class MenuUpdateController {

    @FXML
    private AnchorPane rootUpdate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootUpdate.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

    }

    @FXML
    void btnSave(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        double unitPrice = Double.parseDouble(txtPrice.getText());

        boolean b = MenuModel.UpdateForm(new MenuDto(id,name, unitPrice));
        if (b) {
            Alert updateAlert = new Alert(Alert.AlertType.INFORMATION);
            updateAlert.setTitle("Notification");
            updateAlert.setHeaderText("Update Successful");
            updateAlert.setContentText("The data has been successfully updated!");
            updateAlert.showAndWait();
        }
        else {
            Alert noUpdateAlert = new Alert(Alert.AlertType.WARNING);
            noUpdateAlert.setTitle("Notification");
            noUpdateAlert.setHeaderText("No Update");
            noUpdateAlert.setContentText("No changes detected. Update not performed.");
            noUpdateAlert.showAndWait();
        }

    }

    @FXML
    void btnSearch(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());

        MenuDto menu =  MenuModel.SearchForm(id);
        txtName.setText(menu.getName());
        txtPrice.setText(String.valueOf(menu.getUnitPrice()));



    }

}