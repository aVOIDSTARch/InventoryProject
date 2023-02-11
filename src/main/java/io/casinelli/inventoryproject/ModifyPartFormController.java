package io.casinelli.inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormController implements Initializable {

    Stage thisStage;
    Parent scene;


    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    @FXML
    private Button btnCancelPartMod;
    @FXML
    private Button btnSaveModPart;
    @FXML
    private TextField tfPartModMin;
    @FXML
    private TextField tfPartModSwitch;
    @FXML
    private TextField tfPartModMax;
    @FXML
    private TextField tfPartModInv;
    @FXML
    private TextField tfPartModName;
    @FXML
    private TextField tfPartModID;
    @FXML
    private RadioButton rdoBtnOutSourced;
    @FXML
    private ToggleGroup rdoGrpModPart;
    @FXML
    private RadioButton rdoBtnInHouse;
    @FXML
    private AnchorPane switchLabelText;
    @FXML
    private Label lblPartModSwitch;



    @FXML
    private void cancelPartMod(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @FXML
    private void saveModPart(ActionEvent actionEvent) {
    }

    @FXML
    private void switchLabelText(ActionEvent actionEvent) {
    }
}
