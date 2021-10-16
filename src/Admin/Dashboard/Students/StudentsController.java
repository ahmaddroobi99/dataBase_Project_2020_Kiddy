package Admin.Dashboard.Students;

import Admin.Dashboard.ADashboardController;
import OracleConnection.DBConnection;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StudentsController implements Initializable {

    @FXML
    //private ListView<Student> listView;
    private ListView<StudentModel> listView;
    @FXML
    private AnchorPane holderPane;
    AnchorPane NewStudent;
    
    @FXML
    private JFXButton btnAddNew;



    private ObservableList<StudentModel> studentObservableList;
    Connection connection = DBConnection.GetDatabaseConnection();
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String sql = null;



    public StudentsController() throws SQLException  {

        studentObservableList = FXCollections.observableArrayList();
        sql = "SELECT * FROM T_STUDENT";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();
        System.out.println(resultSet);
                    System.out.println("0000000000");

        while (resultSet.next()) {
                    System.out.println("11111");

                            
            studentObservableList.add(new StudentModel(
                    resultSet.getInt("ID_STD"), resultSet.getString("FNAME"),
                    resultSet.getString("MNAME"),resultSet.getString("LNAME"),
                    resultSet.getInt("SID"),resultSet.getString("GENDER"),
                    resultSet.getString("BDAY"),resultSet.getString("EMAIL"),
                    resultSet.getString("ADDRESS"),resultSet.getInt("PHONE"),
                    resultSet.getString("PHOTO"), resultSet.getString("BUS"),
                     resultSet.getInt("CLASSID"), resultSet.getInt("MFEE")
                    ));

        }


        



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(studentObservableList);
        listView.setCellFactory(studentListView -> new StudentListViewCell());
        FilteredList<StudentModel> filteredData = new FilteredList<>(studentObservableList, s -> true);
    }
    
    @FXML
    private void btnAddNewSTD(ActionEvent event) throws IOException {
        
             NewStudent = FXMLLoader.load(getClass().getResource("/Admin/Dashboard/Students/AddStudents.fxml"));
             setNode(NewStudent);
       

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
        
    
        
        
        
    

}
