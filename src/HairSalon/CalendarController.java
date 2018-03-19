package HairSalon;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CalendarController implements Initializable{

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
    void loadCalendar(MouseEvent event) {

    }
    public static AnchorPane rootP;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rootP = AnchorPane;
        
    }
}
