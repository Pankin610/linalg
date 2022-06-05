import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import linalg.matrix.Matrix;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class MatrixViewController extends MainMenu{

  Matrix mat;
  Scene backScene;

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private GridPane grid;

  @FXML
  private Button back;

  MatrixViewController(Matrix mat, Scene backScene) {
    this.backScene = backScene;
    this.mat = mat;
  }

  @FXML
  public void onBackClicked(MouseEvent event){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
    Stage currentStage = (Stage) grid.getScene().getWindow();
    currentStage.setScene(backScene);
    currentStage.show();
  }

  @FXML
  void initialize() {
    if(mat.Rows() > 5 || mat.Cols() > 5) {
      throw new IllegalArgumentException("Wrong matrix dimensions");
    }
    
    for(int i = 0; i < mat.Rows(); i++){
      for(int j = 0; j < mat.Cols(); j++){
        Label label = new Label(Double.toString(mat.ValueAt(i,j)));
        label.setMinWidth(50);
        label.setMinHeight(50);
        grid.add(label, j, i);
      }
    }

  }

}