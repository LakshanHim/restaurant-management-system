package lk.ACPT.controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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
    void btnMenu(ActionEvent event) {

    }

    @FXML
    void btnOrder(ActionEvent event) {

    }

    @FXML
    void btnReport(ActionEvent event) {

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

}
