package com.hairsalon.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.hairsalon.dataItems.Customer;
import com.hairsalon.dataItems.Login;
import com.hairsalon.handlers.APIHandler;
import com.hairsalon.handlers.IntegerPropertyAdapter;
import com.hairsalon.handlers.StringPropertyAdapter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.nio.charset.UnsupportedCharsetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class EditCustomerController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXTextField customerIDtxt;

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
    void deleteCustomer(ActionEvent event) throws IOException {
        try {
            APIHandler api = new APIHandler("http://localhost:62975/token/login", "login");
            api.loginAPI();
            Login login = api.getLoginData();
            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

                HttpDelete apiput = new HttpDelete("http://localhost:62975/api/customers/Delete?id=" + customerIDtxt.getText());
                apiput.addHeader("content-type", "application/json");
                apiput.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
                CustomerController.customers.remove(CustomerController.selectedIndex);
                HttpResponse apiresult = httpClient.execute(apiput);
                System.out.println(apiresult.getStatusLine());
                Stage stage = (Stage) rootP.getScene().getWindow();
                stage.close();

            }
        } catch (IOException ex) {
            loadDialog("Failed", "Error connecting to the database, Please click the refresh button or reopen the program");
        }
    }
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    void saveChanges(ActionEvent event) throws IOException {
        TreeItem<Customer> item = new TreeItem<>();
        item.setValue(edit);
        try {
            APIHandler api = new APIHandler("http://localhost:62975/token/login", "login");
            api.loginAPI();
            Login login = api.getLoginData();
            Customer customer = new Customer(parseInt(customerIDtxt.getText()), firstNametxt.getText(), lastNametxt.getText(), emailtxt.getText(), passwordtxt.getText(), confirmPasswordtxt.getText(), phonetxt.getText(), dobtxt.getValue().format(formatter), gendertxt.getText());
            edit = customer;
            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                final GsonBuilder gsonBuilder = new GsonBuilder();

                gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
                gsonBuilder.registerTypeHierarchyAdapter(IntegerProperty.class, new IntegerPropertyAdapter());
                final Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
                String json = gson.toJson(customer);
                System.out.println(json);
                StringEntity requestEntity = new StringEntity(
                        json,
                        ContentType.APPLICATION_JSON);

                HttpPut apiput = new HttpPut("http://localhost:62975/api/customers/update");
                apiput.addHeader("content-type", "application/json");
                apiput.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
                apiput.setEntity(requestEntity);

                HttpResponse apiresult = httpClient.execute(apiput);
                String result = EntityUtils.toString(apiresult.getEntity(), "UTF-8");
                if (apiresult.getStatusLine().getStatusCode() == 400) {
                    List<String> errors = new ArrayList<>();
                    JsonParser parser = new JsonParser();
                    JsonObject rootObj = parser.parse(result).getAsJsonObject();
                    JsonObject obj = rootObj.get("modelState").getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entries = obj.entrySet(); // model.Email, model.LastName, etc and their child
                    entries.stream().map((entry) -> entry.getValue().getAsJsonArray()).forEachOrdered((errorModels) -> {
                        int j = 0;
                        for (JsonElement e : errorModels) {
                            errors.add(errorModels.get(j).getAsString());
                            j++;
                        }
                    });
                    String error = "";
                    for (String e : errors) {
                        error = error + e + "\n";
                    }
                    loadDialog("Failed", error);
                    setUserData(item);
                } else {
                    CustomerController.customers.set(CustomerController.selectedIndex, edit);
                    Stage stage = (Stage) rootP.getScene().getWindow();
                    stage.close();
                }

            }
        } catch (JsonSyntaxException | IOException | NumberFormatException | UnsupportedCharsetException | ParseException ex) {
            loadDialog("Failed", "Error connecting to the database, Please click the refresh button or reopen the program");
        }
    }

    public static AnchorPane rootP;
    public static Customer edit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rootP = AnchorPane;

    }

    void setUserData(TreeItem<Customer> item) {
        Customer customer = item.getValue();
        edit = customer;
        customerIDtxt.setText(customer.getID().toString());
        firstNametxt.setText(customer.getFirstName());
        lastNametxt.setText(customer.getLastName());
        emailtxt.setText(customer.getEmail());
        passwordtxt.setText(customer.getPassword());
        confirmPasswordtxt.setText(customer.getConfirmPassword());
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.UK);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(customer.getDOB(), formatter);
        dobtxt.setValue(date);
        phonetxt.setText(customer.getPhone());
        gendertxt.setText(customer.getGender());
    }

    public Customer getCustomer() {
        return edit;
    }

    public void loadDialog(String header, String body) {

        JFXDialogLayout content = new JFXDialogLayout();
        Text head = new Text(header);
        head.setFont(Font.font("Berlin Sans FB", 20));
        content.setHeading(head);
        Text text = new Text(body);
        text.setFont(Font.font("Century Gothic", 15));
        content.setBody(text);
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Okay");
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(button);
        dialog.show();
    }
}
