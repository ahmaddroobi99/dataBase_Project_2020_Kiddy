/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Admin.Dashboard.Teachers;

import Admin.Dashboard.Students.StudentListViewCell;
import Admin.Dashboard.Teachers.TeachersModel;
import OracleConnection.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class TeachersController implements Initializable {

    @FXML
    //private ListView<Student> listView;
    private ListView<TeachersModel> listView;

    private ObservableList<TeachersModel> teachersObservableList;
    Connection connection = DBConnection.GetDatabaseConnection();
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String sql = null;



    public TeachersController() throws SQLException  {

        teachersObservableList = FXCollections.observableArrayList();
        sql = "SELECT * FROM T_TEACHER";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();
        System.out.println(resultSet);
                    System.out.println("0000000000");

        while (resultSet.next()) {
                    System.out.println("11111");

                            
            teachersObservableList.add(new TeachersModel(
                    resultSet.getInt("SID"), resultSet.getString("FNAME"),
                    resultSet.getString("LNAME"),resultSet.getString("GENDER"),
                    resultSet.getInt("SALARY"),resultSet.getString("EMAIL"),
                    resultSet.getString("BDAY"),resultSet.getString("ADDRESS"),
                    resultSet.getInt("PHONE"),resultSet.getString("PHOTO"), 
                    resultSet.getInt("CLASSID")
                    ));

        }


        



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(teachersObservableList);
        listView.setCellFactory(TeachersListView -> new TeachersListViewCell());
        FilteredList<TeachersModel> filteredData = new FilteredList<>(teachersObservableList, s -> true);
    }

}

/*
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class TeachersController implements Initializable {

    @FXML
    private ListView<Student> listView;
    private ListView<Student> listView1;

    private ObservableList<Student> studentObservableList;

    public TeachersController()  {

        studentObservableList = FXCollections.observableArrayList();

        //add some Students
        studentObservableList.addAll(
      //          new Student("123456789","Ahmad","Hamdan", "Email@gmail.com","Tulkarm","1500","0599999999","1st Sec5"),
        //        new Student("912345678","Ahmad","Hamdan", "Ahmad@gmail.com","Tulkarm","1500","0599999988","1st Sec4")
        );
        



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(studentObservableList);
        listView.setCellFactory(studentListView -> new TeachersListViewCell());
        FilteredList<Student> filteredData = new FilteredList<>(studentObservableList, s -> true);

    //ObservableList<Student> student = FXCollections.observableArrayList();
    //IntStream.range(0, 1000).mapToObj(Integer::toString).forEach(data::add);

    //FilteredList<String> filteredData = new FilteredList<>(data, s -> true);



        //listView1.setItems(studentObservableList);
        //listView1.setCellFactory(studentListView -> new StudentListViewCell1());

    }

}
*/