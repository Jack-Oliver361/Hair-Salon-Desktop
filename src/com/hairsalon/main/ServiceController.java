/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.main;


import com.hairsalon.dataItems.Customer;
import com.hairsalon.dataItems.Employee;
import com.hairsalon.dataItems.Service;
import com.hairsalon.handlers.APIHandler;
import static com.hairsalon.main.EmployeeController.employees;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jacko
 */
public class ServiceController implements Initializable{
    
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
    private JFXTreeTableView<Service> treeView;

    @FXML
    void loadCalendar(ActionEvent event) throws IOException {
        Parent page_parent = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
        Scene page_scene = new Scene(page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(page_scene);
        app_stage.show();
    }

    @FXML
    void loadCustomers(ActionEvent event) throws IOException {
        Parent page_parent = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        Scene page_scene = new Scene(page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(page_scene);
        app_stage.show();
    }

    @FXML
    void loadEmployees(ActionEvent event) throws IOException {
        Parent page_parent = FXMLLoader.load(getClass().getResource("EmployeeView.fxml"));
        Scene page_scene = new Scene(page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(page_scene);
        app_stage.show();
    }
    @FXML
    void loadServices(ActionEvent event) throws IOException {
        
        Parent page_parent = FXMLLoader.load(getClass().getResource("ServiceView.fxml"));
        Scene page_scene = new Scene(page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(page_scene);
        app_stage.show();
    }

    @FXML
    void loadMessages(ActionEvent event) throws IOException {
        Parent page_parent = FXMLLoader.load(getClass().getResource("MessageView.fxml"));
        Scene page_scene = new Scene(page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(page_scene);
        app_stage.show();
    }

    @FXML
    void loadSettings(ActionEvent event) throws IOException {
        Parent page_parent = FXMLLoader.load(getClass().getResource("SettingsView.fxml"));
        Scene page_scene = new Scene(page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(page_scene);
        app_stage.show();
    }
    
    @FXML
    private JFXButton newEmployeeBtn;

    @FXML
    void addService(ActionEvent event) throws IOException {
        Stage st = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewServiceView.fxml"));
        Parent sceneMain = loader.load();
        Scene scene = new Scene(sceneMain);
        st.setScene(scene);
        st.showAndWait();
    }


    public static AnchorPane rootP;
    public static int selectedIndex;
    public TreeItem<Service> serviceTree;
    public static ObservableList<Service> services;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rootP = AnchorPane;
        calendarBtn.getStyleClass().removeAll("button, focused"); 
        
        JFXTreeTableColumn<Service,Number> colID = new JFXTreeTableColumn<>("Service ID");
        colID.setPrefWidth(100);
        colID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Service, Number> param) -> param.getValue().getValue().serviceID);
        
        JFXTreeTableColumn<Service,String> colName = new JFXTreeTableColumn<>("Name");
        colName.setPrefWidth(150);
        colName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Service, String> param) -> param.getValue().getValue().name);
        
        JFXTreeTableColumn<Service,String> colDuration = new JFXTreeTableColumn<>("Duration");
        colDuration.setPrefWidth(150);
        colDuration.setCellValueFactory((TreeTableColumn.CellDataFeatures<Service, String> param) -> param.getValue().getValue().duration);
        
        JFXTreeTableColumn<Service,String> colPrice = new JFXTreeTableColumn<>("Price");
        colPrice.setPrefWidth(150);
        colPrice.setCellValueFactory((TreeTableColumn.CellDataFeatures<Service, String> param) -> param.getValue().getValue().price);
        
        APIHandler api;
        api = new APIHandler("http://localhost:62975/token/login", "login");
        try {
            api.loginAPI();
            
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        api.setUrl("http://localhost:62975/api/services");
        api.setDataBeingPulled("service");
        api.MakeAPICall();
        services = FXCollections.observableArrayList();
        api.getDataFromAPI().stream().map((dataItem) -> (Service) dataItem).forEachOrdered((service) -> {
            services.add(new Service(service.getID(), service.getName(), service.getDuration(), service.getPrice()));
        });   
        
         
        serviceTree = new RecursiveTreeItem<>(services, RecursiveTreeObject::getChildren); 
        treeView.getColumns().setAll(colID, colName, colDuration, colPrice);
        treeView.setRoot(serviceTree);
        treeView.setShowRoot(false);
        
        treeView.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if(mouseEvent.getClickCount() == 2)
            {
                try {
                   Stage st = new Stage();
                   TreeItem<Service> item = treeView.getSelectionModel().getSelectedItem();
                   selectedIndex = item.getParent().getChildren().indexOf(item);
                   treeView.refresh();
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("EditServiceView.fxml"));
                   Parent sceneMain = loader.load();
                   EditServiceController controller = loader.<EditServiceController>getController();
                   controller.setUserData(item);
                   Scene scene = new Scene(sceneMain);
                   st.setScene(scene);
                   st.showAndWait();
                   Comparator<Service> byID = Comparator.comparing(Service::getID);
                   System.out.println(selectedIndex);
                   services.sort(byID);
                   treeView.getSortOrder().add(colID);
                   treeView.getSortOrder().remove(colID);
                   
                   
                } catch (IOException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
}



