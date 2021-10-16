package Admin.Dashboard.Students;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import OracleConnection.DBConnection;
import static OracleConnection.DBConnection.GetDatabaseConnection;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


public class AddStudentsController implements Initializable {

    @FXML
    private ImageView studentImage;

    @FXML
    private JFXTextField txtMname;
    @FXML
    private JFXTextField txtFee;

    @FXML
    private JFXRadioButton radioMale;

    @FXML
    private RadioButton radioFemale;

    @FXML
    private JFXDatePicker txfDateBith;

    @FXML
    private JFXTextField txfLName;

    @FXML
    private JFXTextField txtFname;

    @FXML
    private JFXTextField txtSID;

    @FXML
    private JFXTextField txfEmail;

    @FXML
    private JFXTextField txfPassword;

    @FXML
    private JFXComboBox<String> comboClass;
    

    @FXML
    private JFXRadioButton radioBus;

    @FXML
    private JFXRadioButton radioNBus;

    @FXML
    private JFXTextField txfPhone;

    @FXML
    private JFXTextField txfAdress;

    //************** for database connection***********
   
    Connection connection = null;
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String sql = null;
    
        //************** for radio button***********
    ToggleGroup ggroup;
    ToggleGroup bgroup;
    
        // **************for class and section observable list******************* 
    ObservableList<String> classesNames = FXCollections.observableArrayList();
    
        //for image selection variables
    final FileChooser fileChooser = new FileChooser();
    FileInputStream imageInputStream = null;
    File file;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ggroup = new ToggleGroup();
        radioMale.setToggleGroup(ggroup);
        radioMale.setSelected(true);
        radioFemale.setToggleGroup(ggroup);
        
        bgroup = new ToggleGroup();
        radioNBus.setToggleGroup(bgroup);
        radioNBus.setSelected(true);
        radioBus.setToggleGroup(bgroup);
        
        
        
        try {
            showClasses();
            
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AddStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
                   
    } 
    private void showClasses() throws ClassNotFoundException, SQLException{
       
        
        connection= DBConnection.GetDatabaseConnection();
        sql="select * from T_CLASS";
        statement = connection.prepareStatement(sql);
        
        resultSet = statement.executeQuery();
         classesNames.clear();
        while (resultSet.next()) {
            classesNames.add(resultSet.getString("NAME"));
            
        }
        comboClass.getItems().clear();
        comboClass.setItems(classesNames);
    
    }
                     
    @FXML
    private void SaveSTD(javafx.event.ActionEvent actionEvent) throws FileNotFoundException, ClassNotFoundException, SQLException { 
        String classPrimaryKey = null;    
        connection = GetDatabaseConnection();
        RadioButton selectbtn = (RadioButton) ggroup.getSelectedToggle();
        RadioButton selectbtnb = (RadioButton) bgroup.getSelectedToggle();

        String fname = txtFname.getText();
        String mname = txtMname.getText();
        String lname = txfLName.getText();
        //String sid = txtSID.getText();
        String email = txfEmail.getText();
        String password = txfPassword.getText();
        String adress = txfAdress.getText();
        

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

              sql = "insert into t_student (ID_STD, FNAME,MNAME,LNAME,SID,GENDER,BDAY,EMAIL,PASSWORD,PHONE,ADRESS,CLASS,BUS,MFEE, PHOTO)"
                        + "values(ID_STUDENT.NEXTVAL, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, fname);
                statement.setString(2, mname);
                statement.setString(3, lname);
                statement.setInt(4, Integer.parseInt(txtSID.getText()));
                statement.setString(5, selectbtn.getText());
                statement.setDate(6, Date.valueOf(txfDateBith.getValue()));
                statement.setString(7, email);
                statement.setString(8, password);
                statement.setInt(9, Integer.parseInt(txfPhone.getText()));
                statement.setString(10, adress);
                statement.setString(11, classPrimaryKey);
                statement.setString(12, selectbtnb.getText());
                statement.setInt(13, Integer.parseInt(txtFee.getText()));
                statement.setString(14, null);

                statement.execute();
                showNotifications("Student record", "Student Add Successfully");
                clearFields();  
        
            } 
            else{
                imageInputStream = new FileInputStream(file);
                String absolute = file.getAbsolutePath();
                System.out.println(absolute);

                
              sql = "insert into T_STUDENT (ID_STD, FNAME,MNAME,LNAME,SID,GENDER,BDAY,EMAIL,PASSWORD,PHONE,ADDRESS,CLASSID,BUS,MFEE, PHOTO)"
                        + "values(ID_STUDENT.NEXTVAL, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, fname);
                statement.setString(2, mname);
                statement.setString(3, lname);
                statement.setInt(4, Integer.parseInt(txtSID.getText()));
                statement.setString(5, selectbtn.getText());
                statement.setDate(6, Date.valueOf(txfDateBith.getValue()));
                statement.setString(7, email);
                statement.setString(8, password);
                statement.setInt(9, Integer.parseInt(txfPhone.getText()));
                statement.setString(10, adress);
                statement.setString(11, classPrimaryKey);
                statement.setString(12, selectbtnb.getText());
                statement.setInt(13, Integer.parseInt(txtFee.getText()));
//                statement.setBlob(14, imageInputStream);
                statement.setString(14, absolute);
                System.out.println(imageInputStream);

                
                statement.execute();
                showNotifications("Student record", "Student Add Successfully");
                clearFields();  
                
            }
                
        }
            
    }
    private void clearFields(){
        
        txtFname.setText("");
        txtMname.setText("");
        txfLName.setText("");
        txfPhone.setText("");
        txfEmail.setText("");
        txfAdress.setText("");
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