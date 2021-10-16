package Main;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    /*
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/splash/splashView.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    */
    
          @Override
   public void start(Stage stage) throws Exception {
       

        Parent root = FXMLLoader.load(getClass().getResource("/Admin/Dashboard/ADashboard.fxml"));   

        
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
