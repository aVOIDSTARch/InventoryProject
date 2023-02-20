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

/**
 * Class used to provide a javaFX scene interface to allow parts to
 * be added to the inventory.
 */
public class AddPartFormController implements Initializable {
    /**
     * Initializes the AddPartForm Controller
     * @param url The location used to resolve relative paths for the
     *            root object, or null if the location is not known.
     *            This is not ued in this implementation.
     * @param resourceBundle The resources used to localize the root
     *                       object, or null if the root object was
     *                       not localized. This is not ued in this
     *                       implementation.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfPartAddID.setText(String.valueOf(Inventory.getNextPartID()));
        rdoBtnInHouse.selectedProperty().set(true);
        lblPartAddSwitch.setText("Machine ID");
    }

    //class variables
    Stage thisStage;
    Parent scene;
    //Buttons
    @FXML
    private Button btnCancelPartAdd;
    @FXML
    private Button btnSaveAddPart;
    //Text fields
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
    //Radio buttons
    @FXML
    private RadioButton rdoBtnOutSourced;
    @FXML
    private RadioButton rdoBtnInHouse;
    @FXML
    private ToggleGroup rdoGrpAddPart;
   //variable label
   @FXML
    private Label lblPartAddSwitch;


    /**
     * @param actionEvent cancel button click event
     * @throws IOException error occurs when the FXMLLoader object
     * fails to locate or load teh appropriate FXML document
     */
    @FXML
    private void cancelPartAdd(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Saves new part to inventory ArrayList
     * @param actionEvent save button click event
     */
    @FXML
    private void saveAddPart(ActionEvent actionEvent) throws IOException {
        Part partToAdd = validateAndBuildPartToAdd();
        if (partToAdd != null){
            Inventory.addPart(partToAdd);
            int increaseCount = Inventory.getNextPartID();
            tfPartAddID.setText(String.valueOf(increaseCount));
            //return to main form scene
            cancelPartAdd(actionEvent);
        }

    }

    /**
     * Switches user interface settings when radio buttons are changed
     * @param actionEvent Radio Button toggled
     */
    @FXML
    private void switchLabelText(ActionEvent actionEvent) {
        if (rdoBtnInHouse.isSelected()) {
            lblPartAddSwitch.setText("Machine ID");
        } else if (rdoBtnOutSourced.isSelected())
            lblPartAddSwitch.setText("Company Name");
    }


    /**
     * Validates and instantiates new Part object
     * @return Newly Created Part or null indicating failure
     */
    private Part validateAndBuildPartToAdd() {
        //ID does not need inout validation as it is not input by user
        if (!(Integer.parseInt(tfPartAddMin.getText()) >= Integer.parseInt(tfPartAddInv.getText())) &&
                !(Integer.parseInt(tfPartAddInv.getText()) <= Integer.parseInt(tfPartAddMax.getText()))){
            //alertUser
            showAlertDialog(2);
            return null;
        }
        int id = Integer.parseInt(tfPartAddID.getText());
        String name = tfPartAddName.getText();
        double price;
        int inv;
        int min;
        int max;
        String radio = tfPartAddSwitch.getText();
        //Catch invalid numerical input by user
        try {
            price = Double.parseDouble(tfPartAddPrice.getText());
            min = Integer.parseInt(tfPartAddMin.getText());
            max = Integer.parseInt(tfPartAddMax.getText());
            inv = Integer.parseInt(tfPartAddInv.getText());
        } catch(NumberFormatException nfe) {
            //alertUser
            showAlertDialog(1);
            return null;
        }


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
    //Alert Boxes for Add Part Form
    /**
     * Display appropriate alert message to user contingent upon input
     * @param alertType type of alert message to display
     */
    private void showAlertDialog(int alertType) {
        //Create new alert
        Alert anAlert = new Alert(Alert.AlertType.ERROR);
        //Use switch statement to populate dialog box and display
        switch (alertType) {
            case 1 -> {
                anAlert.setTitle("Invalid Input Error");
                anAlert.setHeaderText("Error while attempting to add new part!");
                anAlert.setContentText("Please verify all inputs and resubmit new part.");
                anAlert.showAndWait();
            }
            case 2 -> {
                anAlert.setTitle("Invalid Inventory Error");
                anAlert.setHeaderText("Error while attempting to add new part!");
                anAlert.setContentText("Please verify all inventory inputs and resubmit new part.");
                anAlert.showAndWait();
            }
        }
    }
}
