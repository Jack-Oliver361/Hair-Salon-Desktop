package com.hairsalon.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hairsalon.dataItems.Employee;
import com.hairsalon.dataItems.Login;
import com.hairsalon.handlers.APIHandler;
import com.hairsalon.handlers.IntegerPropertyAdapter;
import com.hairsalon.handlers.StringPropertyAdapter;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class EditEmployeeController implements Initializable{

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXTextField EmployeeIDtxt;

    @FXML
    private JFXTextField firstNametxt;

    @FXML
    private JFXTextField lastNametxt;

    @FXML
    void deleteEmployee(ActionEvent event) throws IOException {
 APIHandler api = new APIHandler("http://localhost:62975/token/login", "login");
        api.loginAPI();
        Login login = api.getLoginData();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpDelete apiput = new HttpDelete("http://localhost:62975/api/employees/" + EmployeeIDtxt.getText());
            apiput.addHeader("content-type", "application/json");
            apiput.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
            EmployeeController.employees.remove(EmployeeController.selectedIndex);
            HttpResponse apiresult = httpClient.execute(apiput);
            System.out.println(apiresult.getStatusLine()); 
            Stage stage = (Stage) rootP.getScene().getWindow();
            stage.close();

        }    
    }
    

    @FXML
    void saveChanges(ActionEvent event) throws IOException, InterruptedException {
        APIHandler api = new APIHandler("http://localhost:62975/token/login", "login");
        api.loginAPI();
        Login login = api.getLoginData();
        Employee employee = new Employee(parseInt(EmployeeIDtxt.getText()), firstNametxt.getText(), lastNametxt.getText());
        edit = employee;
        System.out.print(employee.getFirstName());
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
           final GsonBuilder gsonBuilder = new GsonBuilder();

            gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
            gsonBuilder.registerTypeHierarchyAdapter(IntegerProperty.class, new IntegerPropertyAdapter());
            final Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(employee);
            System.out.println(json);
            StringEntity requestEntity = new StringEntity(
                    json,
                    ContentType.APPLICATION_JSON);
            
            HttpPut apiput = new HttpPut("http://localhost:62975/api/employees");
            apiput.addHeader("content-type", "application/json");
            apiput.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
            apiput.setEntity(requestEntity);

            HttpResponse apiresult = httpClient.execute(apiput);
            System.out.println(apiresult.getStatusLine()); 
            EmployeeController.employees.set(EmployeeController.selectedIndex, edit);
            Stage stage = (Stage) rootP.getScene().getWindow();
            stage.close();

        }    
    }
    
    
    
    
    public static AnchorPane rootP;
    public static Employee edit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rootP = AnchorPane;

       
        
    }

    void setUserData(TreeItem<Employee> item) {
        Employee employee = item.getValue();
        EmployeeIDtxt.setText(employee.getID().toString());
        firstNametxt.setText(employee.getFirstName());
        lastNametxt.setText(employee.getLastName());
    }
    
    public Employee getEmployee() {
        return edit;
    }
}
