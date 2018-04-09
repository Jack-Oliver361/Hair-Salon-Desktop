package com.hairsalon.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hairsalon.dataItems.Employee;
import com.hairsalon.dataItems.Login;
import com.hairsalon.dataItems.Service;
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

public class EditServiceController implements Initializable{

    @FXML
    private AnchorPane AnchorPane;

    
    @FXML
    private JFXTextField ServiceIDtxt;

    @FXML
    private JFXTextField nametxt;

    @FXML
    private JFXTextField durationtxt;

    @FXML
    private JFXTextField pricetxt;

    @FXML
    void deleteService(ActionEvent event) throws IOException {
        APIHandler api = new APIHandler("http://localhost:62975/token/login", "login");
        api.loginAPI();
        Login login = api.getLoginData();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpDelete apiput = new HttpDelete("http://localhost:62975/api/services/" + ServiceIDtxt.getText());
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
        Service service = new Service(parseInt(ServiceIDtxt.getText()), nametxt.getText(), durationtxt.getText(), pricetxt.getText());
        edit = service;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
           final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
            gsonBuilder.registerTypeHierarchyAdapter(IntegerProperty.class, new IntegerPropertyAdapter());
            final Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(service);
            System.out.println(json);
            StringEntity requestEntity = new StringEntity(
                    json,
                    ContentType.APPLICATION_JSON);
            
            HttpPut apiput = new HttpPut("http://localhost:62975/api/services");
            apiput.addHeader("content-type", "application/json");
            apiput.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
            apiput.setEntity(requestEntity);

            HttpResponse apiresult = httpClient.execute(apiput);
            System.out.println(apiresult.getStatusLine()); 
            ServiceController.services.set(ServiceController.selectedIndex, edit);
            Stage stage = (Stage) rootP.getScene().getWindow();
            stage.close();

        }    
    }
    
    
    
    
    public static AnchorPane rootP;
    public static Service edit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rootP = AnchorPane;

       
        
    }

    void setUserData(TreeItem<Service> item) {
        Service service = item.getValue();
        ServiceIDtxt.setText(service.getID().toString());
        nametxt.setText(service.getName());
        durationtxt.setText(service.getDuration());
        pricetxt.setText(service.getPrice());
    }
    
    public Service getService() {
        return edit;
    }
}
