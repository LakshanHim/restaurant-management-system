package lk.ACPT.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ACPT.db.DBConnection;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class Dashboard {
    @FXML
    private BorderPane rootDash;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label lblincome;

    @FXML
    private Label lblOdrNum;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootDash.getScene().getWindow();
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

    @FXML
    void btnAdd(ActionEvent event) {
        try {
            Stage stage = (Stage) this.rootDash.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Menusave-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnLoad(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootDash.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Loadview-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnOrder(ActionEvent event) {
        Stage stage = (Stage) this.rootDash.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Order-page.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Set the scene and enable fullscreen mode
        stage.setScene(scene);
        stage.setFullScreen(true);
    }

    @FXML
    void btnBill(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootDash.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PrintBill-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setFullScreen(true);
    }

    @FXML
    void btnUpdate(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootDash.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuUpdate-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void onButtonClick(ActionEvent event) {
        updateChartData(); // Update chart data on button click
    }

    // This method will update the chart with the subtotal data for all dates from the database
    private void updateChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Fetch the subtotals for all dates from the database
        fetchAllDatesSubtotals(series);

        // Update the BarChart with the new series
        barChart.getData().clear();
        barChart.getData().add(series);
    }

    // Method to fetch subtotals for all dates from the database
    private void fetchAllDatesSubtotals(XYChart.Series<String, Number> series) {
        String sql = "SELECT orderDate, SUM(totalAmount) FROM orders GROUP BY orderDate ORDER BY orderDate ASC";

        try{
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Loop through the result set and populate the series with data for each date
            while (resultSet.next()) {
                LocalDate orderDate = resultSet.getDate("orderDate").toLocalDate();
                double subtotal = resultSet.getDouble("SUM(totalAmount)");

                // Add the data for each date to the series
                series.getData().add(new XYChart.Data<>(orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), subtotal));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Call this method to set up an automatic update every day
    private void setupDailyUpdate() {
        // Use a Timeline to refresh data every 24 hours
        Timeline timeline = new Timeline(new KeyFrame(Duration.hours(24), event -> updateChartData()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play(); // Start the update process
    }

    private void totalAmountToday(){
        double totalAmounttoday = 0;
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT SUM(totalAmount) AS totalAmountToday FROM orders WHERE orderDate = CURDATE()");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { // Use if instead of while because only one row is expected
                totalAmounttoday = resultSet.getDouble("totalAmountToday"); // Use the alias
                lblincome.setText(String.valueOf("Total Amount:-"+ totalAmounttoday)); // Set the label text with the value
            } else {
                lblincome.setText("0.00"); // Set default value if no data is found
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
    private void OderNum(){
        int totalOrdersToday = 0;
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS totalOrdersToday FROM orders WHERE orderDate = CURDATE()");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { // Use if as only one row is expected
                totalOrdersToday = resultSet.getInt("totalOrdersToday"); // Retrieve the count
                lblOdrNum.setText(String.valueOf("Total Orders:-"+ totalOrdersToday)); // Set the label text with the value
            } else {
                lblOdrNum.setText("0"); // Default value if no data is found
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    // Initialize the controller
    public void initialize() {
        updateChartData();  // Initial update when the app starts
        setupDailyUpdate();// Set up the auto-update every day
        totalAmountToday();
        OderNum();


    }
}
