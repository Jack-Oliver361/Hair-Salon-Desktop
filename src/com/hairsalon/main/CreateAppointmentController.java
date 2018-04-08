package com.hairsalon.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hairsalon.dataItems.AvailableTimes;
import com.hairsalon.handlers.APIHandler;
import com.hairsalon.dataItems.Customer;
import com.hairsalon.dataItems.Employee;
import com.hairsalon.dataItems.Service;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class CreateAppointmentController implements Initializable{

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXTreeTableView<Customer> customerTreeView;

    @FXML
    private JFXTreeTableView<Service> serviceTreeView;

    @FXML
    private JFXTreeTableView<Employee> employeeTreeView;

    @FXML
    private JFXTreeTableView<AvailableTimes> timesTreeView;
    
    @FXML
    private JFXDatePicker appointmentDate;

    public static AnchorPane rootP;
    public static Customer selectedCustomer;
    public APIHandler api;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rootP = AnchorPane;
        createCustomerTable();
        createServiceTable();
        createEmployeeTable();
        appointmentDate.valueProperty().addListener((ov, oldValue, newValue) ->{
            
            try {
                createAvailableTimesTable();
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(CreateAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        });

    }

    public void createCustomerTable() {
        
        ObservableList<Customer> customers;
        
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
        
        api = new APIHandler("http://localhost:62975/token/login", "login");
        try {
            api.loginAPI();
            
        } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        api.setUrl("http://localhost:62975/api/customers");
        api.setDataBeingPulled("customer");
        api.MakeAPICall();
        customers = FXCollections.observableArrayList();
         for (Object dataItem : api.getDataFromAPI()) {
            Customer customer = (Customer) dataItem;

                customers.add(new Customer(customer.getID(),customer.getFirstName(),customer.getLastName(),customer.getEmail(),customer.getPassword(),customer.getConfirmPassword(),customer.getPhone(),customer.getDOB(),customer.getGender()));
                
            }   
        
          
        final TreeItem<Customer> root = new RecursiveTreeItem<>(customers, RecursiveTreeObject::getChildren);
        customerTreeView.getColumns().setAll(colID, colFirstName, colLastName, colEmail, colPhone, colDOB, colGender);
        customerTreeView.setRoot(root);
        customerTreeView.setShowRoot(false);
    }

    public void createServiceTable() {
        ObservableList<Service> services;
        
        JFXTreeTableColumn<Service,Number> colID = new JFXTreeTableColumn<>("Service ID");
        colID.setPrefWidth(100);
        colID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Service, Number> param) -> param.getValue().getValue().serviceID);
        
        JFXTreeTableColumn<Service,String> colName = new JFXTreeTableColumn<>("Name");
        colName.setPrefWidth(150);
        colName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Service, String> param) -> param.getValue().getValue().name);
        
        JFXTreeTableColumn<Service,String> colDur = new JFXTreeTableColumn<>("Duration");
        colDur.setPrefWidth(150);
        colDur.setCellValueFactory((TreeTableColumn.CellDataFeatures<Service, String> param) -> param.getValue().getValue().duration);
        
        JFXTreeTableColumn<Service,String> colPrice = new JFXTreeTableColumn<>("Price");
        colPrice.setPrefWidth(250);
        colPrice.setCellValueFactory((TreeTableColumn.CellDataFeatures<Service, String> param) -> param.getValue().getValue().price);
        
        
        api.setUrl("http://localhost:62975/api/services");
        api.setDataBeingPulled("service");
        api.MakeAPICall();
        services = FXCollections.observableArrayList();
         for (Object dataItem : api.getDataFromAPI()) {
            Service service = (Service) dataItem;

                services.add(new Service(service.getID(),service.getName(),service.getDuration(),service.getPrice()));
                
            }   
        
          
        final TreeItem<Service> root = new RecursiveTreeItem<>(services, RecursiveTreeObject::getChildren);
        serviceTreeView.getColumns().setAll(colID, colName, colDur, colPrice);
        serviceTreeView.setRoot(root);
        serviceTreeView.setShowRoot(false);
    }

    public void createEmployeeTable() {
        
        ObservableList<Employee> employees;
        
        JFXTreeTableColumn<Employee,Number> colID = new JFXTreeTableColumn<>("Employee ID");
        colID.setPrefWidth(100);
        colID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employee, Number> param) -> param.getValue().getValue().employeeID);
        
        JFXTreeTableColumn<Employee,String> colFirstName = new JFXTreeTableColumn<>("First Name");
        colFirstName.setPrefWidth(150);
        colFirstName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employee, String> param) -> param.getValue().getValue().firstName);
        
        JFXTreeTableColumn<Employee,String> colLastName = new JFXTreeTableColumn<>("Last Name");
        colLastName.setPrefWidth(150);
        colLastName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employee, String> param) -> param.getValue().getValue().lastName);
        
        
        api.setUrl("http://localhost:62975/api/employees");
        api.setDataBeingPulled("employee");
        api.MakeAPICall();
        employees = FXCollections.observableArrayList();
         for (Object dataItem : api.getDataFromAPI()) {
            Employee employee = (Employee) dataItem;
                employees.add(new Employee(employee.getID(),employee.getFirstName(),employee.getLastName()));
                
            }   
        
         
        final TreeItem<Employee> root = new RecursiveTreeItem<>(employees, RecursiveTreeObject::getChildren); 
        employeeTreeView.getColumns().setAll(colID, colFirstName, colLastName);
        employeeTreeView.setRoot(root);
        employeeTreeView.setShowRoot(false);
    }

    public void createAvailableTimesTable() throws URISyntaxException, IOException {
        
        ObservableList<AvailableTimes> times;
        List<String> availableTimes;
        JFXTreeTableColumn<AvailableTimes,String> colAvailableTimes = new JFXTreeTableColumn<>("Available Time");
        colAvailableTimes.setPrefWidth(100);
        colAvailableTimes.setCellValueFactory((TreeTableColumn.CellDataFeatures<AvailableTimes, String> param) -> param.getValue().getValue().AvailableTime);
        
        Service service = getSelectService();
        Employee employee = getSelectEmployee();
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        String date = appointmentDate.getValue().format(formatter);
        
        
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
           
            
            HttpGet apiget = new HttpGet("http://localhost:62975/api/appointments/availableTimes");
            apiget.addHeader("content-type", "application/json");
            apiget.addHeader("Authorization", api.getLoginData().getToken_type() + " " + api.getLoginData().getAccess_token());
            URI uri = new URIBuilder(apiget.getURI())
               .addParameter("fullName",fullName)
               .addParameter("date", date)
               .addParameter("Service", service.getName())
               .build();
            ((HttpRequestBase) apiget).setURI(uri);
            
            HttpResponse apiresult = httpClient.execute(apiget);
            String json = EntityUtils.toString(apiresult.getEntity(), "UTF-8");
            Type listType = new TypeToken<List<String>>() {}.getType();
            final GsonBuilder gsonBuilder = new GsonBuilder();
            final Gson gson = gsonBuilder.create();
            availableTimes = gson.fromJson(json, listType);
            

        } 
        times = FXCollections.observableArrayList();
        for (String element : availableTimes) {
            times.add(new AvailableTimes(element));
        }
        
       
                
           
        
         
        final TreeItem<AvailableTimes> root = new RecursiveTreeItem<>(times, RecursiveTreeObject::getChildren); 
        timesTreeView.getColumns().setAll(colAvailableTimes);
        timesTreeView.setRoot(root);
        timesTreeView.setShowRoot(false);
        
    }
    
    private Customer getSelectCustomer(){
        TreeItem<Customer> selectedItem = customerTreeView.getSelectionModel().getSelectedItem();
        return selectedItem == null ? null : selectedItem.getValue() ;
    }
    private Service getSelectService(){
        TreeItem<Service> selectedItem = serviceTreeView.getSelectionModel().getSelectedItem();
        return selectedItem == null ? null : selectedItem.getValue() ;
    }
    private Employee getSelectEmployee(){
        TreeItem<Employee> selectedItem = employeeTreeView.getSelectionModel().getSelectedItem();
        return selectedItem == null ? null : selectedItem.getValue() ;
    }
    
}