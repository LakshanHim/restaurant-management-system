package lk.ACPT.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import lk.ACPT.dto.MenuDto;
import lk.ACPT.model.MenuModel;

import java.io.File;
import java.io.IOException;

public class MenuSaveControlller {

    @FXML
    private AnchorPane rootAddmenu;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUnitaPrice;

    @FXML
    private TextArea txtDescription;

    @FXML
    private FileChooser fileChooser = new FileChooser();

    @FXML
    private File selectedImageFile;

    @FXML
    void btnSave(ActionEvent event) {
        String name = txtName.getText();
        double unitPrice = Double.parseDouble(txtUnitaPrice.getText());
        String description = txtDescription.getText();
        String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

        boolean b = MenuModel.SaveForm(new MenuDto(name, unitPrice, description, imagePath));
        if (b) {
            Alert addAlert = new Alert(Alert.AlertType.INFORMATION);
            addAlert.setTitle("Item Added");
            addAlert.setHeaderText("Success");
            addAlert.setContentText("A new item has been added to the menu.");
            addAlert.showAndWait();
        } else {
            Alert noAddAlert = new Alert(Alert.AlertType.WARNING);
            noAddAlert.setTitle("No Action Taken");
            noAddAlert.setHeaderText("No Changes Made");
            noAddAlert.setContentText("No item was added to the menu.");
            noAddAlert.showAndWait();
        }
    }

    @FXML
    void btnUploadImage(ActionEvent event) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);
        btnUploadImage.setStyle("-fx-background-color: #e74c3c; -fx-font-weight: bold; -fx-text-fill: white;");
    }

    @FXML
    private Button btnUploadImage;

    @FXML
    void btnback(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootAddmenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

    }

}