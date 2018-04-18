/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jacko
 */
public class MessageController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;

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
    void loadServices(ActionEvent event) throws IOException {

        Parent page_parent = FXMLLoader.load(getClass().getResource("ServiceView.fxml"));
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
    void backToCal(ActionEvent event) throws IOException {
        Parent page_parent = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
        Scene page_scene = new Scene(page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(page_scene);
        app_stage.show();
    }

    public static AnchorPane rootP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rootP = AnchorPane;

    }
}
