package lk.ACPT;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppInitilizer extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Login-page.fxml"));
//        Scene scene = new Scene(load);
//        primaryStage.setScene(scene);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.centerOnScreen();
//        primaryStage.show();


        BorderPane load = FXMLLoader.load(getClass().getResource("/view/Order-page.fxml"));
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
}
