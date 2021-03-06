package client;

import controller.PageLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    PageLoader pageLoader = new PageLoader();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/sample.fxml"));
        primaryStage.setTitle("Voice chat");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 460, 625));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
