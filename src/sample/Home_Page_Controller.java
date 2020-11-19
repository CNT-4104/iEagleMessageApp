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
  void initialize(){
    name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
    username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
    ArrayList<Contact> usersContactList = new ArrayList<>();

    contacts_tableview.getItems().addAll(Database_Accessor.getContacts());


  }

  @FXML
  void delete_contact(MouseEvent event) {
    System.out.println("Contact will be deleted");

  }

  @FXML
  void go_to_contact_convo(MouseEvent event) {
    System.out.println("Converdation");


  }

  @FXML
  void go_to_deleted_messages(MouseEvent event) {

  }

  @FXML
  void logout_user(MouseEvent event) {
    Main.createNewScene(event, "Login_FXML.fxml");
  }

  @FXML
  void sort_other(MouseEvent event) {

  }

  @FXML
  void sort_professor(MouseEvent event) {

  }

  @FXML
  void sort_student(MouseEvent event) {

  }

}

