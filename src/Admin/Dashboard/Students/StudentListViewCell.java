/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Dashboard.Students;


import OracleConnection.DBConnection;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;
import splash.SplashViewController;



public class StudentListViewCell extends ListCell<StudentModel> {

 @FXML
    private AnchorPane gridPaneSTD;

    @FXML
    private Circle img;

    @FXML
    private Label lblFname;

    @FXML
    private Label lblMname;

    @FXML
    private Label lblLname;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblSID;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblClass;

    @FXML
    private Label lblBday;

    @FXML
    private Label lblBus;

    @FXML
    private Label lblGender;

    @FXML
    private Label label41;

    @FXML
    private Label lblFee;  
   
    @FXML
    private FontAwesomeIconView fxIconGender;

    
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(StudentModel student, boolean empty) {
        super.updateItem(student, empty);
        
        if(empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                                System.out.println("test55");

                mLLoader = new FXMLLoader(getClass().getResource("STDListCell.fxml"));

                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Connection connection = null;
            String sql = null;
            int classID = student.getClassID();
            System.out.println("The # " + classID);
                        
            connection= DBConnection.GetDatabaseConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM T_CLASS");
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                lblClass.setText(resultSet.getString(2)); //Getting Saved name class
            }

            } catch (SQLException ex) {
                Logger.getLogger(StudentListViewCell.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            lblSID.setText(String.valueOf(student.getSID()));
            lblFname.setText(student.getFname());
            lblMname.setText(student.getMname());    
            lblLname.setText(student.getLname());
            lblEmail.setText(student.getEmail());
            lblBus.setText(student.getBus());
            lblAddress.setText(student.getAddress());
            lblPhone.setText(String.valueOf(student.getPhone()));
            lblFee.setText(String.valueOf(student.getMFee()));
            lblGender.setText(student.getGender());
            lblBday.setText(student.getBday());
            
            String imagePath = student.getPhoto();
            
        //    imagePath = "C:\\Users\\kkhad\\Desktop\\Emt5q-QXIAgHys0.jpg";
            BufferedImage myPicture = null;
            img.setStroke(Color.CORNSILK);
        
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException ex) {
            Logger.getLogger(StudentListViewCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = SwingFXUtils.toFXImage(myPicture, null );
        img.setFill(new ImagePattern(image));
        img.setEffect(new DropShadow(+25d, 0d, 2d, Color.WHITE));


                setText(null);
                setGraphic(gridPaneSTD);
            }

    }
    
    	

}
