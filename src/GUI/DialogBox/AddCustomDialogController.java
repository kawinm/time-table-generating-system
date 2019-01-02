package GUI.DialogBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AddCustomDialogController {

    @FXML
    private Label message;

    String msg;

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        msg = CustomDialog.getMsg();
        message.setText(msg);
    }

}