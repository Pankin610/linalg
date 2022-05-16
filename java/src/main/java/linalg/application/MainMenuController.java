import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import java.io.IOException;

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
  void OnStartClicked(MouseEvent event) throws IOException{
    double[][] arr1 = {
      {1, 2, 3, 4, 5},
      {5, 4, 3, 2, 1},
      {1, 3, 5, 2, 3},
      {2, 4, 5, 3, 1},
      {1, 5, 2, 4, 3}
    };
    
    DenseMatrixBuilder builder = new DenseMatrixBuilder(5,5);
    for(int i = 0; i < 5; i++){
      for(int j = 0; j < 5; j++){
        builder.SetValue(i, j, arr1[i][j]);
      }
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("MatrixView.fxml"));
    loader.setController(new MatrixViewController(builder.BuildMatrix()));

    Stage currentStage = (Stage) start.getScene().getWindow();
    currentStage.setScene(new Scene(loader.load(), 800, 800));
    currentStage.show();

  }
  
}
