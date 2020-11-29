package sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import org.h2.engine.Database;

public class Main extends Application {
    // TO-DO
    // We should make getters / setters for these instead of making them public
    public static iMessageUser currentiMessageUser;
    public static Message currentMessage;
    private static Client client = new Client("10.0.0.43", 6177);


    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load GUI, starting at login page
        Parent root = FXMLLoader.load(getClass().getResource("Login_FXML.fxml"));
        Scene sceneMain = new Scene(root);
        primaryStage.setTitle("iEagle");
        primaryStage.setScene(sceneMain);
        sceneMain.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        primaryStage.show();

        // Connect client object to server

        // Creates listener from interface
        // Listens for whether users are online or offline and prints them
        client.addUserStatusListener(new UserStatusListener() {
            @Override
            public void isOnline(String username) {
                System.out.println(username + " is online.");
            }

            @Override
            public void isOffline(String username) {
                System.out.println(username + " is offline.");
            }
        });

        // Creates listener from interface
        // Listens for incoming messages and prints them
        client.addMessageListener(new MessageListener() {
            @Override
            public void uponReceivingMessage(String sender, String messageContent) {
                System.out.println(sender + ": " + messageContent);
            }
        });

        // If client is able to connect to the server
        if (client.connectToServer()) {
            System.out.println("Connection Successful!");
        } else {
            System.out.println("Failed to connect!");
        }
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

    public static Client getClient() {
        return client;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
