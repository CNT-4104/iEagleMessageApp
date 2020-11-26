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
    chat_textarea.clear();

    //Selecting the contact current user wishes to chat with
    Contact recipientContact = chat_tableview.getSelectionModel().getSelectedItem();

    //Get the username of the contact
    String recipient = recipientContact.getUsername();

    //Add the messages between current user and recipient into an ArrayList
    ArrayList<String> messageList = new ArrayList<>();
    for (Message x : Database_Accessor.getLiveMessage(recipient, Main.currentiMessageUser.getUsername())) {
      //Unread messages with the contact will be marked as read
      if(x.getMessage_type().equals("unread")){
        Database_Accessor.updateMessageType(x.message_id, "read");
      }
      //Doesn't display the deleted messages between users
      if (x.getMessage_type().equals("deleted")) {
            System.out.println("");
      }
      else {
        messageList.add(
        (Database_Accessor.lookup_iMessage_user(x.getCurrent_user_id()).getUsername()
            + ": "
            + x.message_context
            + "     "
            + x.getTime_of_message()
            + "\n"));
      }
      }
    for(String x: messageList){
      chat_textarea.appendText(x);
    }
  }

  @FXML
  void send_message(MouseEvent event) {
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
    chat_textarea.appendText(Main.currentiMessageUser.getUsername()+ ": " + message.message_context
        + "     "+message.getTime_of_message() + "\n");
    reply_textfield.clear();
  }


  public void initialize(){
    chat_name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
    chat_usesrname_col.setCellValueFactory(new PropertyValueFactory<>("Username"));
    ArrayList<Contact> chat_contact_list = new ArrayList<>();
    for(Contact x: Database_Accessor.getContacts(Main.currentiMessageUser.getUser_id())){
      chat_contact_list.add(x);
    }
    chat_tableview.getItems().addAll(chat_contact_list);
  }

}
