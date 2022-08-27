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
import linalg.algorithms.GivensRotation;
import linalg.matrix.MatrixFactory;
import linalg.matrix.SparseMatrix;
import linalg.algorithms.concurrency.MultithreadedAlgorithms;
import linalg.random.RandomMatrices;
import java.util.function.Supplier;
import javafx.scene.control.TextField;
import java.time.*;
import java.util.concurrent.*;
import javafx.application.Platform;
import java.util.function.Consumer;



public class StatisticsController {

  private Scene backScene;
  private ArrayList<Thread> threads;
  private Label[] averageTimeArray;
  private Label[] worstRunArray;
  private Label[] bestRunArray;

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
  private ChoiceBox secondChoiceBox;

  @FXML
  private Label errorLabel;

  @FXML
  private Label worstRun;

  @FXML
  private Label bestRun;

  @FXML
  private Label secondAverageTime;

  @FXML
  private Label secondBestRun;

  @FXML
  private Label secondWorstRun;

  @FXML
  private Label worstRunTextLabel;

  @FXML
  private Label bestRunTextLabel;

  @FXML
  private Label averageTextLabel;

  StatisticsController(Scene backScene) {
    this.backScene = backScene;
  }

  private <T extends Matrix> void runThread(Supplier<T> data, Consumer<T> algo, int i) {
    Thread thread = new Thread(() -> {
      try{
        AlgorithmStats algorithmStats = AlgorithmStats.GetAlgoStats(data, algo,10);
        if(Thread.interrupted()){
          return;
        }
        Platform.runLater(() -> {
          setLabels(algorithmStats, i);
        });
      }catch(Exception ignore){}
    });
    threads.add(thread);
    thread.start();
  }

  private void setLabels(AlgorithmStats algorithmStats, int i) {
    Duration duration;
    long seconds, mseconds;
    duration = algorithmStats.AverageRunTime();
    seconds = duration.getSeconds();
    mseconds = duration.toMillis() - seconds*1000;
    averageTimeArray[i].setText(Long.toString(seconds) + "s " + Long.toString(mseconds) + "ms");
    duration = algorithmStats.BestRun();
    seconds = duration.getSeconds();
    mseconds = duration.toMillis() - seconds*1000;
    bestRunArray[i].setText(Long.toString(seconds) + "s " + Long.toString(mseconds) + "ms");
    duration = algorithmStats.WorstRun();
    seconds = duration.getSeconds();
    mseconds = duration.toMillis() - seconds*1000;
    worstRunArray[i].setText(Long.toString(seconds) + "s " + Long.toString(mseconds) + "ms");
  }
  
  private void buttonClicked(ChoiceBox choiceBox){
    errorLabel.setText("");
    String size = matrixSize.getText();
    int sz;
    try {
      sz = Integer.parseInt(size);
    } catch (NumberFormatException e) {
      errorLabel.setText("Wrong input!");
      return;
    } 
    int i = choiceBox == algorithmChoiceBox ? 0 : 1;
    Supplier<Matrix> data = typeChoisceBox.getValue() == "Dense" ? () -> RandomMatrices.RandomDenseMatrix(sz, sz) : 
      () -> RandomMatrices.RandomSparseMatrix(sz, sz, sz);
    Supplier<SparseMatrix> dataSparse = () -> RandomMatrices.RandomSparseMatrix(sz, sz, sz);
    if(choiceBox.getValue() == "LUDecomposition"){
      runThread(data, m -> new LUDecomposition(m).Decompose(), i);
    }
    if(choiceBox.getValue() == "QRGramSchmidt"){
      runThread(data, m -> new GramSchmidt().Decompose(m), i);
    }
    if(choiceBox.getValue() == "HouseHolder"){
      runThread(data, m -> new Householder().Decompose(m), i);
    }
    if(choiceBox.getValue() == "QRGivensRotation"){
      runThread(data, m -> new GivensRotation().Decompose(m), i);
    }
    if(choiceBox.getValue() == "Matrix multiplication"){
      if(typeChoisceBox.getValue() == "Dense"){
        runThread(data, m -> new MatrixFactory().DenseMultiply(m, m), i);
      }else{
        runThread(dataSparse, m -> new MatrixFactory().SparseMultiply(m, m), i);
      }
    }
    if(choiceBox.getValue() == "Concurrent matrix multiplication"){
      if(typeChoisceBox.getValue() == "Dense"){
        runThread(data, m -> {
          try{
            new MultithreadedAlgorithms().DenseMatrixProduct(m, m);
          }catch(Exception ignore){};
        }, i);
      } else {
        runThread(dataSparse, m -> {
          try{
            new MultithreadedAlgorithms().SparseMatrixProduct(m, m);
          }catch(Exception ignore) {};
        }, i);
      }
    }
  }
  
  private void clearLabels() {
    averageTime.setText("");
    bestRun.setText("");
    worstRun.setText("");
    secondAverageTime.setText("");
    secondBestRun.setText("");
    secondWorstRun.setText("");
  }

  @FXML
  public void onRunClicked(MouseEvent event) {
    clearLabels();
    averageTextLabel.setVisible(false);
    bestRunTextLabel.setVisible(false);
    worstRunTextLabel.setVisible(false);
    for(Thread thread: threads){
      thread.interrupt();
    }
    buttonClicked(algorithmChoiceBox);
  }
    
  @FXML
  public void onCompareClicked (MouseEvent event){
    clearLabels();
    averageTextLabel.setVisible(true);
    bestRunTextLabel.setVisible(true);
    worstRunTextLabel.setVisible(true);
    for(Thread thread: threads){
      thread.interrupt();
    }
    buttonClicked(algorithmChoiceBox);
    buttonClicked(secondChoiceBox);
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
    threads = new ArrayList();

    averageTextLabel.setVisible(false);
    bestRunTextLabel.setVisible(false);
    worstRunTextLabel.setVisible(false);
    averageTimeArray = new Label[2];
    worstRunArray = new Label[2];
    bestRunArray = new Label[2];
    averageTimeArray[0] = averageTime;
    averageTimeArray[1] = secondAverageTime;
    worstRunArray[0] = worstRun;
    worstRunArray[1] = secondWorstRun;
    bestRunArray[0] = bestRun;
    bestRunArray[1] = secondBestRun;
    
    typeChoisceBox.getItems().addAll("Dense", "Sparse");
    typeChoisceBox.setValue("Dense");
    algorithmChoiceBox.getItems().addAll("QRGramSchmidt", "LUDecomposition", "HouseHolder","QRGivensRotation", 
      "Matrix multiplication", "Concurrent matrix multiplication");

    secondChoiceBox.getItems().addAll("QRGramSchmidt", "LUDecomposition", "HouseHolder","QRGivensRotation", 
      "Matrix multiplication", "Concurrent matrix multiplication");

    secondChoiceBox.setValue("QRGramSchmidt");
    algorithmChoiceBox.setValue("QRGramSchmidt");
  }
}
