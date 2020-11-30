package sample;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;



public class Login_Controller {

  @FXML
  private AnchorPane Login_Anchorpane;

  @FXML
  private Label Login_Header_Label;

  @FXML
  public TextField username_textfield;

  @FXML
  public PasswordField password_textfield;

  @FXML
  private Button login_button;

  @FXML
  private Label label_forgot_password;

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
    String username = username_textfield.getText();
    String forgotPasswordMessage = "Your password has been emailed to: " + username + "@eagle.fgcu.edu";
    label_forgot_password.setText(forgotPasswordMessage);

  }


  @FXML
  public void login_user(MouseEvent event) throws IOException {
   String username = username_textfield.getText();
   String password = password_textfield.getText();
   System.out.println("\nUser entered: \n"
           + "Username: " +username+
       "\nPassword: "+ password+"\n");

   System.out.println(Main.getClient().signIn(username, password));


    if(Main.getClient().signIn(username, password)){
      Main.currentiMessageUser = Database_Accessor.getiMessageUser(username);
      System.out.println("Current user:" + Main.currentiMessageUser.getUsername());
      Main.createNewScene(event, "Home_Page_FXML.fxml");
    }
    else{
      System.out.println("Please check what you entered and try again");
    }

    System.out.println("Logging in");

  }



}
