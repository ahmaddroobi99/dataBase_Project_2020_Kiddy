package Admin.Dashboard;


import OracleConnection.DBConnection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikhil
 */
public class EmailSendController implements Initializable {

    @FXML
    private JFXTextField subjectT;
    @FXML
    private JFXTextField subject;
    @FXML
    private JFXTextArea messageT;
    @FXML
    private JFXTextArea message;
    
    @FXML
    private JFXComboBox<String> comboEmailT;
    @FXML
    private JFXComboBox<String> comboEmail;
    
    ObservableList<String> eMails = FXCollections.observableArrayList();
    ObservableList<String> eMailsT = FXCollections.observableArrayList();
    
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String sql = null;
    PreparedStatement statementT = null;
    ResultSet resultSetT = null;
    String sqlT = null;




    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showEmails();
            showEmailsT();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmailSendController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmailSendController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
    }
    
    private void showEmails() throws ClassNotFoundException, SQLException{
        connection= DBConnection.GetDatabaseConnection();
        sql="select * from T_STUDENT";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();
         eMails.clear();
        while (resultSet.next()) {
            eMails.add(resultSet.getString("EMAIL"));

        }
        comboEmail.getItems().clear();
        comboEmail.setItems(eMails);
    
    }   
    private void showEmailsT() throws ClassNotFoundException, SQLException{
        connection= DBConnection.GetDatabaseConnection();
        sqlT = "select * from T_TEACHER";
        statementT = connection.prepareStatement(sqlT);
        resultSetT = statementT.executeQuery();
         eMailsT.clear();
        while (resultSetT.next()) {
            eMailsT.add(resultSetT.getString("EMAIL"));

        }
        comboEmailT.getItems().clear();
        comboEmailT.setItems(eMailsT);
    
    }   

    @FXML
    private void handleSendButton(ActionEvent event) {
     //  Allow Less Secure App(should be turned on). https://myaccount.google.com/lesssecureapps
        System.out.println(comboEmail.getValue());
        SendEmail("email@gmail.com", "123456789", comboEmail.getValue(), subject.getText() ,message.getText());
    }
    @FXML
    private void handleSendButtonT(ActionEvent event) {
        System.out.println(comboEmailT.getValue());
        SendEmail("email@gmail.com", "123456789", comboEmailT.getValue(), subjectT.getText() ,messageT.getText());
    }
    
    private void SendEmail(String user, String pass, String to, String sub, String msg)
    {
        Properties prop= new Properties();
        
         prop.put("mail.smtp.ssl.trust","smtp.gmail.com");
         prop.put("mail.smtp.auth",true);
         prop.put("mail.smtp.starttls.enable",true);
         prop.put("mail.smtp.host","smtp.gmail.com");
         prop.put("mail.smtp.port","587");
         
         
         Session session= Session.getInstance(prop, new javax.mail.Authenticator()
         {
             @Override
             protected javax.mail.PasswordAuthentication getPasswordAuthentication()
             {
             return new javax.mail.PasswordAuthentication(user, pass);
       
             }
             
         });
         
         try
         {
             Message message1= new MimeMessage(session);
             
             message1.setFrom( new InternetAddress("no-reply@gmail.com"));
             message1.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
             message1.setSubject(sub);
             message1.setText(msg);
             
             Transport.send(message1);
             
              JOptionPane.showMessageDialog(null,"message sent");
         
         }   
         
         catch(Exception e)
         {
          JOptionPane.showMessageDialog(null,e);
         }
      
        
        
    }
    
}
