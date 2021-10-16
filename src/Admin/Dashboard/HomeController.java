package Admin.Dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javafx.collections.ObservableList;

import Admin.Dashboard.ClassAddViewModal;
import OracleConnection.DBConnection;
import static OracleConnection.DBConnection.GetDatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class HomeController {
    String firstName;
    String lastName;
    String fullName = null;
    String username = "";
    @FXML
    private JFXTextField searchField;
 
    @FXML
    private StackPane mainPane;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXButton refreshBtn;
    private ApplicationContext applicationContext;
    
    @FXML
    private Label totalRegisteredCountLabel;
    @FXML
    private Label totalMalesCountLabel;

    @FXML
    private Label totalFemaleCountLabel;
    @FXML
    private Label teacherCountLabel;
    /////////
    @FXML
    private BarChart<?, ?> chartSTD;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    
   
///////////////
    @FXML
    private TextField txfClassName;
    @FXML    
    private TableView<ClassAddViewModal> table;
    @FXML
    private TableColumn<ClassAddViewModal, Integer> tbClassID;
    @FXML
    private TableColumn<ClassAddViewModal, String> tbClassName;
    @FXML
    private TableColumn<ClassAddViewModal, Void> tbDelete;
    @FXML
    private TextField txfSearchBox;
    
    //************** for database connection***********
    Connection connection = null;
    PreparedStatement statement,statementM, statementF, statementT = null;
    ResultSet resultSet = null;
    String sql,sqlM,sqlF  = null;

    public void initialize() {
        tbClassID.setCellValueFactory(new PropertyValueFactory<>("ClassID"));
        tbClassName.setCellValueFactory(new PropertyValueFactory<>("ClassName"));
        try {
            loadDataIntoTable();
            searchingHandler();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addDeleteToTable(tbDelete,"Delete");
        connection = GetDatabaseConnection();
        ResultSet resultSetM, resultSetF, resultSetT  = null;

        XYChart.Series set1= new XYChart.Series<>();

        sql = "SELECT COUNT(*) AS total FROM T_STUDENT";
        sqlM = "SELECT COUNT(*) AS totalM FROM T_STUDENT  WHERE GENDER = 'Male'";
        sqlF = "SELECT COUNT(*) AS totalF FROM T_STUDENT WHERE GENDER = 'Female'";
        //sqlT = "SELECT COUNT(*) AS totalT FROM T_TEACHER";
        try {
            statement = connection.prepareStatement(sql);
            statementM = connection.prepareStatement(sqlM);
            statementF = connection.prepareStatement(sqlF);
            statementT = connection.prepareStatement("SELECT COUNT(*) AS totalT FROM T_TEACHER");
            
            resultSet = statement.executeQuery();
            resultSet.next();
            totalRegisteredCountLabel.setText(String.valueOf(resultSet.getInt("total"))); 
            resultSet.close() ;
            
            resultSetM = statementM.executeQuery();
            resultSetM.next();
            totalMalesCountLabel.setText(String.valueOf(resultSetM.getInt("totalM")));  
            int mX=resultSetM.getInt("totalM");
            resultSetM.close() ;
            
            resultSetF = statementF.executeQuery();
            resultSetF.next();
            totalFemaleCountLabel.setText(String.valueOf(resultSetF.getInt("totalF")));
            int fX=resultSetF.getInt("totalF");
            resultSetF.close() ;

            resultSetT = statementT.executeQuery();
            resultSetT.next();
            teacherCountLabel.setText(String.valueOf(resultSetT.getInt("totalT")));  
            resultSetT.close() ;

            set1.getData().add(new XYChart.Data("Male students", mX));
            set1.getData().add(new XYChart.Data("Female students", fX));

            

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

            chartSTD.getData().addAll(set1);

        
        

        
    }
    
    
    public void loadDataIntoTable() throws ClassNotFoundException, SQLException {

        ObservableList<ClassAddViewModal> classT = FXCollections.observableArrayList();
        connection = GetDatabaseConnection();

        sql = "SELECT * FROM T_CLASS";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();

        while (resultSet.next()) {

            classT.add(new ClassAddViewModal(resultSet.getInt("ID"), resultSet.getString("NAME")));
            System.out.println(resultSet.getInt("ID"));
            System.out.println(resultSet.getString("NAME"));
            
        }

        table.setItems(classT);

    }
    private void searchingHandler() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<ClassAddViewModal> filteredData = new FilteredList<>(table.getItems(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txfSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super ClassAddViewModal>) student -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (student.getClassName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches ID.

                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<ClassAddViewModal> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);

    }
    static ClassAddViewModal date;

    private void addDeleteToTable(TableColumn column, String name) {

        Callback<TableColumn<ClassAddViewModal, Void>, TableCell<ClassAddViewModal, Void>> cellFactory = new Callback<TableColumn<ClassAddViewModal, Void>, TableCell<ClassAddViewModal, Void>>() {
            @Override
            public TableCell<ClassAddViewModal, Void> call(final TableColumn<ClassAddViewModal, Void> param) {
                final TableCell<ClassAddViewModal, Void> cell = new TableCell<ClassAddViewModal, Void>() {

                    JFXButton btn = new JFXButton(name);

                    {
                        btn.setStyle("-fx-background-color: #e02418;-fx-text-fill:white; ");
                        btn.setOnAction((ActionEvent event) -> {

                            date = getTableView().getItems().get(getIndex());

                            try {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure to delete This Class Room ?");
                                alert.initModality(Modality.APPLICATION_MODAL);
                                Optional<ButtonType> action = alert.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    connection = GetDatabaseConnection();
                                    sql = "delete from T_CLASS where ID=? ";
                                    statement = connection.prepareStatement(sql);
                                    statement.setInt(1, date.getClassID());
                                    statement.execute();
                                    showNotifications(" Class Deletion ", "Class delete sucessfully");
                                    loadDataIntoTable();
                                    searchingHandler();

                                }
                            } catch (Exception ex) {
                                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
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

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        //setGraphic(btn);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        column.setCellFactory(cellFactory);
        table.refresh();

    }
 
    public void onAddDepartment() {
        connection = GetDatabaseConnection();

        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(this.mainPane, content, DialogTransition.RIGHT);
        content.setAlignment(Pos.CENTER);
        content.setHeading(new Node[]{new Text("Add Class")});
        VBox box = new VBox();
        box.setSpacing(15.0D);
        box.setAlignment(Pos.CENTER);
        JFXTextField classNameField = new JFXTextField();
        classNameField.setPromptText("Enter Class Name");
        classNameField.getStyleClass().add("deptField");
        box.getChildren().addAll(new Node[]{classNameField});
        content.setBody(new Node[]{box});
        JFXButton addBtn = new JFXButton("Add");
        JFXButton cancelBtn = new JFXButton("Cancel");
        addBtn.getStyleClass().add("dial-btn");
        cancelBtn.getStyleClass().add("dial-btn");
        System.out.println("test6 ");
              
        addBtn.setOnAction((event) -> {
            if (!classNameField.getText().isEmpty() && !classNameField.getText().trim().isEmpty()) {
                String cname = classNameField.getText();
                System.out.println(cname);
                DBConnection.CheckInsertClass(cname);
                
                dialog.close();                   
                table.refresh();
                            //alert.hideWithAnimation();


            } else {
                                                    
                showNotifications(" Error ", "Class Field cannot be empty");
            }
        });
        cancelBtn.setOnAction((event) -> {
            dialog.close();
        });
        content.setActions(new Node[]{addBtn, cancelBtn});
        dialog.setLayoutX(100.0D);
        dialog.show();
        
        

    }
    private void showNotifications(String title, String text) {
        Notifications.create().title(title).text(text).hideAfter(Duration.seconds(3)).darkStyle()
                              .hideCloseButton()
                              .position(Pos.CENTER)
                              .showConfirm();
    }
    

    
}
