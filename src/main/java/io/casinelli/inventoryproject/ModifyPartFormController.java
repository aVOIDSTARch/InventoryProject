package io.casinelli.inventoryproject;

import io.casinelli.inventoryproject.Model.InHouse;
import io.casinelli.inventoryproject.Model.Inventory;
import io.casinelli.inventoryproject.Model.Outsourced;
import io.casinelli.inventoryproject.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormController implements Initializable {
    //class variables
    Stage thisStage;
    Parent scene;
    Part selectedPart;

    //Variable to pass index of object to modify
    private int index;
    //Index getter
    public int getIndex() {
        return index;
    }
    //Index setter
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Initializes the text fields using the part stared in the MainForm static field
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Assign selected part
        selectedPart = MainFormController.getSelectedPart();
        //Populate text fields from selected part
        tfPartModID.setText(String.valueOf(selectedPart.getId()));
        tfPartModName.setText(selectedPart.getName());
        tfPartModInv.setText(String.valueOf(selectedPart.getStock()));
        tfPartModMax.setText(String.valueOf(selectedPart.getMax()));
        tfPartModMin.setText(String.valueOf(selectedPart.getMin()));
        tfPartModPrice.setText(String.valueOf(selectedPart.getPrice()));
        if (selectedPart instanceof InHouse) {
            rdoBtnInHouse.selectedProperty().set(true);
            lblPartModSwitch.setText("Machine ID");
            tfPartModSwitch.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else if ( selectedPart instanceof Outsourced){
            rdoBtnOutSourced.selectedProperty().set(true);
            lblPartModSwitch.setText("Company Name");
            tfPartModSwitch.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }

    //Buttons
    @FXML
    private Button btnCancelPartMod;
    @FXML
    private Button btnSaveModPart;
    //Text Fields
    @FXML
    private TextField tfPartModPrice;
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
    //Radio Button and Toggle Groups
    @FXML
    private RadioButton rdoBtnOutSourced;
    @FXML
    private ToggleGroup rdoGrpModPart;
    @FXML
    private RadioButton rdoBtnInHouse;
    //Label that changes with radio button selection
    @FXML
    private Label lblPartModSwitch;


    /**
     * Loads teh MainForm scene
     * @param actionEvent cancel button pushed
     * @throws IOException when the FXMLLoader object's load() method fails to load teh next scene
     */
    @FXML
    private void cancelPartMod(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Replaces the part that has the ID matching the originally loaded part
     * @param actionEvent save button clicked
     */
    @FXML
    private void saveModPart(ActionEvent actionEvent) {
        //get index of part here
        Part replacementPart = validateAndBuildPartToAdd();
        int index = -1;
        if (replacementPart != null) {
            for (Part part : Inventory.getAllParts()) {
                index++;
                if (part.getId() == replacementPart.getId()) {
                    Inventory.getAllParts().set(index, replacementPart);
                    //switch back to MainForm scene after successfully modifying part
                    try {
                        cancelPartMod(actionEvent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                ;
            }
        }
    }

    /**
     * Handles switching the user interface items when the radio button is changed
     * @param actionEvent radio button toggled
     */
    @FXML
    private void switchLabelText(ActionEvent actionEvent) {
        if (rdoBtnInHouse.isSelected()) {
            lblPartModSwitch.setText("Machine ID");
        }
        else if (rdoBtnOutSourced.isSelected())
            lblPartModSwitch.setText("Company Name");
    }


    /**
     * validates user input and creates a new part instance to replace the existing one
     * @return Part to replace the existing one in the inventory ArrayList
     */
    private Part validateAndBuildPartToAdd() {
        //ID does not need inout validation as it is not input by user
        if (Integer.parseInt(tfPartModMin.getText()) >
                Integer.parseInt(tfPartModMax.getText()) ){
            //alertUser
            showAlertDialog(1);
            return null;
        }
        int id = Integer.parseInt(tfPartModID.getText());
        String name = tfPartModName.getText();
        double price = 0.0;
        int inv =0;
        int min =0;
        int max = 0;
        String radio = tfPartModSwitch.getText();
        //Catch invalid numerical input by user
        try {
            price = Double.parseDouble(tfPartModPrice.getText());
            Integer.parseInt(tfPartModMin.getText());
            Integer.parseInt(tfPartModMax.getText());
            inv = Integer.parseInt(tfPartModInv.getText());
        } catch(NumberFormatException nfe) {
            //alertUser
            showAlertDialog(1);
            return null;
        }
        //Use radio button toggle to determine Part subclass
        if (rdoBtnInHouse.isSelected()) {
            try{
                int machineID = Integer.parseInt(radio);
                return new InHouse(id, name, price, inv, min, max, machineID);
            }catch(NumberFormatException nfe) {
                //Alert user of error
                showAlertDialog(1);
                return null;
            }
        }
        else {
            return new Outsourced(id, name, price, inv, min, max, radio);
        }
    }
    //Alert Boxes
    private void showAlertDialog(int alertType) {
        //Create new alert
        Alert anAlert = new Alert(Alert.AlertType.ERROR);
        //Use switch statement to populate dialog box and display
        switch (alertType) {
            case 1:
                anAlert.setTitle("Invalid Input Error");
                anAlert.setHeaderText("Error while attempting to add new part!");
                anAlert.setContentText("Please verify all inputs and resubmit new part.");
                anAlert.showAndWait();
                break;

        }
    }
}
