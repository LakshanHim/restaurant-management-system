package lk.ACPT.controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
    }

}
