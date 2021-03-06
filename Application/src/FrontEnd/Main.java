package FrontEnd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader sceneRaw = new FXMLLoader(getClass().getResource("Application.fxml"));
        Parent root = sceneRaw.load();
        primaryStage.setScene(new Scene(root, 644, 582));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
