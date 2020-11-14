package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;



public class Login_Controller {

  @FXML
  private AnchorPane Login_Anchorpane;

  @FXML
  private Label Login_Header_Label;

  @FXML
  private TextField email_textfield;

  @FXML
  private TextField password_textfield;

  @FXML
  private Button login_button;

  @FXML
  private Hyperlink Activate_Account_hyperlink;

  @FXML
  private Hyperlink Forgot_Password_hyperlink;

  @FXML
  private ImageView login_image_view;

  @FXML
  public void initialize(){

  }

  @FXML
  void activate_new_account(MouseEvent event) {
    Main.createNewScene(event, "Activate_Account_FXML.fxml");
  }

  @FXML
  void get_forgot_password(MouseEvent event) {

  }

  @FXML
  void login_user(MouseEvent event) {
    Main.createNewScene(event, "Home_Page_FXML.fxml");

  }

}
