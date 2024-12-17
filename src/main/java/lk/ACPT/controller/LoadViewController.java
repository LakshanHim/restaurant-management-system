package lk.ACPT.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    void btnBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootLoadView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Select-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

    }

    public void initialize() {
        // Load data for the TableView
        ArrayList<MenuTM> menu = MenuModel.LoadForm();

        // Configure TableView columns
        tblView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        // Set data into the TableView
        tblView.setItems(FXCollections.observableList(menu));
    }



}