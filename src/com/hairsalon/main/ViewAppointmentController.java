/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.main;

import com.hairsalon.dataItems.Employee;
import com.hairsalon.dataItems.ServiceProvided;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

}
