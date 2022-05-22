import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class StatisticsController {

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

    @FXML
    void initialize() {
      typeChoisceBox.getItems().addAll("Dense", "Sparse");
      firstAlgorithmChoiceBox.getItems().addAll("GramSchmidt", "LUDdecomposition", "QRDecomposition");
      secondAlgorithmChoiceBox.getItems().addAll("GramSchmidt", "LUDdecomposition", "QRDecomposition");
      

    }

}
