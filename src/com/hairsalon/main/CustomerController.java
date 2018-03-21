/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.main;

import com.hairsalon.dataItems.Customer;
import com.hairsalon.handlers.APIHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        colID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, Number> param) -> param.getValue().getValue().customerID);
        
        JFXTreeTableColumn<Customer,String> colFirstName = new JFXTreeTableColumn<>("First Name");
        colFirstName.setPrefWidth(150);
        colFirstName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().firstName);
        
        JFXTreeTableColumn<Customer,String> colLastName = new JFXTreeTableColumn<>("Last Name");
        colLastName.setPrefWidth(150);
        colLastName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().lastName);
        
        JFXTreeTableColumn<Customer,String> colEmail = new JFXTreeTableColumn<>("Email");
        colEmail.setPrefWidth(250);
        colEmail.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().email);
        
        JFXTreeTableColumn<Customer,String> colPhone = new JFXTreeTableColumn<>("Phone Number");
        colPhone.setPrefWidth(150);
        colPhone.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().phone);
        
        JFXTreeTableColumn<Customer,String> colDOB = new JFXTreeTableColumn<>("Date of birth");
        colDOB.setPrefWidth(150);
        colDOB.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().dob);
        
        JFXTreeTableColumn<Customer,String> colGender = new JFXTreeTableColumn<>("Gender");
        colGender.setPrefWidth(120);
        colGender.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().gender);
        
        APIHandler api;
        api = new APIHandler("http://localhost:62975/token/login", "login");
        try {
            api.loginAPI();
            
        } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        api.setUrl("http://localhost:62975/api/customers");
        api.setDataBeingPulled("customer");
        api.MakeAPICall();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
         for (Object dataItem : api.getDataFromAPI()) {
            Customer customer = (Customer) dataItem;
            System.out.print(customer.getFirstName() + " 1");
                customers.add(new Customer(customer.getID(),customer.getFirstName(),customer.getLastName(),customer.getEmail(),customer.getPassword(),customer.getConfirmPassword(),customer.getPhone(),customer.getDOB(),customer.getGender()));
                
            }   
        
          
        final TreeItem<Customer> root = new RecursiveTreeItem<>(customers, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(colID, colFirstName, colLastName, colEmail, colPhone, colDOB, colGender);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }
}



