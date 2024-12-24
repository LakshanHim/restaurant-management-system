package lk.ACPT.controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ACPT.db.DBConnection;
import lk.ACPT.dto.OrderDetailDto;
import lk.ACPT.dto.OrderDto;
import lk.ACPT.model.OrderModel;
import lk.ACPT.tm.OrderTM;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class OrderController {

    @FXML
    private BorderPane rootOrder;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane cardGrid;

    @FXML
    private TableView<OrderTM> orderTable;

    @FXML
    private TableColumn<OrderTM, String> column1;

    @FXML
    private TableColumn<OrderTM, Integer> column2;

    @FXML
    private TableColumn<OrderTM, Double> column3;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblSubTotal;

    @FXML
    private TextField txtAmount;

    double balance=0;

    @FXML
    void amount(ActionEvent event) {
        double amount = Double.parseDouble(txtAmount.getText());
        balance = amount-subTotal;
        lblBalance.setText("$"+balance);
    }

    @FXML
    void btnPay(ActionEvent event) {
        if (balance > 0) {
            // Clear order details
            orderTable.getItems().clear();
            lblSubTotal.setText("");
            lblBalance.setText("");
            txtAmount.clear();

            // Show success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Successful");
            alert.setHeaderText("Payment Completed");
            alert.setContentText("The bill has been paid successfully.");

            // Set the owner of the alert to the current stage to ensure it pops up inside the stage
            Stage stage = (Stage) lblSubTotal.getScene().getWindow();
            alert.initOwner(stage);  // Set the alert's owner to the current stage
            alert.showAndWait();

        } else {
            // Show error alert if balance is insufficient
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Payment Error");
            alert.setHeaderText("Insufficient Amount");
            alert.setContentText("Your amount is less than the total bill. Please check and try again.");

            // Set the owner of the error alert to the current stage
            Stage stage = (Stage) lblSubTotal.getScene().getWindow();
            alert.initOwner(stage);  // Set the alert's owner to the current stage
            alert.showAndWait();
        }
    }



    @FXML
    void btnRecept(ActionEvent event) throws SQLException, ClassNotFoundException {
        int lastOrderNum = OrderModel.recipt();
        OrderDto orderDto = OrderModel.orderSearch(lastOrderNum);

        String orderDate = orderDto.getOrderDate();
        String orderTime = orderDto.getOrderTime();
        double totalAmount = orderDto.getSubTotal();

        // Create bill content
        VBox billContent = new VBox(10);
        billContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New'; -fx-font-size: 12;");

        // Create the title and center it
        StackPane titlePane = new StackPane();
        Text title = new Text("ChefD");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        titlePane.getChildren().add(title);
        StackPane.setAlignment(title, Pos.CENTER);

        // Add the title to the bill content
        billContent.getChildren().add(titlePane);

        // Add order information
        Text orderInfo = new Text("Order Number: " + lastOrderNum +
                "\nDate: " + orderDate + "\nTime: " + orderTime + "   \n\nItems  " + "        qty" + "       price");
        billContent.getChildren().add(orderInfo);

        try {
            ArrayList<OrderDetailDto> items = OrderModel.orderDetailSearch(lastOrderNum);

            // Add each item to the receipt
            for (OrderDetailDto item : items) {
                Text itemText = new Text("- " + item.getItemsName() + "    " + item.getQty() + "     " + item.getTotalPrice());
                billContent.getChildren().add(itemText);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving order details: " + e.getMessage());
        }

        // Add the total amount
        Text total = new Text("\nTotal Amount: $" + totalAmount);
        total.setStyle("-fx-font-weight: bold;");
        billContent.getChildren().add(total);

        // Create a new Scene
        Scene scene = new Scene(billContent, 300, 400);

        // Create a new Stage for the receipt
        Stage receiptStage = new Stage();
        receiptStage.setScene(scene);
        receiptStage.setTitle("Order Receipt");

        // Resize the stage based on content
        receiptStage.sizeToScene();

        // Set the owner of the new stage
        Stage currentStage = (Stage) lblSubTotal.getScene().getWindow();
        receiptStage.initOwner(currentStage);

        // Show the stage
        receiptStage.show();
        print(billContent);
    }


    private void print(VBox billContent) {
        Printer printer = Printer.getDefaultPrinter();
        if (printer == null) {
            System.out.println("No printer found!");
            return;
        }

        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null && job.showPrintDialog(billContent.getScene().getWindow())) {
            boolean success = job.printPage(billContent);
            if (success) {
                job.endJob();
                System.out.println("Bill printed successfully.");
            } else {
                System.out.println("Failed to print the bill.");
            }
        }
    }

    double subTotal=0;
    private ArrayList<OrderDetailDto> orderDetailDtos;
    String formatDate;
    String formatTime;


    @FXML
    public void initialize() {
        orderDetailDtos = new ArrayList<>();
        lblSubTotal.setText("$"+subTotal);
        lblBalance.setText("$"+balance);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        formatDate = formatter.format(date);

        LocalTime myObj = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        formatTime = myObj.format(timeFormatter);

        // Set up TableView columns
        column1.setCellValueFactory(new PropertyValueFactory<>("item"));
        column2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        column3.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Clear existing items and set grid spacing
        cardGrid.getChildren().clear();
        cardGrid.setHgap(10);
        cardGrid.setVgap(10);

        try {
            // Load items from the database
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

        Button addButton = new Button("Add");
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
        double total = unitPrice * quantity;
        subTotal += total;
        // Create a new order item and add it to the TableView
        OrderTM newItem = new OrderTM(itemName, quantity, total);
        orderTable.getItems().add(newItem);

        orderDetailDtos.add(new OrderDetailDto(itemName,quantity,total));
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        // Handle back button action
        Stage stage = (Stage) this.rootOrder.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.setFullScreen(false);
    }

    @FXML
    void btnClose(ActionEvent event) {
        // Handle close button action
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onAddClicked(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean b =OrderModel.placeOrder(new OrderDto(formatDate,formatTime,subTotal,orderDetailDtos));

        if(b){
            System.out.println("updated");
        }
        else {
            System.out.println("not updated");
        }

        lblSubTotal.setText("$"+subTotal);


    }
}
