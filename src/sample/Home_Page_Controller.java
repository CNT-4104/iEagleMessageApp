package sample;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Home_Page_Controller {

  @FXML
  private Button MessageButton;

  @FXML
  private Button deleted_messages_button;

  @FXML
  private Button logout_button;

  @FXML
  private ImageView LogoutButton;

  @FXML
  private Button RMessageButton;

  @FXML
  private Button RMessageButton1;

  @FXML
  private Button logout_button1;

  @FXML
  private ImageView LogoutButton1;

  @FXML
  private ImageView default_user_imageview;

  @FXML
  private ImageView LogoutButton11;

  @FXML
  private Label username_display_label;

  @FXML
  private ImageView LogoutButton12;

  @FXML
  private Button go_to_chats_button;
  @FXML
  private Button unread_button;

  @FXML
  private Button sort_student_button;

  @FXML
  private Button sort_professor_button;

  @FXML
  private Button sort_other_button;

  @FXML
  private Button delete_contact_button;

  @FXML
  private Button go_to_convo_button;


  @FXML
  private TableView<Contact> contacts_tableview;

  @FXML
  private TableColumn<?, ?> name_col;

  @FXML
  private TableColumn<?, ?> username_col;

  @FXML
  private TableColumn<?, ?> type_col;

  @FXML
  private TableView<Message> messages_tableview;

  @FXML
  private TableColumn<?, ?> chat_contact_col;

  @FXML
  private TableColumn<?, ?> context_col;

  @FXML
  private TableColumn<?, ?> date_col;

  @FXML
  private TableColumn<?, ?> time_col;

  @FXML
  private TableColumn<?, ?> message_type_col;

  @FXML
  void initialize() {
    int currentUserID = Main.currentiMessageUser.getUser_id();
    name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
    username_col.setCellValueFactory(new PropertyValueFactory<>("Username"));
    type_col.setCellValueFactory(new PropertyValueFactory<>("contact_type"));
    contacts_tableview.getItems().addAll(Database_Accessor.getContacts(currentUserID));
    }


  @FXML
  void delete_contact(MouseEvent event) {
    Contact deletedContact = contacts_tableview.getSelectionModel().getSelectedItem();
    String contactToDelete = deletedContact.getUsername();
    Database_Accessor.deleteContact(contactToDelete);
    contacts_tableview.getItems().remove(contactToDelete);
    System.out.println("Contact was be deleted");
  }

  @FXML
  void go_to_chats(MouseEvent event) {
    Main.createNewScene(event, "chat_screen_FXML.fxml");

  }

  @FXML
  void get_unread_messages(MouseEvent event) {
    messages_tableview.getItems().clear();
    chat_contact_col.setCellValueFactory(new PropertyValueFactory<>("chat_contact"));
    context_col.setCellValueFactory(new PropertyValueFactory<>("message_context"));
    date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
    time_col.setCellValueFactory(new PropertyValueFactory<>("time_of_message"));
    message_type_col.setCellValueFactory(new PropertyValueFactory<>("message_type"));
    messages_tableview.getItems().addAll(Database_Accessor.getUnreadMessage(Main.currentiMessageUser.getUser_id()));
  }


  @FXML
  void go_to_contact_convo(MouseEvent event) {

    Main.createNewScene(event, "chat_screen_FXML.fxml");

  }

  @FXML
  void go_to_deleted_messages(MouseEvent event) {
    messages_tableview.getItems().clear();
    chat_contact_col.setCellValueFactory(new PropertyValueFactory<>("chat_contact"));
    context_col.setCellValueFactory(new PropertyValueFactory<>("message_context"));
    date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
    time_col.setCellValueFactory(new PropertyValueFactory<>("time_of_message"));
    message_type_col.setCellValueFactory(new PropertyValueFactory<>("message_type"));
    messages_tableview.getItems().addAll(Database_Accessor.getDeletedMessage(Main.currentiMessageUser.getUser_id()));
  }

  @FXML
  void logout_user(MouseEvent event) {
    Main.createNewScene(event, "Login_FXML.fxml");
  }

  @FXML
  void sort_other(MouseEvent event) {
    int currentUserID = Main.currentiMessageUser.getUser_id();
    contacts_tableview.getItems().clear();
    ArrayList<Contact> otherContactList = new ArrayList<>();
    for (Contact x : Database_Accessor.getContacts(currentUserID)) {
      if (x.getContact_type().equals("Other")) {
        otherContactList.add(x);
      }
    }
    contacts_tableview.getItems().addAll(otherContactList);

  }

  @FXML
  void sort_professor(MouseEvent event) {
    int currentUserID = Main.currentiMessageUser.getUser_id();
    contacts_tableview.getItems().clear();
    ArrayList<Contact> professorContactList = new ArrayList<>();
    for (Contact x : Database_Accessor.getContacts(currentUserID)) {
      if (x.getContact_type().equals("Professor")) {
        professorContactList.add(x);
      }
      }
      contacts_tableview.getItems().addAll(professorContactList);
  }

  @FXML
  void sort_student(MouseEvent event) {
    int currentUserID = Main.currentiMessageUser.getUser_id();
    contacts_tableview.getItems().clear();
    ArrayList<Contact> studentContactList = new ArrayList<>();
    for (Contact x : Database_Accessor.getContacts(currentUserID)){
      if (x.getContact_type().equals("Student")) {
        studentContactList.add(x);
      }
      }
      contacts_tableview.getItems().addAll(studentContactList);

  }

}

