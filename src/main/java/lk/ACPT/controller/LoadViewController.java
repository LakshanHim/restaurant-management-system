package lk.ACPT.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import lk.ACPT.model.MenuModel;
import lk.ACPT.tm.MenuTM;

import java.io.IOException;
import java.util.ArrayList;

public class LoadViewController {

    @FXML
    private AnchorPane rootLoadView;

    @FXML
    private TableView<MenuTM> tblView;

    @FXML
    private TableColumn<MenuTM, String> colItemName;

    @FXML
    private TableColumn<MenuTM, Double> colUnitPrice;

    @FXML
    private TableColumn<MenuTM, String> colDescription;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootLoadView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

    }

    public void initialize() {
        // Bind columns to properties
        tblView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Enable editing for itemName, unitPrice, and description
        colItemName.setCellFactory(TextFieldTableCell.forTableColumn());
        colUnitPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colDescription.setCellFactory(TextFieldTableCell.forTableColumn());

        tblView.setEditable(true);

        // Set listeners to update the database when a cell is edited
        colItemName.setOnEditCommit(event -> {
            MenuTM selectedMenu = event.getRowValue();
            selectedMenu.setItemName(event.getNewValue());
            MenuModel.updateMenu(selectedMenu);
        });

        colUnitPrice.setOnEditCommit(event -> {
            MenuTM selectedMenu = event.getRowValue();
            selectedMenu.setUnitPrice(event.getNewValue());
            MenuModel.updateMenu(selectedMenu);
        });

        colDescription.setOnEditCommit(event -> {
            MenuTM selectedMenu = event.getRowValue();
            selectedMenu.setDescription(event.getNewValue());
            MenuModel.updateMenu(selectedMenu);
        });

        // Load data into TableView
        ArrayList<MenuTM> menu = MenuModel.LoadForm();
        tblView.setItems(FXCollections.observableList(menu));
    }




}