/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.main;

import com.hairsalon.dataItems.Customer;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CustomerController implements Initializable {

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
    private JFXTreeTableView<Customer> treeView;

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
    private JFXButton newCustomerBtn;

    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        Stage st = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCustomerView.fxml"));
        Parent sceneMain = loader.load();
        Scene scene = new Scene(sceneMain);
        st.setScene(scene);
        st.showAndWait();
    }

    @FXML
    void refreshList(ActionEvent event) {
        createCustomerList();
    }

    public static AnchorPane rootP;
    public static int selectedIndex;
    public static ObservableList<Customer> customers;
    public JFXTreeTableColumn<Customer, Number> colID = new JFXTreeTableColumn<>("Customer ID");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rootP = AnchorPane;
        calendarBtn.getStyleClass().removeAll("button, focused");
        createCustomerList();

        treeView.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) {
                try {
                    Stage st = new Stage();
                    TreeItem<Customer> item = treeView.getSelectionModel().getSelectedItem();
                    selectedIndex = item.getParent().getChildren().indexOf(item);
                    treeView.refresh();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCustomerView.fxml"));
                    Parent sceneMain = loader.load();
                    EditCustomerController controller = loader.<EditCustomerController>getController();
                    controller.setUserData(item);
                    Scene scene = new Scene(sceneMain);
                    st.setScene(scene);
                    st.showAndWait();
                    Comparator<Customer> byID = Comparator.comparing(Customer::getID);
                    customers.sort(byID);
                    treeView.getSortOrder().add(colID);
                    treeView.getSortOrder().remove(colID);

                } catch (IOException ex) {
                    loadDialog("Failed", "Error connecting to the database, Please click the refresh button or reopen the program");
                }
            }
        });
    }

    public void createCustomerList() {
        colID.setPrefWidth(100);
        colID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, Number> param) -> param.getValue().getValue().customerID);

        JFXTreeTableColumn<Customer, String> colFirstName = new JFXTreeTableColumn<>("First Name");
        colFirstName.setPrefWidth(150);
        colFirstName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().firstName);

        JFXTreeTableColumn<Customer, String> colLastName = new JFXTreeTableColumn<>("Last Name");
        colLastName.setPrefWidth(150);
        colLastName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().lastName);

        JFXTreeTableColumn<Customer, String> colEmail = new JFXTreeTableColumn<>("Email");
        colEmail.setPrefWidth(250);
        colEmail.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().email);

        JFXTreeTableColumn<Customer, String> colPhone = new JFXTreeTableColumn<>("Phone Number");
        colPhone.setPrefWidth(150);
        colPhone.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().phone);

        JFXTreeTableColumn<Customer, String> colDOB = new JFXTreeTableColumn<>("Date of birth");
        colDOB.setPrefWidth(150);
        colDOB.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().dob);

        JFXTreeTableColumn<Customer, String> colGender = new JFXTreeTableColumn<>("Gender");
        colGender.setPrefWidth(120);
        colGender.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> param.getValue().getValue().gender);
        try {
            APIHandler api;
            api = new APIHandler("http://localhost:62975/token/login", "login");
            api.loginAPI();

            api.setUrl("http://localhost:62975/api/customers/get");
            api.setDataBeingPulled("customer");
            api.MakeAPICall();
            customers = FXCollections.observableArrayList();
            api.getDataFromAPI().stream().map((dataItem) -> (Customer) dataItem).forEachOrdered((customer) -> {
                customers.add(new Customer(customer.getID(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword(), customer.getConfirmPassword(), customer.getPhone(), customer.getDOB(), customer.getGender()));
            });

            final TreeItem<Customer> root = new RecursiveTreeItem<>(customers, RecursiveTreeObject::getChildren);
            treeView.getColumns().setAll(colID, colFirstName, colLastName, colEmail, colPhone, colDOB, colGender);
            treeView.setRoot(root);
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
