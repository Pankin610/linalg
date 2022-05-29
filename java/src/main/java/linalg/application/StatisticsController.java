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
import java.util.ArrayList;
import java.util.ArrayList;
import linalg.algorithm.comp.AlgorithmStats;
import linalg.algorithms.GramSchmidt;
import linalg.algorithms.LUDecomposition;
import linalg.algorithms.QRDecomposition;
import linalg.random.RandomMatrices;
import java.util.function.Supplier;

public class StatisticsController {

    ArrayList<String> allAlgorithms;

    Scene backScene;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox AlgorithmChoiceBox;

    @FXML
    private Label resultOfAlgorithm;

    @FXML
    private ChoiceBox typeChoisceBox;

    StatisticsController(Scene backScene) {
      this.backScene = backScene;
    }
    
    @FXML
    public void onCompareClicked(MouseEvent event){
      Supplier<Matrix> data = typeChoisceBox.getValue() == "Dense" ? () -> RandomMatrices.RandomDenseMatrix(100, 100) : () -> RandomMatrices.RandomSparseMatrix(100, 100, 100);
      AlgorithmStats algorithmStats = new AlgorithmStats();
      if(AlgorithmChoiceBox.getValue() == "LUDecomposition"){
        resultOfAlgorithm.setText(Long.toString(algorithmStats.GetAlgoStats(data, m -> new LUDecomposition(m).Decompose(), 1).AverageRunTime().toMillis()));
      }
      
      if(AlgorithmChoiceBox.getValue() == "GramSchmidt"){
        resultOfAlgorithm.setText(Long.toString(AlgorithmStats.GetAlgoStats(data, m -> new GramSchmidt().Decompose(m), 1).AverageRunTime().toMillis()));
      }

      if(AlgorithmChoiceBox.getValue() == "QRDecomposition"){
        resultOfAlgorithm.setText(Long.toString(AlgorithmStats.GetAlgoStats(data, m -> new GramSchmidt().Decompose(m), 1).AverageRunTime().toMillis()));
      }

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
      allAlgorithms = new ArrayList<>();
      allAlgorithms.add("GramSchmidt");
      allAlgorithms.add("LUDecomposition");
      allAlgorithms.add("QRDecomposition");
      typeChoisceBox.getItems().addAll("Dense", "Sparse");
      AlgorithmChoiceBox.getItems().addAll("GramSchmidt", "LUDecomposition", "QRDecomposition");
    }

}
