package Admin.Dashboard.Teachers;

import OracleConnection.DBConnection;
import static OracleConnection.DBConnection.GetDatabaseConnection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author k.kh.Pc
 */
public class AddTeachersController implements Initializable {

   
 @FXML
    private ImageView studentImage;

    @FXML
    private JFXTextField txtLname;

    @FXML
    private JFXRadioButton radioMale;

    @FXML
    private RadioButton radioFemale;

    @FXML
    private JFXDatePicker txfDateBith;

    @FXML
    private JFXTextField txtSID;

    @FXML
    private JFXTextField txtFname;

    @FXML
    private JFXTextField txfEmail;

    @FXML
    private JFXTextField txfPassword;

    @FXML
    private JFXComboBox<String> comboClass;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txfPhone;

    @FXML
    private JFXTextField txfAddress;

    //************** for database connection***********
   
    Connection connection = null;
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String sql = null;
    
        //************** for radio button***********
    ToggleGroup ggroup;
    
        // **************for class and section observable list******************* 
    ObservableList<String> classNames = FXCollections.observableArrayList();
    
        //for image selection variables
    final FileChooser fileChooser = new FileChooser();
    FileInputStream imageInputStream = null;
    File file;
    
    //Connection connection = DBConnection.GetDatabaseConnection();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("test2 ");
        //group radio button
        ggroup = new ToggleGroup();
        radioMale.setToggleGroup(ggroup);
        radioMale.setSelected(true);
        radioFemale.setToggleGroup(ggroup);
                
        
        try {
            showClasses();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AddTeachersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
                   
    } 
    private void showClasses() throws ClassNotFoundException, SQLException{
       
        connection= DBConnection.GetDatabaseConnection();
        sql="select * from T_CLASS";
        statement = connection.prepareStatement(sql);
        
        resultSet = statement.executeQuery();
         classNames.clear();
        while (resultSet.next()) {
            classNames.add(resultSet.getString("name"));
            
        }
        comboClass.getItems().clear();
        comboClass.setItems(classNames);
    
    }
                     
    @FXML
    private void SaveSTD(javafx.event.ActionEvent actionEvent) throws FileNotFoundException, ClassNotFoundException, SQLException { 
        String classPrimaryKey = null;
        
        connection = GetDatabaseConnection();
        RadioButton selectbtn = (RadioButton) ggroup.getSelectedToggle();
        String fname = txtFname.getText();
        String lname = txtLname.getText();
        //String sid = txtSID.getText();
        String email = txfEmail.getText();
        String password = txfPassword.getText();
      //  int phone = Integer.parseInt(txfPhone.getText());
        String address = txfAddress.getText();

        sql = "select * from T_CLASS where name=?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, comboClass.getValue());
        resultSet = statement.executeQuery();
       
        if (resultSet.next()) {
           classPrimaryKey=resultSet.getString("ID");

        }
        
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you soure to save student record?");
        alert.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            if(file==null) {       

              sql = "insert into T_TEACHER (SID,FNAME,LNAME,EMAIL,PASSWORD,SALARY,CLASSID,ADDRESS,PHONE,PHOTO, RESUME,GENDER,BDAY)"
                        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(txtSID.getText()));
                statement.setString(2, fname);
                statement.setString(3, lname);
                statement.setString(4, email);
                statement.setString(5, password);
                statement.setInt(6, Integer.parseInt(txtSalary.getText()));
                statement.setString(7, classPrimaryKey);
                statement.setString(8, address);
                statement.setInt(9, Integer.parseInt(txfPhone.getText()));
                statement.setBinaryStream(10, null);
                statement.setBinaryStream(11, null);            
                statement.setString(12, selectbtn.getText());
                statement.setDate(13, Date.valueOf(txfDateBith.getValue()));
 
                statement.execute();
                showNotifications("Student record", "Student Add Successfully");
                clearFields();  
        
            } 
            else{
                imageInputStream = new FileInputStream(file);
                String absolute = file.getAbsolutePath();
                System.out.println(absolute);
                
                 System.out.println("test7577");
              sql = "insert into T_TEACHER (SID,FNAME,LNAME,EMAIL,PASSWORD,SALARY,CLASSID,ADDRESS,PHONE, RESUME,GENDER,BDAY,PHOTO)"
                        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(txtSID.getText()));
                statement.setString(2, fname);
                statement.setString(3, lname);
                statement.setString(4, email);
                statement.setString(5, password);
                statement.setInt(6, Integer.parseInt(txtSalary.getText()));
                statement.setString(7, classPrimaryKey);
                statement.setString(8, address);
                statement.setInt(9, Integer.parseInt(txfPhone.getText()));
                statement.setBinaryStream(10, null);            
                statement.setString(11, selectbtn.getText());
                statement.setDate(12, Date.valueOf(txfDateBith.getValue()));
    
                statement.setString(13, absolute);
                //statement.setBlob(13, imageInputStream);
                System.out.println(imageInputStream);

                
                statement.execute();
                showNotifications("Student record", "Student Add Successfully");
                clearFields();  
                
            }
                
        }
            
    
    }
    private void clearFields(){
        
    
        txtFname.setText("");
        txfPhone.setText("");
        txfEmail.setText("");
        txfAddress.setText("");
        txfPassword.setText("");
        imageInputStream = null;
        txfDateBith.setValue(null);
        studentImage.setImage(null);
        comboClass.setValue("");
    
    }
    @FXML
    private void selectImage(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            Image image = new Image(file.toURI().toString());
            studentImage.setImage(image);

        }
        
    }
        private void showNotifications(String title, String text) {

        Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(3))
                .darkStyle()
                .hideCloseButton()
                .position(Pos.TOP_LEFT)
                .showConfirm();

    }


  

    @FXML
    private void closeWindow(ActionEvent event) {
        final Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
   
   
}