/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.main;

import com.hairsalon.dataItems.Customer;
import com.hairsalon.dataItems.Employee;
import com.hairsalon.handlers.APIHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Jacko
 */
public class EmployeeController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXButton calendarBtn;

    @FXML
    private JFXButton customerBtn;

    @FXML
    private JFXButton employeeBtn;

    @FXML
    private JFXButton messageBtn;

    @FXML
    private JFXButton settingsBtn;

    @FXML
    private JFXTreeTableView<Employee> treeView;

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
    private JFXButton newEmployeeBtn;

    @FXML
    void addEmployee(ActionEvent event) throws IOException {
        Stage st = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewEmployeeView.fxml"));
        Parent sceneMain = loader.load();
        Scene scene = new Scene(sceneMain);
        st.setScene(scene);
        st.showAndWait();
    }

    @FXML
    void refreshList(ActionEvent event) {
        createEmployeeList();
    }

    public static AnchorPane rootP;
    public static int selectedIndex;
    public TreeItem<Employee> employeeTree;
    public static ObservableList<Employee> employees;
    public JFXTreeTableColumn<Employee, Number> colID = new JFXTreeTableColumn<>("Employee ID");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rootP = AnchorPane;
        createEmployeeList();

        treeView.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) {
                try {
                    Stage st = new Stage();
                    TreeItem<Employee> item = treeView.getSelectionModel().getSelectedItem();
                    selectedIndex = item.getParent().getChildren().indexOf(item);
                    treeView.refresh();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEmployeeView.fxml"));
                    Parent sceneMain = loader.load();
                    EditEmployeeController controller = loader.<EditEmployeeController>getController();
                    controller.setUserData(item);
                    Scene scene = new Scene(sceneMain);
                    st.setScene(scene);
                    st.showAndWait();
                    Comparator<Employee> byID = Comparator.comparing(Employee::getID);
                    System.out.println(selectedIndex);
                    employees.sort(byID);
                    treeView.getSortOrder().add(colID);
                    treeView.getSortOrder().remove(colID);

                } catch (IOException ex) {
                    loadDialog("Failed", "Error connecting to the database, Please click the refresh button or reopen the program");
                }
            }
        });

    }

    public void createEmployeeList() {
        colID.setPrefWidth(121);
        colID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employee, Number> param) -> param.getValue().getValue().employeeID);

        JFXTreeTableColumn<Employee, String> colFirstName = new JFXTreeTableColumn<>("First Name");
        colFirstName.setPrefWidth(275);
        colFirstName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employee, String> param) -> param.getValue().getValue().firstName);

        JFXTreeTableColumn<Employee, String> colLastName = new JFXTreeTableColumn<>("Last Name");
        colLastName.setPrefWidth(275);
        colLastName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employee, String> param) -> param.getValue().getValue().lastName);
        try {
            APIHandler api;
            api = new APIHandler("http://localhost:62975/token/login", "login");
            api.loginAPI();
            api.setUrl("http://localhost:62975/api/employees/get");
            api.setDataBeingPulled("employee");
            api.MakeAPICall();
            employees = FXCollections.observableArrayList();
            api.getDataFromAPI().stream().map((dataItem) -> (Employee) dataItem).forEachOrdered((employee) -> {
                employees.add(new Employee(employee.getID(), employee.getFirstName(), employee.getLastName()));
            });

            employeeTree = new RecursiveTreeItem<>(employees, RecursiveTreeObject::getChildren);
            treeView.getColumns().setAll(colID, colFirstName, colLastName);
            treeView.setRoot(employeeTree);
            treeView.setShowRoot(false);
        } catch (IOException ex) {
            loadDialog("Failed", "Error connecting to the database, Please click the refresh button or reopen the program");
        }
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
