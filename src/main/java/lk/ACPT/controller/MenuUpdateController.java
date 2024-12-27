package lk.ACPT.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ACPT.dto.MenuDto;
import lk.ACPT.model.MenuModel;


import java.io.File;
import java.io.IOException;

public class MenuUpdateController {

    @FXML
    private AnchorPane rootUpdate;

    @FXML
    private ImageView imgView;

    @FXML
    private TextField txtId;

    @FXML
    private FileChooser fileChooser = new FileChooser();

    @FXML
    private File selectedImageFile;

    @FXML
    private TextField txtItemName, txtName, txtPrice, txtDesc;

    @FXML
    private Button txtimage;


    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootUpdate.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

    }

    @FXML
    void btnSearch(ActionEvent event) {
        String itemName = txtItemName.getText(); // Get the name of the item to search

        // Search by item name
        MenuDto menu = MenuModel.SearchForm(itemName);  // Pass itemName to the SearchForm method
        if (menu != null) {
            txtPrice.setText(String.valueOf(menu.getUnitPrice()));  // Display the current price in the update field
            txtDesc.setText(menu.getDescription());
            String imagePath1 = menu.getImagePath();
            selectedImageFile = new File(imagePath1);



            String imagePath = menu.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image("file:" + imagePath); // Assuming the image is stored locally
                imgView.setImage(image);

            }
        } else {
            Alert noItemFoundAlert = new Alert(Alert.AlertType.WARNING);
            noItemFoundAlert.setTitle("Item Not Found");
            noItemFoundAlert.setHeaderText("No Item Found");
            noItemFoundAlert.setContentText("No item was found with the name: " + itemName);
            noItemFoundAlert.showAndWait();
        }
    }

    @FXML
    void btnUploadImage(ActionEvent event) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            System.out.println("Image selected: " + selectedImageFile.getName());
        }
    }

    @FXML
    void btnSave(ActionEvent event) {
        String itemName = txtItemName.getText();  // Item name to search and update
        double updatedPrice = Double.parseDouble(txtPrice.getText());  // Updated price
        String updatedDesc = txtDesc.getText();   // Updated description
        String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

        // Update the menu item with new details
        boolean updated = MenuModel.UpdateForm(new MenuDto(itemName,updatedPrice,updatedDesc,imagePath));  // Pass updated details to the model

        if (updated) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Update Successful");
            successAlert.setHeaderText("Item Updated");
            successAlert.setContentText("The item has been successfully updated.");
            successAlert.showAndWait();
        } else {
            Alert failureAlert = new Alert(Alert.AlertType.WARNING);
            failureAlert.setTitle("Update Failed");
            failureAlert.setHeaderText("No Changes Made");
            failureAlert.setContentText("The item could not be updated. Please try again.");
            failureAlert.showAndWait();
        }
    }


}