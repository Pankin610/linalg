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
import linalg.algorithms.Householder;
import linalg.random.RandomMatrices;
import java.util.function.Supplier;
import javafx.scene.control.TextField;
import java.time.*;
import java.util.concurrent.*;
import javafx.application.Platform;
import java.util.function.Consumer;



public class StatisticsController {

    ArrayList<String> allAlgorithms;
    Scene backScene;

    Thread thread = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox algorithmChoiceBox;

    @FXML
    private Label averageTime;

    @FXML
    private ChoiceBox typeChoisceBox;

    @FXML
    private TextField matrixSize;

    @FXML
    private Label errorLabel;

    @FXML
    private Label worstRun;

    @FXML
    private Label bestRun;

    StatisticsController(Scene backScene) {
      this.backScene = backScene;
    }
    
    void runThread(Supplier<Matrix> data, Consumer<Matrix> algo){
      thread = new Thread(() -> {
        AlgorithmStats algorithmStats = AlgorithmStats.GetAlgoStats(data, algo,10);
        if(Thread.interrupted()){
          return;
        }
        Platform.runLater(() -> {
          setLabels(algorithmStats);
        });
      });
      thread.start();
    }


    void setLabels(AlgorithmStats algorithmStats){
      Duration duration;
      long seconds, mseconds;
      duration = algorithmStats.AverageRunTime();
      seconds = duration.getSeconds();
      mseconds = duration.toMillis() - seconds*1000;
      averageTime.setText(Long.toString(seconds) + "s " + Long.toString(mseconds) + "ms");
      duration = algorithmStats.BestRun();
      seconds = duration.getSeconds();
      mseconds = duration.toMillis() - seconds*1000;
      bestRun.setText(Long.toString(seconds) + "s " + Long.toString(mseconds) + "ms");
      duration = algorithmStats.WorstRun();
      seconds = duration.getSeconds();
      mseconds = duration.toMillis() - seconds*1000;
      worstRun.setText(Long.toString(seconds) + "s " + Long.toString(mseconds) + "ms");
    }
 
    @FXML
    public void onCompareClicked(MouseEvent event){
      errorLabel.setText("");
      if(thread!=null){
        thread.interrupt();
      }
      String size = matrixSize.getText();
      int sz;
      try {
        sz = Integer.parseInt(size);
      } catch (NumberFormatException e) {
        errorLabel.setText("Wrong input!");
        return;
      } 
      Supplier<Matrix> data = typeChoisceBox.getValue() == "Dense" ? () -> RandomMatrices.RandomDenseMatrix(sz, sz) : () -> RandomMatrices.RandomSparseMatrix(sz, sz, sz);
      if(algorithmChoiceBox.getValue() == "LUDecomposition"){
        runThread(data, m -> new LUDecomposition(m).Decompose());
      }
      if(algorithmChoiceBox.getValue() == "GramSchmidt"){
        runThread(data, m -> new GramSchmidt().Decompose(m));
      }
      if(algorithmChoiceBox.getValue() == "HouseHolder"){
        runThread(data, m -> new Householder().Decompose(m));
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
      typeChoisceBox.setValue("Dense");
      algorithmChoiceBox.getItems().addAll("GramSchmidt", "LUDecomposition", "HouseHolder");
      algorithmChoiceBox.setValue("GramSchmidt");
    }
}
