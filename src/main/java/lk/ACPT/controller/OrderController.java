package lk.ACPT.controller;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ACPT.dto.OrderDetailDto;
import lk.ACPT.dto.OrderDto;
import lk.ACPT.model.OrderModel;
import lk.ACPT.model.SearchModel;
import lk.ACPT.tm.OrderTM;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class OrderController {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNum;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblRemain;

    @FXML
    private Label lblTime;

    @FXML
    private BorderPane rootOrder;

    @FXML
    private Spinner<Integer> spi1;

    @FXML
    private Spinner<Integer> spi2;

    @FXML
    private Spinner<Integer> spi3;

    @FXML
    private Spinner<Integer> spi4;

    @FXML
    private Spinner<Integer> spi5;

    @FXML
    private Spinner<Integer> spi6;

    @FXML
    private Spinner<Integer> spi7;

    @FXML
    private Spinner<Integer> spi8;

    @FXML
    private TableView<OrderTM> tblCart;

    @FXML
    private TextField txtCash;
    private JPasswordField setTime;
    private ArrayList<OrderDetailDto> orderDetailDtos;
    private  double subTotal =0 ;
    private ArrayList<OrderTM> itemTMS;

    @FXML
    void Payed(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrder(ActionEvent event) {
        DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = dateformatter.format(date);
        lblDate.setText(format);

        LocalTime myObj = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = myObj.format(formatter);
        lblTime.setText(formattedTime);
        lblPrice.setText(String.valueOf(subTotal));

        OrderModel.placeOrder(new OrderDto(format,formattedTime,subTotal,orderDetailDtos));

    }


    @FXML
    void btnAdd1(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi1.getValue();
        int id = 1;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        subTotal += totalPrice;


    }

    @FXML
    void btnAdd2(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi2.getValue();
        int id = 2;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

    }

    @FXML
    void btnAdd3(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi3.getValue();
        int id = 3;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

    }

    @FXML
    void btnAdd4(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi4.getValue();
        int id = 4;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));
    }

    @FXML
    void btnAdd5(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi5.getValue();
        int id = 5;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));
    }

    @FXML
    void btnAdd6(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi6.getValue();
        int id = 6;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));
    }

    @FXML
    void btnAdd7(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi7.getValue();
        int id = 7;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));
    }

    @FXML
    void btnAdd8(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi8.getValue();
        int id = 8;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));
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
    void bynClose(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    public void initialize() {
        // Set a ValueFactory to the Spinner to define range and default value
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min: 0, Max: 100, Default: 0
        spi1.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min: 50, Max: 150, Default: 50
        spi2.setValueFactory(valueFactory2);

        SpinnerValueFactory<Integer> valueFactory3 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min: 50, Max: 150, Default: 50
        spi3.setValueFactory(valueFactory3);

        SpinnerValueFactory<Integer> valueFactory4 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min: 50, Max: 150, Default: 50
        spi4.setValueFactory(valueFactory4);

        SpinnerValueFactory<Integer> valueFactory5 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min: 50, Max: 150, Default: 50
        spi5.setValueFactory(valueFactory5);

        SpinnerValueFactory<Integer> valueFactory6 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min: 50, Max: 150, Default: 50
        spi6.setValueFactory(valueFactory6);

        SpinnerValueFactory<Integer> valueFactory7 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min: 50, Max: 150, Default: 50
        spi7.setValueFactory(valueFactory7);

        SpinnerValueFactory<Integer> valueFactory8 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min: 50, Max: 150, Default: 50
        spi8.setValueFactory(valueFactory8);

        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("totalPrice"));


        itemTMS = new ArrayList<>();


    }

}
