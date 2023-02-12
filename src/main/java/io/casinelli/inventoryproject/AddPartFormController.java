package io.casinelli.inventoryproject;

import io.casinelli.inventoryproject.Model.InHouse;
import io.casinelli.inventoryproject.Model.Inventory;
import io.casinelli.inventoryproject.Model.Outsourced;
import io.casinelli.inventoryproject.Model.Part;
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
    private TextField tfPartAddPrice;
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
        tfPartAddID.setText(String.valueOf(Inventory.getNextPartID()));
        rdoBtnInHouse.selectedProperty().set(true);
        lblPartAddSwitch.setText("Machine ID");
    }

    @FXML
    private void cancelPartAdd(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @FXML
    private void saveAddPart(ActionEvent actionEvent) {
        Part partToAdd = validateAndBuildPartToAdd();
        if (partToAdd != null){
            Inventory.addPart(partToAdd);
        }
        int increaseCount = Inventory.getNextPartID();
        tfPartAddID.setText(String.valueOf(increaseCount));
    }

    @FXML
    private void switchLabelText(ActionEvent actionEvent) {
        if (rdoBtnInHouse.isSelected()) {
            lblPartAddSwitch.setText("Machine ID");
        } else if (rdoBtnOutSourced.isSelected())
            lblPartAddSwitch.setText("Company Name");
    }


    private Part validateAndBuildPartToAdd() {
        //ID does not need inout validation as it is not input by user
        if (Integer.parseInt(tfPartAddMin.getText()) >
                Integer.parseInt(tfPartAddMax.getText()) ){
            //alertUser
            return null;
        }
        int id = Integer.parseInt(tfPartAddID.getText());
        String name = tfPartAddName.getText();
        double price = 0.0;
        int inv =0;
        int min =0;
        int max = 0;
        String radio = tfPartAddSwitch.getText();
        //Catch invalid numerical input by user
        try {
            price = Double.parseDouble(tfPartAddPrice.getText());
            Integer.parseInt(tfPartAddMin.getText());
            Integer.parseInt(tfPartAddMax.getText());
            inv = Integer.parseInt(tfPartAddInv.getText());
        } catch(NumberFormatException nfe) {
            //alertUser
            return null;
        }


        if (rdoBtnInHouse.isSelected()) {
            try{
                int machineID = Integer.parseInt(radio);
                return new InHouse(id, name, price, inv, min, max, machineID);
            }catch(NumberFormatException nfe) {
                //Alert user of error
                return null;
            }
        }
        else {
            return new Outsourced(id, name, price, inv, min, max, radio);
        }
    }
}
