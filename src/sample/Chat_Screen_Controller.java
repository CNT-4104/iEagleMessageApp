package sample;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.xml.bind.Marshaller.Listener;


public class Chat_Screen_Controller {

  @FXML
  private TableView<Contact> chat_tableview;

  @FXML
  private TableColumn<?, ?> chat_name_col;

  @FXML
  private TableColumn<?, ?> chat_usesrname_col;

  @FXML
  private Button go_to_conservation_button;

  @FXML
  private TextArea chat_textarea;

  @FXML
  private TextField reply_textfield;

  @FXML
  private Button send_button;

  @FXML
  private Button homescreen_button;

  @FXML
  void go_home(MouseEvent event) {
    Main.createNewScene(event, "Home_Page_FXML.fxml");
  }

  @FXML
  public void populate_conversation(MouseEvent event) {
    showMessage();

}

  @FXML
  void send_message(MouseEvent event) throws IOException {
    Contact recipient = chat_tableview.getSelectionModel().getSelectedItem();
    Message message =
        new Message(Main.currentiMessageUser.getUser_id(),
            "sent",
            LocalTime.now(),
            recipient.getUsername(),
            LocalDate.now(),
            reply_textfield.getText());
    Main.currentMessage = message;
    String messageContext = reply_textfield.getText();
    Database_Accessor.addMessage(message);
    Main.getClient().sendMessage(recipient.getUsername(), messageContext);
    chat_textarea.appendText(Main.currentiMessageUser.getUsername()+": " + messageContext+"         "+LocalTime.now() + "\n");

    reply_textfield.clear();
  }

  //Uses message listener to receive incoming messages from the server.
  public void showMessage(){
        chat_textarea.appendText(Main.liveMessage+"          "+LocalTime.now()+"\n");

  }

  public void get_user_status(){
    UserStatusListener userStatusListener = new UserStatusListener() {
      ArrayList<Contact> online_contact_list = new ArrayList<>();
      ArrayList<Contact> offline_contact_list = new ArrayList<>();
      @Override
      public void isOnline(String onlineUser) {
        Contact onlineContact = Database_Accessor.getOnlineContacts("asmith2341");
        online_contact_list.add(onlineContact);
        chat_tableview.getItems().addAll(online_contact_list);
      }

      @Override
      public void isOffline(String offlineUser) {
        Contact offlineContact = Database_Accessor.getOnlineContacts(offlineUser);
        offline_contact_list.add(offlineContact);
        chat_tableview.getItems().addAll(offline_contact_list);
      }
    };
    }

  public void initialize(){
    chat_name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
    chat_usesrname_col.setCellValueFactory(new PropertyValueFactory<>("Username"));
   chat_tableview.getItems().addAll(Database_Accessor.getContacts(Main.currentiMessageUser.getUser_id()));

    }

  }


