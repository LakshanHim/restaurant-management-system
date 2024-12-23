package lk.ACPT.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.Region;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ACPT.db.DBConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderController {

    @FXML
    private BorderPane rootOrder;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane cardGrid;

    @FXML
    public void initialize() {
        cardGrid.getChildren().clear();
        cardGrid.setHgap(10);
        cardGrid.setVgap(10);

        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            String query = "SELECT itemName, unitPrice, description, imagePath FROM menu";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            int column = 0;
            int row = 0;

            while (resultSet.next()) {
                String itemName = resultSet.getString("itemName");
                double unitPrice = resultSet.getDouble("unitPrice");
                String description = resultSet.getString("description");
                String imagePath = resultSet.getString("imagePath");

                VBox card = createCard(itemName, unitPrice, description, imagePath);
                cardGrid.add(card, column, row);

                column++;
                if (column == 5) {
                    column = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private VBox createCard(String itemName, double unitPrice, String description, String imagePath) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");
        card.setEffect(new DropShadow(10, Color.GRAY));
        card.setMaxWidth(50);
        card.setMaxWidth(350);

        // Item Name
        Text nameText = new Text("Name: " + itemName);
        nameText.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        // Description
        Text descriptionText = new Text("Description: " + description);
        descriptionText.setWrappingWidth(180);

        // Price
        Text priceText = new Text("Price: $" + unitPrice);
        priceText.setStyle("-fx-font-size: 14; -fx-fill: #0078D7;");

        // Add image view
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(new FileInputStream(imagePath));
            imageView.setImage(image);
            imageView.setFitWidth(100); // Adjust image width
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
            imageView.setStyle("-fx-alignment: center; ");
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        // Spinner and Button in the same line
        HBox spinnerButtonBox = new HBox(10);
        Spinner<Integer> quantitySpinner = new Spinner<>();
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));// Min 1, Max 100
        quantitySpinner.setStyle("-fx-max-width: 60px; -fx-max-height: 30px; ");
        quantitySpinner.setEditable(true);

        javafx.scene.control.Button addButton = new javafx.scene.control.Button("Add");
        addButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-font-size: 14;");
        addButton.setOnAction(event -> {
            int quantity = quantitySpinner.getValue();
            handleAddButton(itemName, unitPrice, quantity);
        });

        spinnerButtonBox.getChildren().addAll(quantitySpinner, addButton);

        // Add all components to the card
        card.getChildren().addAll(imageView, nameText, descriptionText, priceText, spinnerButtonBox);

        return card;
    }

    private void handleAddButton(String itemName, double unitPrice, int quantity) {
        System.out.println("Added to cart: " + itemName + " x" + quantity + " - $" + (unitPrice * quantity));
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootOrder.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.setFullScreen(false);
    }

    @FXML
    void btnClose(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
