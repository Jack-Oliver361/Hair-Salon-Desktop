package com.hairsalon.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.hairsalon.dataItems.Customer;
import com.hairsalon.dataItems.Employee;
import com.hairsalon.dataItems.Login;
import com.hairsalon.handlers.APIHandler;
import com.hairsalon.handlers.IntegerPropertyAdapter;
import com.hairsalon.handlers.StringPropertyAdapter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.UnsupportedCharsetException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class NewCustomerController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private StackPane stackPane;

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
    private JFXCheckBox checkBox;

    @FXML
    private Hyperlink policyLink;

    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        if (checkBox.isSelected()) {
            try {
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
                if (dobtxt.getValue() != null) {
                    newCustomer.addProperty("dob", dobtxt.getValue().format(formatter));
                } else {
                    newCustomer.addProperty("dob", "null");
                }
                newCustomer.addProperty("gender", gendertxt.getText());

                try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                    final GsonBuilder gsonBuilder = new GsonBuilder();

                    gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
                    gsonBuilder.registerTypeHierarchyAdapter(IntegerProperty.class, new IntegerPropertyAdapter());
                    final Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
                    String json = gson.toJson(newCustomer);
                    StringEntity requestEntity = new StringEntity(
                            json,
                            ContentType.APPLICATION_JSON);

                    HttpPost apipost = new HttpPost("http://localhost:62975/api/customers/register");
                    apipost.addHeader("content-type", "application/json");
                    apipost.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
                    apipost.setEntity(requestEntity);

                    HttpResponse apiresult = httpClient.execute(apipost);
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
                    } else {
                        Customer customer = gson.fromJson(result, Customer.class);
                        CustomerController.customers.add(new Customer(customer.getID(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword(), customer.getConfirmPassword(), customer.getPhone(), customer.getDOB(), customer.getGender()));
                        Stage stage = (Stage) rootP.getScene().getWindow();
                        stage.close();
                    }
                }
            } catch (JsonSyntaxException | IOException | UnsupportedCharsetException | ParseException ex) {
                loadDialog("Failed", "Error connecting to the database, Please click the refresh button or reopen the program");
            }
        } else {
            loadDialog("Failed", "The client must agree to the privacy policy to sign up");
        }
    }

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static AnchorPane rootP;
    public static Employee edit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rootP = AnchorPane;
        policyLink.setOnAction((ActionEvent e) -> {
            try {
                Stage st = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("privacyPolicyView.fxml"));
                Parent sceneMain = loader.load();
                Scene scene = new Scene(sceneMain);
                st.setScene(scene);
                st.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(NewCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

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
