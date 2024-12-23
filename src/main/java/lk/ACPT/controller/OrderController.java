package lk.ACPT.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ACPT.db.DBConnection;

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
    private VBox cardContainer;

    @FXML
    public void initialize() {
        loadOrders();
    }

    private void loadOrders() {
        cardContainer.getChildren().clear(); // Clear any existing cards

        try (Connection connection = DBConnection.getDBConnection().getConnection()) {
            String query = "SELECT id, name, description, price FROM ordersx"; // Modify table name and columns as per your DB
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");

                VBox card = createCard(id, name, description, price);
                cardContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createCard(int id, String name, String description, double price) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");
        card.setEffect(new DropShadow(10, Color.GRAY));
        card.setMinHeight(Region.USE_PREF_SIZE);

        Text nameText = new Text("Name: " + name);
        nameText.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Text descriptionText = new Text("Description: " + description);
        descriptionText.setWrappingWidth(200);

        Text priceText = new Text("Price: $" + price);
        priceText.setStyle("-fx-font-size: 14; -fx-fill: #0078D7;");

        card.getChildren().addAll(nameText, descriptionText, priceText);
        return card; // Return the VBox directly
    }
    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootOrder.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Set the scene and enable fullscreen mode
        stage.setScene(scene);
        stage.setFullScreen(false);

    }

    @FXML
    void btnClose(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

}
