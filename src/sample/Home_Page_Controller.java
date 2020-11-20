package sample;

import java.util.ArrayList;
import javafx.collections.ObservableList;
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

    name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
    username_col.setCellValueFactory(new PropertyValueFactory<>("Username"));
    type_col.setCellValueFactory(new PropertyValueFactory<>("contact_type"));
    ArrayList<Contact> usersContactList = new ArrayList<>();
    for (Contact x : Database_Accessor.getContacts()) {
      usersContactList.add(x);
    }
    contacts_tableview.getItems().addAll(usersContactList);
    }


  @FXML
  void delete_contact(MouseEvent event) {

    ObservableList<Contact> ContactsList;
    ContactsList = contacts_tableview.getSelectionModel().getSelectedItems();
    Contact contactName = ContactsList.get(0);
    String contactToDelete = contactName.getUsername();
    Database_Accessor.deleteContact(contactToDelete);
    contacts_tableview.getItems().remove(contactToDelete);
    System.out.println("Contact will be deleted");

  }

  @FXML
  void go_to_chats(MouseEvent event) {
    Main.createNewScene(event, "chat_screen_FXML.fxml");

  }


  @FXML
  void go_to_contact_convo(MouseEvent event) {

    Main.createNewScene(event, "chat_screen_FXML.fxml");

  }

  @FXML
  void go_to_deleted_messages(MouseEvent event) {
    chat_contact_col.setCellValueFactory(new PropertyValueFactory<>("chat_contact"));
    context_col.setCellValueFactory(new PropertyValueFactory<>("message_context"));
    date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
    time_col.setCellValueFactory(new PropertyValueFactory<>("time_of_message"));
    message_type_col.setCellValueFactory(new PropertyValueFactory<>("message_type"));
    ArrayList<Message> deletedMessagesList = new ArrayList<>();
    for(Message x : Database_Accessor.getMessages("asmith2341")){
      if(x.getMessage_type().equals("deleted")){
        deletedMessagesList.add(x);
      }
      else{
        System.out.println("");
      }
  }
    messages_tableview.getItems().addAll(deletedMessagesList);


  }

  @FXML
  void logout_user(MouseEvent event) {
    Main.createNewScene(event, "Login_FXML.fxml");
  }

  @FXML
  void sort_other(MouseEvent event) {
    contacts_tableview.getItems().clear();
    name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
    username_col.setCellValueFactory(new PropertyValueFactory<>("Username"));
    type_col.setCellValueFactory(new PropertyValueFactory<>("contact_type"));
    ArrayList<Contact> otherContactList = new ArrayList<>();
    for (Contact x : Database_Accessor.getContacts()) {
      if (x.getContact_type().equals("Other")) {
        otherContactList.add(x);
      }
    }
    contacts_tableview.getItems().addAll(otherContactList);

  }

  @FXML
  void sort_professor(MouseEvent event) {
    contacts_tableview.getItems().clear();
    name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
    username_col.setCellValueFactory(new PropertyValueFactory<>("Username"));
    type_col.setCellValueFactory(new PropertyValueFactory<>("contact_type"));
    ArrayList<Contact> professorContactList = new ArrayList<>();
    for (Contact x : Database_Accessor.getContacts()) {
      if (x.getContact_type().equals("Professor")) {
        professorContactList.add(x);
      }
      }
      contacts_tableview.getItems().addAll(professorContactList);
  }

  @FXML
  void sort_student(MouseEvent event) {
    contacts_tableview.getItems().clear();
    name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
    username_col.setCellValueFactory(new PropertyValueFactory<>("Username"));
    type_col.setCellValueFactory(new PropertyValueFactory<>("contact_type"));
    ArrayList<Contact> studentContactList = new ArrayList<>();
    for (Contact x : Database_Accessor.getContacts()) {
      if (x.getContact_type().equals("Student")) {
        studentContactList.add(x);
      }
      }
      contacts_tableview.getItems().addAll(studentContactList);

  }

}

