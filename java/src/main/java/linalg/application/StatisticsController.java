import javafx.scene.control.ChoiceBox;
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

public class StatisticsController {

    Scene backScene;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox firstAlgorithmChoiceBox;

    @FXML
    private ChoiceBox secondAlgorithmChoiceBox;

    @FXML
    private ChoiceBox typeChoisceBox;

    StatisticsController(Scene backScene) {
      this.backScene = backScene;
    }

    @FXML
    public void onBackClicked(MouseEvent event){
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
      Stage currentStage = (Stage) typeChoisceBox.getScene().getWindow();
      currentStage.setScene(backScene);
      currentStage.show();
    }

    @FXML
    void initialize() {
      typeChoisceBox.getItems().addAll("Dense", "Sparse");
      firstAlgorithmChoiceBox.getItems().addAll("GramSchmidt", "LUDdecomposition", "QRDecomposition");
      secondAlgorithmChoiceBox.getItems().addAll("GramSchmidt", "LUDdecomposition", "QRDecomposition");
    }

}
