package GUI;

import GUI.DialogBox.CustomDialog;
import Generators.SchedulerMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static Database.condb.branchname;

public class homeController {

    @FXML
    private AnchorPane mainframe;

    @FXML
    private VBox subjectBox;

    @FXML
    private JFXTextField subname;

    @FXML
    private JFXCheckBox lab;

    @FXML
    private JFXButton subadd;

    @FXML
    private JFXTextField subcode;

    @FXML
    private JFXTextField subminhrs;

    @FXML
    private JFXTextField profname;

    @FXML
    private JFXButton finish;

    @FXML
    private JFXComboBox<String> branch;

    @FXML
    private JFXComboBox<String> classYear;

    @FXML
    private JFXButton classadd;

    @FXML
    private JFXButton generate;

    private int minhrs = 0;

    @FXML
    void finishing(ActionEvent event) {

    }

    StringBuffer inputstring = new StringBuffer();
    int t = 1, g = 1;

    @FXML
    void classadding(ActionEvent event) throws IOException {
        if (branchname(branch.getValue()+"("+classYear.getValue()+")") == 0) {
            inputstring = new StringBuffer();
            inputstring.append("studentgroups\n");
            inputstring.append(branch.getValue());
            inputstring.append("(");
            inputstring.append(classYear.getValue());
            inputstring.append(")");
            subjectBox.setVisible(true);
        }
        else {
            CustomDialog.setMsg("This class has allocated already!");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DialogBox/AddCustomDialog.fxml"));
            Parent parent = fxmlLoader.load();

            Scene scene = new Scene(parent, 200, 100);
            Stage stage = new Stage();
            stage.setTitle("Warning!!");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        }

    }

    @FXML
    void generating(ActionEvent event) throws IOException {
        if (minhrs < 10) {
            CustomDialog.setMsg("Add Minimum Subjects!!!");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DialogBox/AddCustomDialog.fxml"));
            Parent parent = fxmlLoader.load();

            Scene scene = new Scene(parent, 200, 100);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }

        else {
            FileWriter fw = new FileWriter("input.txt");
            PrintWriter pw = new PrintWriter(fw);
            if (g == 1) {
                inputstring.append("\nend");
            }
            pw.print(inputstring);
            pw.close();
            SchedulerMain sc = new SchedulerMain();
            Parent root = FXMLLoader.load(getClass().getResource("result.fxml"));
            mainframe.getChildren().setAll(root);
        }

    }

    @FXML
    void subadding(ActionEvent event) throws IOException {
        minhrs += Integer.parseInt(subminhrs.getText());
        if (lab.isSelected())
            inputstring.append(" l"+subcode.getText()+" "+subminhrs.getText()+" "+profname.getText());
        else
            inputstring.append(" "+subcode.getText()+" "+subminhrs.getText()+" "+profname.getText());


        CustomDialog.setMsg("Subject Successfully Added!!!");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DialogBox/AddCustomDialog.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, 200, 100);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void viewing(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        mainframe.getChildren().setAll(root);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> branchlist = FXCollections.observableArrayList("CSE","CIVIL","EEE","ECE","MECH");
        branch.setItems(branchlist);

        ObservableList<String> year = FXCollections.observableArrayList("I","II","III","IV");
        classYear.setItems(year);
    }
}
