package OracleConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

    public static Connection GetDatabaseConnection() {
        Connection connection = null;
        try {
            String driverName = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driverName);
             String serverName = "localhost";
            String serverPort = "1521";
            String sid = "ORCL";

            String url = "jdbc:oracle:thin:@" + serverName + ":" + serverPort + ":" + sid;
            String username = "system";
            String password = "oracle";

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully Connected to the database!");
        }
        
        catch (ClassNotFoundException e) {
            System.out.println("Could not find the database driver " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to the database " + e.getMessage());
        }
        return connection;
    }
            

    public static boolean CheckLoginUser(String uname, String pass) { //get input from login system module
        Connection connection = GetDatabaseConnection();
//        String checkQuery = "select * from registeredUser where user = ' "+uname+" ' and pass = ' "+pass+" ' ";
        String checkQuery = "select * from ADMIN where USERNAME = ? and PASSWORD = ? "; // i don't use id from database table.
        
        PreparedStatement preparedStatement = null;
        boolean status = false; //initially false

        try {
            preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setString(1, uname); // dynamically sending username
            preparedStatement.setString(2, pass); // sending password to checkquery statement
            ResultSet resultSet = preparedStatement.executeQuery();

            /* while (resultSet.next()) {
                return status;
            } */

            status = resultSet.next();
            preparedStatement.close();
            return status;

        } catch (SQLException e) {
//            e.getLocalizedMessage();
            e.printStackTrace();
        }
        return status;
    }
    
    ////
        public static boolean CheckInsertClass(String cname) { //get input from login system module
        Connection connection = GetDatabaseConnection();
        System.out.println("test44");
//        String checkQuery = "select * from registeredUser where user = ' "+uname+" ' and pass = ' "+pass+" ' ";
        // String checkQuery = "select * from ADMIN where USERNAME = ? and PASSWORD = ? "; // i don't use id from database table.
            //String checkQuery = "insert into ClASS (CLASSID, CLASSNAME, TEACHERNAME) values(?,?,?) ";
           String checkQuery = " INSERT INTO T_CLASS (ID, NAME) VALUES (ID_STUDENT.NEXTVAL,?)";
            System.out.println("test4");

        PreparedStatement preparedStatement = null;
        boolean status = false; //initially false

        try {
            preparedStatement = connection.prepareStatement(checkQuery);
           // preparedStatement.setInt(1, id); // dynamically sending username
            preparedStatement.setString(1, cname); // sending password to checkquery statement
         //   preparedStatement.setString(3, tname); // sending password to checkquery statement
            ResultSet resultSet = preparedStatement.executeQuery();

            /* while (resultSet.next()) {
                return status;
            } */

            status = resultSet.next();
            preparedStatement.close();
            return status;

        } catch (SQLException e) {
//            e.getLocalizedMessage();
            e.printStackTrace();
        }
        return status;
    }

}