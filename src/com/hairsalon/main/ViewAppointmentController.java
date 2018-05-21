/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.main;

import com.hairsalon.dataItems.Employee;
import com.hairsalon.dataItems.Login;
import com.hairsalon.dataItems.ServiceProvided;
import com.hairsalon.handlers.APIHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Jacko
 */
public class ViewAppointmentController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField customerIDtxt;

    @FXML
    private JFXTextField firstNametxt;

    @FXML
    private JFXTextField lastNametxt;

    @FXML
    private JFXTextField phonetxt;

    @FXML
    private JFXTextField appointmentIDtxt;

    @FXML
    private JFXTextField datetxt;

    @FXML
    private JFXTextField timetxt;

    @FXML
    private JFXTextField serviceIDtxt;

    @FXML
    private JFXTextField serviceNametxt;

    @FXML
    private JFXTextField serviceDurtxt;

    @FXML
    private JFXTextField servicePricetxt;

    @FXML
    void CloseView(ActionEvent event) {
        Stage stage = (Stage) rootP.getScene().getWindow();
        stage.close();
    }

    @FXML
    void CancelAppointment(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel appointment");
        alert.setHeaderText("Cancel Appointment");
        alert.setContentText("Are you sure want to cancel the appointment?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                APIHandler api = new APIHandler("http://localhost:62975/token/login", "login");
                api.loginAPI();
                Login login = api.getLoginData();
                try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

                    HttpDelete apiput = new HttpDelete("http://localhost:62975/api/appointments/Delete?id=" + appointmentIDtxt.getText());
                    apiput.addHeader("content-type", "application/json");
                    apiput.addHeader("Authorization", login.getToken_type() + " " + login.getAccess_token());
                    CalendarController.appointments.remove(CalendarController.selectedIndex);
                    HttpResponse apiresult = httpClient.execute(apiput);

                    Stage stage = (Stage) rootP.getScene().getWindow();
                    stage.close();

                }
            } catch (IOException ex) {
                loadDialog("Failed", "Error connecting to the database, Please click the refresh button or reopen the program");
            }
        } else {

        }
    }

    public static AnchorPane rootP;
    public static Employee edit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rootP = AnchorPane;

    }

    void setUserData(TreeItem<ServiceProvided> item) {
        ServiceProvided appointmentInfo = item.getValue();

        appointmentIDtxt.setText(appointmentInfo.getAppointmentID().toString());
        datetxt.setText(appointmentInfo.getAppointment().getDate());
        timetxt.setText(appointmentInfo.getAppointment().getTime());

        customerIDtxt.setText(appointmentInfo.getAppointment().getCustomerID().toString());
        firstNametxt.setText(appointmentInfo.getAppointment().getCFirstName());
        lastNametxt.setText(appointmentInfo.getAppointment().getCLastName());

        serviceIDtxt.setText(appointmentInfo.getServiceID().toString());
        serviceNametxt.setText(appointmentInfo.getService().getName());
        serviceDurtxt.setText(appointmentInfo.getService().getDuration());
        servicePricetxt.setText(appointmentInfo.getService().getPrice());

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
