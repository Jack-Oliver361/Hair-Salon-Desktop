package com.hairsalon.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hairsalon.dataItems.Customer;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class NewEmployeeController implements Initializable{

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXTextField firstNametxt;

    @FXML
    private JFXTextField lastNametxt;

    @FXML
    void addEmployee(ActionEvent event) throws IOException {
        APIHandler api = new APIHandler("http://localhost:62975/token/login", "login");
        api.loginAPI();
        Login login = api.getLoginData();
        JsonObject newEmployee = new JsonObject();
        newEmployee.addProperty("firstName", firstNametxt.getText());
        newEmployee.addProperty("lastName", lastNametxt.getText());
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
           final GsonBuilder gsonBuilder = new GsonBuilder();

            gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
            gsonBuilder.registerTypeHierarchyAdapter(IntegerProperty.class, new IntegerPropertyAdapter());
            final Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(newEmployee);
            System.out.println(json);
            StringEntity requestEntity = new StringEntity(
                    json,
                    ContentType.APPLICATION_JSON);
            
            HttpPost apipost = new HttpPost("http://localhost:62975/api/employees");
            apipost.addHeader("content-type", "application/json");
            apipost.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
            apipost.setEntity(requestEntity);

            HttpResponse apiresult = httpClient.execute(apipost);
            String result = EntityUtils.toString(apiresult.getEntity(), "UTF-8");
            Employee employee = gson.fromJson(result, Employee.class);
            System.out.println(employee.getFirstName());
            EmployeeController.employees.add(new Employee(employee.getID(), employee.getFirstName(), employee.getLastName()));
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

}
