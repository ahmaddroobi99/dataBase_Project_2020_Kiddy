package Admin.Dashboard.Teachers;

import Admin.Dashboard.HomeController;
import Admin.Dashboard.Students.StudentModel;
import OracleConnection.DBConnection;
import static OracleConnection.DBConnection.GetDatabaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javax.imageio.ImageIO;
import splash.SplashViewController;



public class TeachersListViewCell extends ListCell<TeachersModel> {

 @FXML
    private AnchorPane gridPaneTCH;

    @FXML
    private Circle img;

    @FXML
    private Label lblFname;

    @FXML
    private Label lblSalary;

    @FXML
    private Label lblLname;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblSID;

    @FXML
    private Label lblEmailT;

    @FXML
    private Label lblClass;

    @FXML
    private Label lblBdayT;

    
    @FXML
    private Label lblGender;

    @FXML
    private Label label41;

    @FXML
    private Label lblFee;  
   
    @FXML
    private FontAwesomeIconView fxIconGender;
    
     @FXML
    private JFXButton btnDelete;
     
     @FXML
    private JFXButton btnUpdate;

    
    private FXMLLoader mLLoader;
    Connection connection = null;
    String sql = null;

    @Override
    protected void updateItem(TeachersModel teacher, boolean empty) {
        super.updateItem(teacher, empty);
        
        if(empty || teacher == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                                System.out.println("test55");

                mLLoader = new FXMLLoader(getClass().getResource("TListCell.fxml"));

                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
           
            int classID = teacher.getClassID();
            System.out.println("The # " + classID);
                        
            connection= DBConnection.GetDatabaseConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM T_CLASS");
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    System.out.println(resultSet.getString(2));

                lblClass.setText(resultSet.getString(2)); //Getting Saved name class
            }

            } catch (SQLException ex) {
                Logger.getLogger(TeachersListViewCell.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            lblSID.setText(String.valueOf(teacher.getSID()));
            lblFname.setText(teacher.getFname());
            lblLname.setText(teacher.getLname());
            lblEmailT.setText(teacher.getEmail());
            lblAddress.setText(teacher.getAddress());
            lblPhone.setText(String.valueOf(teacher.getPhone()));
            lblSalary.setText(String.valueOf(teacher.getSalary()));
            lblBdayT.setText(teacher.getBday());            
            lblGender.setText(teacher.getGender());
            
            String imagePath = teacher.getPhoto();
            
        //    imagePath = "C:\\Users\\kkhad\\Desktop\\Emt5q-QXIAgHys0.jpg";
            BufferedImage myPicture = null;
            img.setStroke(Color.CORNSILK);
        
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException ex) {
            Logger.getLogger(TeachersListViewCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = SwingFXUtils.toFXImage(myPicture, null );
        img.setFill(new ImagePattern(image));
        img.setEffect(new DropShadow(+25d, 0d, 2d, Color.WHITE));
        //////////
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
                PreparedStatement statement = null;

            try {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure to delete This Teacher ?");
                                alert.initModality(Modality.APPLICATION_MODAL);
                                Optional<ButtonType> action = alert.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    connection = GetDatabaseConnection();
                                    System.out.println(teacher.getSID());
                                    sql = "delete from T_TEACHER where SID=? ";
                                    statement = connection.prepareStatement(sql);
                                    statement.setInt(1, teacher.getSID());
                                    statement.execute();
                                    System.out.println("delete sucessfully");
                                //    showNotifications(" Product Deletion ", "Prodcut delete sucessfully");
                                  //  loadDataIntoTable();
                                  //  searchingHandler();

                                }
                            } catch (Exception ex) {
                                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
            
        }
    });
        
//////////
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
                PreparedStatement statement = null;

            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete This Teacher ?");
                alert.initModality(Modality.APPLICATION_MODAL);
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                connection = GetDatabaseConnection();
                System.out.println(teacher.getSID());
                sql = "SELECT * FROM T_TEACHER where SID=? ";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, teacher.getSID());
                statement.execute();
                System.out.println("delete sucessfully");
                   //    showNotifications(" Product Deletion ", "Prodcut delete sucessfully");
                                  //  loadDataIntoTable();
                                  //  searchingHandler();

                                }
                            } catch (Exception ex) {
                                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
            
        }
    });        
        
        


                setText(null);
                setGraphic(gridPaneTCH);
            }
        
        

    }
    
    	

}
