package GUI;

import GUI.DialogBox.CustomDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static Database.condb.showBranchTT;

public class showController {

    @FXML
    private AnchorPane mainFrame;

    @FXML
    private TextField tf11;

    @FXML
    private TextField tf21;

    @FXML
    private TextField tf41;

    @FXML
    private TextField tf51;

    @FXML
    private TextField tf12;

    @FXML
    private TextField tf22;

    @FXML
    private TextField tf32;

    @FXML
    private TextField tf42;

    @FXML
    private TextField tf31;

    @FXML
    private TextField tf52;

    @FXML
    private TextField tf13;

    @FXML
    private TextField tf23;

    @FXML
    private TextField tf33;

    @FXML
    private TextField tf43;

    @FXML
    private TextField tf53;

    @FXML
    private TextField tf14;

    @FXML
    private TextField tf24;

    @FXML
    private TextField tf34;

    @FXML
    private TextField tf44;

    @FXML
    private TextField tf54;

    @FXML
    private TextField tf15;

    @FXML
    private TextField tf25;

    @FXML
    private TextField tf17;

    @FXML
    private TextField tf16;

    @FXML
    private TextField tf26;

    @FXML
    private TextField tf27;

    @FXML
    private TextField tf35;

    @FXML
    private TextField tf36;

    @FXML
    private TextField tf37;

    @FXML
    private TextField tf45;

    @FXML
    private TextField tf46;

    @FXML
    private TextField tf47;

    @FXML
    private TextField tf55;

    @FXML
    private TextField tf56;

    @FXML
    private TextField tf57;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        mainFrame.getChildren().setAll(root);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        String branch = CustomDialog.getMsg();

        String ttgs[][] = showBranchTT(branch);

        tf11.setText(ttgs[0][0]);
        tf12.setText(ttgs[0][1]);
        tf13.setText(ttgs[0][2]);
        tf14.setText(ttgs[0][3]);
        tf15.setText(ttgs[0][4]);
        tf16.setText(ttgs[0][5]);
        tf17.setText(ttgs[0][6]);

        tf21.setText(ttgs[1][0]);
        tf22.setText(ttgs[1][1]);
        tf23.setText(ttgs[1][2]);
        tf24.setText(ttgs[1][3]);
        tf25.setText(ttgs[1][4]);
        tf26.setText(ttgs[1][5]);
        tf27.setText(ttgs[1][6]);

        tf31.setText(ttgs[2][0]);
        tf32.setText(ttgs[2][1]);
        tf33.setText(ttgs[2][2]);
        tf34.setText(ttgs[2][3]);
        tf35.setText(ttgs[2][4]);
        tf36.setText(ttgs[2][5]);
        tf37.setText(ttgs[2][6]);

        tf41.setText(ttgs[3][0]);
        tf42.setText(ttgs[3][1]);
        tf43.setText(ttgs[3][2]);
        tf44.setText(ttgs[3][3]);
        tf45.setText(ttgs[3][4]);
        tf46.setText(ttgs[3][5]);
        tf47.setText(ttgs[3][6]);

        tf51.setText(ttgs[4][0]);
        tf52.setText(ttgs[4][1]);
        tf53.setText(ttgs[4][2]);
        tf54.setText(ttgs[4][3]);
        tf55.setText(ttgs[4][4]);
        tf56.setText(ttgs[4][5]);
        tf57.setText(ttgs[4][6]);
    }
}
