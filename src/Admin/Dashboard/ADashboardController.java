package Admin.Dashboard;

import Admin.*;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ADashboardController implements Initializable {

    @FXML
    private AnchorPane holderPane;
    @FXML
    private JFXButton btnDashboard2;
    @FXML
    AnchorPane pricing;
    AnchorPane Home,Teachers,AddTeacher,Students,AddStudent,Inbox;
    
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnTeachers;
    @FXML
    private JFXButton btnStudents;
    @FXML
    private JFXButton btnClass;
    @FXML
    private JFXButton btnEmail;
    @FXML
    private JFXButton btnExit;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             Home = FXMLLoader.load(getClass().getResource("Home.fxml"));
             Teachers = FXMLLoader.load(getClass().getResource("/Admin/Dashboard/Teachers/Teachers.fxml"));
             AddTeacher = FXMLLoader.load(getClass().getResource("/Admin/Dashboard/Teachers/AddTeachers.fxml"));
             Students = FXMLLoader.load(getClass().getResource("/Admin/Dashboard/Students/Students.fxml"));
             AddStudent = FXMLLoader.load(getClass().getResource("/Admin/Dashboard/Students/AddStudents.fxml"));
             Inbox = FXMLLoader.load(getClass().getResource("/Admin/Dashboard/EmailSend.fxml"));
            setNode(Home);
        } catch (IOException ex) {
            Logger.getLogger(ADashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Set selected node to a content holder
    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    

    @FXML
    private void switchHome(ActionEvent event) {
        setNode(Home);
    }

    @FXML
    private void switchTeachers(ActionEvent event) {
        setNode(Teachers);
    }
     @FXML
    private void switchAddTeacher(ActionEvent event) {
        setNode(AddTeacher);
    }

    @FXML
    private void switchStudents(ActionEvent event) {
        setNode(Students);
    }

    @FXML
    private void switchAddStudent(ActionEvent event) {
        setNode(AddStudent);
    }

    @FXML
    private void switchInbox(ActionEvent event) {
        setNode(Inbox);
    }
    
    @FXML
    private void Exit() {
        Platform.exit();
    }

    

    

}
