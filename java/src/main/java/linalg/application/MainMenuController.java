import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MainMenuController extends MainMenu{

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button start;

  @FXML
  void initialize() {
    assert start != null : "fx:id=\"start\" was not injected: check your FXML file 'Untitled'.";
  }

  @FXML
  void OnStartClicked(MouseEvent event) {
    System.out.println("Hello World!");
  }
  
}

