/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HairSalon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jacko
 */
public class CustomerController implements Initializable{
    
    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXButton calendarBtn;

    @FXML
    private JFXButton customerBtn;

    @FXML
    private JFXButton employeeBtn;

    @FXML
    private JFXButton messageBtn;

    @FXML
    private JFXButton settingsBtn;

    @FXML
    private JFXTreeTableView<Customer> treeView;

    @FXML
    void loadCalendar(MouseEvent event) {

    }
    public static AnchorPane rootP;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rootP = AnchorPane;
        JFXTreeTableColumn<Customer,Number> colID = new JFXTreeTableColumn<>("Customer ID");
        colID.setPrefWidth(100);
        colID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, Number> param) -> param.getValue().getValue().ID);
        
        JFXTreeTableColumn<Customer,String> colFirstName = new JFXTreeTableColumn<>("First Name");
        colFirstName.setPrefWidth(150);
        colFirstName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().FirstName);
        
        JFXTreeTableColumn<Customer,String> colLastName = new JFXTreeTableColumn<>("Last Name");
        colLastName.setPrefWidth(150);
        colLastName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().LastName);
        
        JFXTreeTableColumn<Customer,String> colEmail = new JFXTreeTableColumn<>("Email");
        colEmail.setPrefWidth(250);
        colEmail.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().Email);
        
        JFXTreeTableColumn<Customer,String> colPhone = new JFXTreeTableColumn<>("Phone Number");
        colPhone.setPrefWidth(150);
        colPhone.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().Phone);
        
        JFXTreeTableColumn<Customer,String> colDOB = new JFXTreeTableColumn<>("Date of birth");
        colDOB.setPrefWidth(150);
        colDOB.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().DOB);
        
        JFXTreeTableColumn<Customer,String> colGender = new JFXTreeTableColumn<>("Gender");
        colGender.setPrefWidth(120);
        colGender.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().Gender);
        
   
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        customers.add(new Customer(3, "John", "Smith", "JohnnySmith123@gmail.com", "Qwerty123", "Qwerty123", "09876543211", "30/07/1997", "Male"));
        
        
        
        
        final TreeItem<Customer> root = new RecursiveTreeItem<Customer>(customers, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(colID, colFirstName, colLastName, colEmail, colPhone, colDOB, colGender);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }
}


class Customer extends RecursiveTreeObject<Customer> {
    
    IntegerProperty ID;
    StringProperty FirstName;
    StringProperty LastName;
    StringProperty Email;
    StringProperty Password;
    StringProperty ConfirmPassword;
    StringProperty Phone;
    StringProperty DOB;
    StringProperty Gender;
    
    public Customer(int id, String FirstName, String LastName, String Email, String Password, String ConfirmPassword, String Phone, String DOB, String Gender){
        this.ID = new SimpleIntegerProperty(id);
        this.FirstName = new SimpleStringProperty(FirstName);
        this.LastName = new SimpleStringProperty(LastName);
        this.Email = new SimpleStringProperty(Email);
        this.Password = new SimpleStringProperty(Password);
        this.ConfirmPassword = new SimpleStringProperty(ConfirmPassword);
        this.Phone = new SimpleStringProperty(Phone);
        this.DOB = new SimpleStringProperty(DOB);
        this.Gender = new SimpleStringProperty(Gender);
    }
}
