package sample;

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
import javax.xml.crypto.Data;


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

    Contact recipientContact = chat_tableview.getSelectionModel().getSelectedItem();
    String recipient = recipientContact.getUsername();
    Message message = new Message("sent", LocalTime.now(), recipient,
        LocalDate.now(), "", 0);
    Database_Accessor.addMessage(message);

    Message currentMessage = Database_Accessor.getLiveMessage();
    iMessageUser currentUser = Database_Accessor.getiMessageUser(Main.currentUser.getUsername());
    ArrayList<String> messageList = new ArrayList<>();
    for(Message x: Database_Accessor.getMessages(currentUser.getUsername())){
        messageList.add(x.chat_contact+ ": " + x.message_context + " " + "\n");
      }
    for(String x: messageList){
      chat_textarea.appendText(x);
    }
  }

  @FXML
  void send_message(MouseEvent event) {
    Message message =
        new Message(
            "sent", LocalTime.now(), "bjrhodes8553", LocalDate.now(), reply_textfield.getText(), 0);
    String messageContext = reply_textfield.getText();

    Database_Accessor.updateMessageContent(messageContext);
    Main.currentMessage = Database_Accessor.getLiveMessage();


    chat_textarea.appendText(message.chat_contact + ": " + message.message_context + " " + "\n");
    reply_textfield.clear();
  }


  public void initialize(){
    chat_name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
    chat_usesrname_col.setCellValueFactory(new PropertyValueFactory<>("Username"));
    ArrayList<Contact> chat_contact_list = new ArrayList<>();
    for(Contact x: Database_Accessor.getContacts()){
      chat_contact_list.add(x);
    }
    chat_tableview.getItems().addAll(chat_contact_list);
  }

}
