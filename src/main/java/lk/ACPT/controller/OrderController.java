package lk.ACPT.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ACPT.dto.OrderDetailDto;
import lk.ACPT.model.SearchModel;
import lk.ACPT.tm.OrderTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class OrderController {
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


    private  double subTotal =0 ;
    private ArrayList<OrderTM> itemTMS;

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


    }

    @FXML
    void btnAdd2(ActionEvent event) {
        Integer value = spi2.getValue();
        System.out.println(value);

    }

    @FXML
    void btnAdd3(ActionEvent event) {
        Integer value = spi3.getValue();
        System.out.println(value);

    }

    @FXML
    void btnAdd4(ActionEvent event) {
        Integer value = spi4.getValue();
        System.out.println(value);
    }

    @FXML
    void btnAdd5(ActionEvent event) {
        Integer value = spi5.getValue();
        System.out.println(value);
    }

    @FXML
    void btnAdd6(ActionEvent event) {
        Integer value = spi6.getValue();
        System.out.println(value);
    }

    @FXML
    void btnAdd7(ActionEvent event) {
        Integer value = spi7.getValue();
        System.out.println(value);
    }

    @FXML
    void btnAdd8(ActionEvent event) {
        Integer value = spi8.getValue();
        System.out.println(value);
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
