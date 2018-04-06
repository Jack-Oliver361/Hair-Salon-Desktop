package com.hairsalon.main;

import com.hairsalon.dataItems.ServiceProvided;
import com.hairsalon.handlers.APIHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CalendarController implements Initializable{

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXButton calendarBtn;

   @FXML
    private JFXTreeTableView<ServiceProvided> treeView;
   
   @FXML
    private JFXDatePicker dayOfAppointment;

    @FXML
    private JFXButton perviousDayBtn;

    @FXML
    private JFXButton nextDayBtn;

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
    void nextDay(ActionEvent event) throws IOException {
        dayOfAppointment.setValue(dayOfAppointment.getValue().plusDays(1));
        getAppointments();
    }

    @FXML
    void perviousDay(ActionEvent event) throws IOException {
        dayOfAppointment.setValue(dayOfAppointment.getValue().minusDays(1));
        getAppointments();
    }

    
    
    public static AnchorPane rootP;
    public static ObservableList<ServiceProvided> appointments;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() 
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                
                super.updateItem(item, empty);
                
               
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
                {
                    setDisabled(true);
                    
                }
            }
        };
        dayOfAppointment.setValue(LocalDate.now().minusDays(12));
        dayOfAppointment.setDayCellFactory(dayCellFactory);
        
        JFXTreeTableColumn<ServiceProvided,Number> colAppointmentID = new JFXTreeTableColumn<>("Appointment ID");
        colAppointmentID.setPrefWidth(100);
        colAppointmentID.setCellValueFactory((TreeTableColumn.CellDataFeatures<ServiceProvided, Number> param) -> param.getValue().getValue().appointmentID);
        
        JFXTreeTableColumn<ServiceProvided,String> colDate = new JFXTreeTableColumn<>("Date");
        colDate.setPrefWidth(150);
        colDate.setCellValueFactory((TreeTableColumn.CellDataFeatures<ServiceProvided, String> param) -> param.getValue().getValue().getAppointment().date);
        
        JFXTreeTableColumn<ServiceProvided,String> colTime = new JFXTreeTableColumn<>("Time");
        colTime.setPrefWidth(150);
        colTime.setCellValueFactory((TreeTableColumn.CellDataFeatures<ServiceProvided, String> param) -> param.getValue().getValue().getAppointment().time);
        
        JFXTreeTableColumn<ServiceProvided,String> colCFullName = new JFXTreeTableColumn<>("Customer Full Name");
        colCFullName.setPrefWidth(150);
        colCFullName.setCellValueFactory((TreeTableColumn.CellDataFeatures<ServiceProvided, String> param) -> Bindings.concat(param.getValue().getValue().getAppointment().cFirstName, " ", param.getValue().getValue().getAppointment().cLastName));
        
        JFXTreeTableColumn<ServiceProvided,String> colEmployeeName = new JFXTreeTableColumn<>("Employee Name");
        colEmployeeName.setPrefWidth(150);
        colEmployeeName.setCellValueFactory((TreeTableColumn.CellDataFeatures<ServiceProvided, String> param) -> Bindings.concat(param.getValue().getValue().getEmployee().firstName, " ", param.getValue().getValue().getEmployee().lastName));
        
        
        try {
            getAppointments();
        } catch (IOException ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        treeView.getColumns().setAll(colAppointmentID, colDate, colTime, colCFullName, colEmployeeName);
        
    }
    public void getAppointments() throws IOException{
      APIHandler api;
        api = new APIHandler("http://localhost:62975/token/login", "login");
        try {
            api.loginAPI();
            
        } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        formatter = formatter.withLocale(Locale.UK);
        System.out.println();
        api.setUrl("http://localhost:62975/api/servicesProvided/dayAppointment/" + dayOfAppointment.getValue().format(formatter));
        api.setDataBeingPulled("appointment");
        api.MakeAPICall();
        appointments = FXCollections.observableArrayList();
        int i = 0;
         for (Object dataItem : api.getDataFromAPI()) {
             
            ServiceProvided appointment = (ServiceProvided) dataItem;
                appointments.add(new ServiceProvided(appointment.getAppointmentID(),appointment.getEmployeeID(),appointment.getNumberOfService(),appointment.getServiceID(), appointment.getAppointment(),appointment.getService(), appointment.getEmployee()));
                System.out.println(appointments.get(i).appointmentID);
                i++;
            }   
        
         final TreeItem<ServiceProvided> root = new RecursiveTreeItem<>(appointments, RecursiveTreeObject::getChildren);
        
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }
}
