package lk.ACPT.controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ACPT.db.DBConnection;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SelectTypeController {
    @FXML
    private AnchorPane rootType;

    @FXML
    private Label setDate;

    @FXML
    private Label setTime;

    @FXML
    void btnAddItems(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootType.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuSave-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();

    }


    @FXML
    void btnMenuUpdate(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootType.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuUpdate-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @FXML
    void btnOrder(ActionEvent event) {
        Stage stage = (Stage) this.rootType.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Order-page.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

          // Set the scene and enable fullscreen mode
        stage.setScene(scene);
        stage.setFullScreen(true);


    }

    @FXML
    void btnReport(ActionEvent event) {
        try  {
            Connection connection = DBConnection.getDBConnection().getConnection();
            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();

            // Export order_details table
            exportOrderDetails(connection, workbook);

            // Export order table
            exportOrder(connection, workbook);

            // Save the Excel file to the specified path
            String filePath = "C:\\Users\\MSI\\OneDrive\\Documents\\database_data.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Excel file has been saved to: " + filePath);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText(null);
                alert.setContentText("The report has been successfully exported!");
                alert.showAndWait();
            } catch (Exception e) {
                System.out.println("Error saving file: " + e.getMessage());
            }

            // Close the workbook
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private  void exportOrderDetails(Connection connection, Workbook workbook) {

        try{
            String query = "SELECT * FROM order_details";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Create a new sheet for order_details
            Sheet sheet = workbook.createSheet("Order Details");

            // Create cell style for header
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Write header row with style
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("odid");
            headerRow.createCell(1).setCellValue("oid");
            headerRow.createCell(2).setCellValue("price");
            headerRow.createCell(3).setCellValue("qty");
            headerRow.createCell(4).setCellValue("itemname");

            // Apply style to header cells
            for (int i = 0; i < 5; i++) {
                headerRow.getCell(i).setCellStyle(headerStyle);
            }

            // Write data rows
            int rowIndex = 1;
            while (resultSet.next()) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(resultSet.getInt("odid"));
                dataRow.createCell(1).setCellValue(resultSet.getInt("oid"));
                dataRow.createCell(2).setCellValue(resultSet.getDouble("price"));
                dataRow.createCell(3).setCellValue(resultSet.getInt("qty"));
                dataRow.createCell(4).setCellValue(resultSet.getString("itemname"));
            }

            // Auto-size columns
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

        } catch (Exception e) {
            System.out.println("Error exporting order_details: " + e.getMessage());
        }
    }

    private void exportOrder(Connection connection, Workbook workbook) {

        try{
            String query = "SELECT * FROM orders";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Create a new sheet for order
            Sheet sheet = workbook.createSheet("Order");

            // Create cell style for header
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Write header row with style
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("id");
            headerRow.createCell(1).setCellValue("orderDate");
            headerRow.createCell(2).setCellValue("orderTime");
            headerRow.createCell(3).setCellValue("totalAmount");

            // Apply style to header cells
            for (int i = 0; i < 4; i++) {
                headerRow.getCell(i).setCellStyle(headerStyle);
            }

            // Write data rows
            int rowIndex = 1;
            double totalAmountSum = 0;
            while (resultSet.next()) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(resultSet.getInt("id"));
                dataRow.createCell(1).setCellValue(resultSet.getDate("orderDate").toString());
                dataRow.createCell(2).setCellValue(resultSet.getTime("orderTime").toString());
                double totalAmount = resultSet.getDouble("totalAmount");
                dataRow.createCell(3).setCellValue(totalAmount);

                totalAmountSum += totalAmount;
            }
            CellStyle subtotalStyle = workbook.createCellStyle();
            Font subtotalFont = workbook.createFont();
            subtotalFont.setBold(true);
            subtotalFont.setColor(IndexedColors.WHITE.getIndex());
            subtotalStyle.setFont(subtotalFont);
            subtotalStyle.setFillForegroundColor(IndexedColors.RED.getIndex());  // Set a red background for the subtotal row
            subtotalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row subtotalRow = sheet.createRow(rowIndex);
            subtotalRow.createCell(2).setCellValue("Subtotal:");
            subtotalRow.createCell(3).setCellValue(totalAmountSum);

            subtotalRow.getCell(2).setCellStyle(subtotalStyle);
            subtotalRow.getCell(3).setCellStyle(subtotalStyle);

            // Auto-size columns
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

        } catch (Exception e) {
            System.out.println("Error exporting order: " + e.getMessage());
        }
    }



    @FXML
    void btnLoadMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootType.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Loadview-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    public void initialize() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = formatter.format(date);
        setDate.setText(format);

        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss a");
            String currentTime = timeFormatter.format(new Date());
            setTime.setText(currentTime);
        }));
        clock.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        clock.play(); // Start the clock
    }

    @FXML
    void btnBill(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootType.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PrintBill-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setFullScreen(true);
    }
    @FXML
    void btnDash(ActionEvent event) {
        Stage stage = (Stage) this.rootType.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dash-page.fxml"));
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

}
