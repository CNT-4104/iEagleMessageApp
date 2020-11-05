package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class Activate_Account_Controller {

  @FXML
  private Label webview_label;

  @FXML
  private ToolBar activate_account_toolbar;

  @FXML
  private TextField txtField_URL;

  @FXML
  private WebView activate_account_webview;

  @FXML
  private Button done_button;

  @FXML
  private Label done_label;

  @FXML
  void go_back(MouseEvent event) {
    Main.createNewScene(event, "Login_FXML.fxml");
  }

  public void initialize(){
    WebEngine engine_one = activate_account_webview.getEngine();
    engine_one.load("https://www.fgcu.edu/its/students/activate");

  }

  public void button_click(MouseEvent event) {

  }
}
