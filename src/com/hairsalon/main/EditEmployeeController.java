package com.hairsalon.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hairsalon.dataItems.Employee;
import com.hairsalon.dataItems.Login;
import com.hairsalon.handlers.APIHandler;
import com.hairsalon.handlers.IntegerPropertyAdapter;
import com.hairsalon.handlers.StringPropertyAdapter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class EditEmployeeController implements Initializable{

    @FXML
    private StackPane stackPane;
    
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
        TreeItem<Employee> item = new TreeItem<>();
        item.setValue(edit);
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
            String result = EntityUtils.toString(apiresult.getEntity(), "UTF-8");
            if(apiresult.getStatusLine().getStatusCode() == 400){
                List<String> errors = new ArrayList<>();
                JsonParser parser = new JsonParser();
                JsonObject rootObj = parser.parse(result).getAsJsonObject();
                JsonObject obj = rootObj.get("modelState").getAsJsonObject();
                 Set <Map.Entry<String, JsonElement>> entries = obj.entrySet(); // model.Email, model.LastName, etc and their child
                 entries.stream().map((entry) -> entry.getValue().getAsJsonArray()).forEachOrdered((errorModels) -> {
                        int j = 0;
                        for (JsonElement e : errorModels) {
                            errors.add(errorModels.get(j).getAsString());
                            j++;
                   }  
                 });
                 String error = "";
                 for(String e : errors){
                     error = error + e + "\n";
                 }
                 loadDialog("Failed", error);   
                 setUserData(item);
            }else{
            EmployeeController.employees.set(EmployeeController.selectedIndex, edit);
            Stage stage = (Stage) rootP.getScene().getWindow();
            stage.close();
            }

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
        edit = employee;
        EmployeeIDtxt.setText(employee.getID().toString());
        firstNametxt.setText(employee.getFirstName());
        lastNametxt.setText(employee.getLastName());
    }
    
    public Employee getEmployee() {
        return edit;
    }
    
    public void loadDialog(String header, String body){
       
        JFXDialogLayout content = new JFXDialogLayout();
        Text head = new Text(header);
        head.setFont(Font.font("Berlin Sans FB", 20));
        content.setHeading(head);
        Text text = new Text(body);
        text.setFont(Font.font("Century Gothic", 15));
        content.setBody(text);
        JFXDialog dialog = new JFXDialog(stackPane,content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Okay");
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(button);
        dialog.show();
    }
}
