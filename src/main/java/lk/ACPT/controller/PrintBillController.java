package lk.ACPT.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ACPT.db.DBConnection;
import lk.ACPT.dto.OrderDetailDto;
import lk.ACPT.dto.OrderDto;
import lk.ACPT.model.OrderModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PrintBillController {

    @FXML
    private BorderPane rootBill;

    @FXML
    private TextArea txtReceipt;

    @FXML
    private TextField txtid;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootBill.getScene().getWindow();
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
    void btnId(ActionEvent event) throws SQLException, ClassNotFoundException {
        int lastOrderNum = Integer.parseInt(txtid.getText());
        OrderDto orderDto = OrderModel.orderSearch(lastOrderNum);

        String orderDate = orderDto.getOrderDate();
        String orderTime = orderDto.getOrderTime();
        double totalAmount = orderDto.getSubTotal();

        // Build the receipt content as a string
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("        ChefD\n")
                .append("----------------------------\n")
                .append("Order Number: ").append(lastOrderNum).append("\n")
                .append("Date: ").append(orderDate).append("\n")
                .append("Time: ").append(orderTime).append("\n")
                .append("----------------------------\n")
                .append("Items         Qty     Price\n");

        try {
            ArrayList<OrderDetailDto> items = OrderModel.orderDetailSearch(lastOrderNum);

            // Add each item to the receipt
            for (OrderDetailDto item : items) {
                receiptContent.append(String.format("- %-12s %-8d $%.2f\n",
                        item.getItemsName(), item.getQty(), item.getTotalPrice()));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving order details: " + e.getMessage());
        }

        receiptContent.append("\n")
                .append("----------------------------\n")
                .append("Total Amount: $").append(totalAmount).append("\n");

        // Set the content to the TextArea
        txtReceipt.setText(receiptContent.toString());
    }

    @FXML
    void btnPrint(ActionEvent event) {
        print(txtReceipt);
    }
    @FXML
    void btnAddMenu(ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) this.rootBill.getScene().getWindow();
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
    void btnLoadMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootBill.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Loadview-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @FXML
    void btnMenuUpdate(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootBill.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuUpdate-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnOrderDetail(ActionEvent event) {
        Stage stage = (Stage) this.rootBill.getScene().getWindow();
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
    void btnDash(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootBill.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dash-page.fxml"));
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

    private void print(TextArea textArea) {
        Printer printer = Printer.getDefaultPrinter();
        if (printer == null) {
            System.out.println("No printer found!");
            return;
        }

        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null && job.showPrintDialog(textArea.getScene().getWindow())) {
            boolean success = job.printPage(textArea);
            if (success) {
                job.endJob();
                System.out.println("Bill printed successfully.");
            } else {
                System.out.println("Failed to print the bill.");
            }
        }
    }

    @FXML
      void btnExcel(ActionEvent event) {
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
}
