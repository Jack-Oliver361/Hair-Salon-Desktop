package HairSalon;

import com.jfoenix.controls.JFXButton;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class CalendarController implements Initializable {

   
    
     @FXML
    private AnchorPane AnchorPane;
      
     @FXML
    private JFXButton calendarBtn;
     
    @FXML
    void loadCalendar(MouseEvent event) {
        calendarBtn.getStyleClass().add("button:selected");
    }

    public static AnchorPane rootP;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rootP = AnchorPane;
        
        
    }

}
