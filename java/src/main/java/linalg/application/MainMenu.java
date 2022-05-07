import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class MainMenu extends Application {

  @Override
  public void start(Stage primaryStage) throws IOException{   
    Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));   
    primaryStage.setTitle("Algorithms");
    primaryStage.setScene(new Scene(root, 800, 800));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}