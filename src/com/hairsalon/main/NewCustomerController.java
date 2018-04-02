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
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class NewCustomerController implements Initializable{

   @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXTextField firstNametxt;

    @FXML
    private JFXTextField lastNametxt;

    @FXML
    private JFXTextField emailtxt;

    @FXML
    private JFXTextField passwordtxt;

    @FXML
    private JFXTextField confirmPasswordtxt;

    @FXML
    private JFXTextField phonetxt;

    @FXML
    private JFXTextField gendertxt;

    @FXML
    private JFXDatePicker dobtxt;


    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        APIHandler api = new APIHandler("http://localhost:62975/token/login", "login");
        api.loginAPI();
        Login login = api.getLoginData();
        JsonObject newCustomer = new JsonObject();
        newCustomer.addProperty("firstName", firstNametxt.getText());
        newCustomer.addProperty("lastName", lastNametxt.getText());
        newCustomer.addProperty("email", emailtxt.getText());
        newCustomer.addProperty("password", passwordtxt.getText());
        newCustomer.addProperty("confirmPassword", confirmPasswordtxt.getText());
        newCustomer.addProperty("phone", phonetxt.getText());
        newCustomer.addProperty("dob", dobtxt.getValue().format(formatter));
        newCustomer.addProperty("gender", gendertxt.getText());
        
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
           final GsonBuilder gsonBuilder = new GsonBuilder();

            gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
            gsonBuilder.registerTypeHierarchyAdapter(IntegerProperty.class, new IntegerPropertyAdapter());
            final Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(newCustomer);
            System.out.println(json);
            StringEntity requestEntity = new StringEntity(
                    json,
                    ContentType.APPLICATION_JSON);
            
            HttpPost apipost = new HttpPost("http://localhost:62975/api/customers/register");
            apipost.addHeader("content-type", "application/json");
            apipost.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
            apipost.setEntity(requestEntity);

            HttpResponse apiresult = httpClient.execute(apipost);
            String result = EntityUtils.toString(apiresult.getEntity(), "UTF-8");
            Customer customer = gson.fromJson(result, Customer.class);
            System.out.println(customer.getFirstName());
            CustomerController.customers.add(new Customer(customer.getID(),customer.getFirstName(),customer.getLastName(),customer.getEmail(),customer.getPassword(),customer.getConfirmPassword(),customer.getPhone(),customer.getDOB(),customer.getGender()));
            Stage stage = (Stage) rootP.getScene().getWindow();
            stage.close();

        }    
    }

    
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static AnchorPane rootP;
    public static Employee edit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rootP = AnchorPane;

       
        
    }

}
