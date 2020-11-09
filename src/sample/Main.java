package sample;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Final test comment (hopefully)
        Parent root = FXMLLoader.load(getClass().getResource("Login_FXML.fxml"));
        Scene sceneMain = new Scene(root);
        primaryStage.setTitle("iEagle");
        primaryStage.setScene(sceneMain);
        sceneMain.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        primaryStage.show();
    }

    public static void createNewScene(Event event, String newFileFXML) {
        Parent newRoot = null;
        try {
            newRoot = FXMLLoader.load(Main.class.getResource(newFileFXML));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene newScene = new Scene(newRoot);
        newScene.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
