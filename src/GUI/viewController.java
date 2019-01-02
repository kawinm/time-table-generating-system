package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import GUI.DialogBox.CustomDialog;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import static Database.condb.viewBranchName;

public class viewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainFrame;

    @FXML
    private JFXListView<JFXButton> listview;

    @FXML
    void newing(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        mainFrame.getChildren().setAll(root);
    }

    @FXML
    void initialize() {
        ArrayList<String> s = viewBranchName();
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CustomDialog.setMsg(((JFXButton) event.getSource()).getText());

                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("show.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mainFrame.getChildren().setAll(root);


            }
        };
        try {
            for (int i=0; i< s.size(); i++) {
                String S = s.get(i);
                JFXButton b = new JFXButton(S);

                b.setOnAction(event);
                listview.getItems().add(b);
            }
        }catch (Exception e) {

        }


    }
}