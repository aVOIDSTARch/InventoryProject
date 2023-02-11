package io.casinelli.inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartFormController implements Initializable {


    Stage thisStage;
    Parent scene;

    @FXML
    private Button btnCancelPartAdd;
    @FXML
    private Button btnSaveAddPart;
    @FXML
    private TextField tfPartAddMin;
    @FXML
    private TextField tfPartAddSwitch;
    @FXML
    private TextField tfPartAddMax;
    @FXML
    private TextField tfPartAddInv;
    @FXML
    private TextField tfPartAddName;
    @FXML
    private TextField tfPartAddID;
    @FXML
    private RadioButton rdoBtnOutSourced;
    @FXML
    private RadioButton rdoBtnInHouse;
    @FXML
    private ToggleGroup rdoGrpAddPart;
    @FXML
    private Label lblPartAddSwitch;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void cancelPartAdd(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @FXML
    private void saveAddPart(ActionEvent actionEvent) {
    }

    @FXML
    private void switchLabelText(ActionEvent actionEvent) {
    }
}
