package io.casinelli.inventoryproject;

import io.casinelli.inventoryproject.Model.Inventory;
import io.casinelli.inventoryproject.Model.Part;
import io.casinelli.inventoryproject.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class used to implement user interface to modify existing product in inventory
 */
public class ModifyProductFormController implements Initializable {
    Stage thisStage;
    Parent scene;

    ObservableList<Part> inventoryList = Inventory.getAllParts();
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Product thisProduct;


    /**
     * Initializes both tableviews in the modify part scene
     * @param url The location used to resolve relative paths for the
     *            root object, or null if the location is not known.
     *            This is not used in this implementation.
     * @param resourceBundle The resources used to localize the root
     *                       object, or null if the root object was
     *                       not localized. This is not ued in this
     *                       implementation.

     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //import selected product
        thisProduct = MainFormController.getSelectedProduct();
        associatedParts = thisProduct.getAssociatedParts();
        //Populate text-fields with product information
        tfModPartID.setText(String.valueOf(thisProduct.getId()));
        tfModPartName.setText(thisProduct.getName());
        tfModPartInv.setText(String.valueOf(thisProduct.getStock()));
        tfModPartPrice.setText(String.valueOf(thisProduct.getPrice()));
        tfModPartMin.setText(String.valueOf(thisProduct.getMin()));
        tfModPartMax.setText(String.valueOf(thisProduct.getMax()));
        //Initialize Search tableview
        tblvPartsSearch.setItems(inventoryList);
        colModPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colModPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Initialize associated parts tableview
        tblvPartsList.setItems(associatedParts);
        colModAssocPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModAssocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModAssocPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colModAssocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    //TableViews
    @FXML
    private TableView<Part> tblvPartsSearch;
    @FXML
    private TableView<Part> tblvPartsList;
    @FXML
    //Buttons
    private Button btnAddPartToProduct;
    @FXML
    private Button btnRemoveAssociatedPart;
    @FXML
    private Button btnCancelModify;
    @FXML
    private Button btnSaveUpdatedProduct;
    //TextFields
    @FXML
    private TextField tfModPartID;
    @FXML
    private TextField tfModPartName;
    @FXML
    private TextField tfModPartInv;
    @FXML
    private TextField tfModPartPrice;
    @FXML
    private TextField tfModPartMax;
    @FXML
    private TextField tfModPartMin;
    @FXML
    private TextField tfPartSearch;
    //Associated Parts table columns
    @FXML
    private TableColumn<Object, Object> colModAssocPartPrice;
    @FXML
    private TableColumn<Object, Object> colModAssocPartInv;
    @FXML
    private TableColumn<Object, Object> colModAssocPartName;
    @FXML
    private TableColumn<Object, Object> colModAssocPartID;
    //Available Parts list columns
    @FXML
    private TableColumn<Object, Object> colModPartPrice;
    @FXML
    private TableColumn<Object, Object> colModPartInv;
    @FXML
    private TableColumn<Object, Object> colModPartName;
    @FXML
    private TableColumn<Object, Object> colModPartID;

    /**
     * Search the available parts list by ID or name
     * <p>
     * FUTURE ENHANCEMENT - search could be enhanced to search more data fields
     *
     * @param actionEvent search button clicked event
     */
    @FXML
    private void searchPartsInProduct(ActionEvent actionEvent) {
        ObservableList<Part> resultsList = FXCollections.observableArrayList();
        String searchText = tfPartSearch.getText();
        //Add all matches of the string and name
        for(Part part : Inventory.getAllParts()){
            if (part.getName().contains(searchText) ) {
                resultsList.add(part);
            }
        }
        //Add all matches of a number and an ID that are not already in the list
        for (Part part : Inventory.getAllParts()) {
           if (String.valueOf(part.getId()).contains(searchText)) {
                resultsList.add(part);
            }
        }
        //Search returned no results
        if (resultsList.isEmpty()) {
            //AlertUser
            showAlertDialog(5);
            //Assign Empty List to tableView
            tblvPartsSearch.setItems(resultsList);
        }
        //Search returned results
        tblvPartsSearch.setItems(resultsList);

    }

    /**
     * Save revised product and return to Mainform scene
     * @param actionEvent save button clicked event
     */
    @FXML
    private void saveProductModify(ActionEvent actionEvent) throws IOException {
        if (!tfModPartInv.getText().isEmpty() && !tfModPartName.getText().isEmpty() &&
        !tfModPartMin.getText().isEmpty() && !tfModPartMax.getText().isEmpty() &&
        !tfModPartPrice.getText().isEmpty()){
            Product newProduct = validateAndBuildProductToAdd();
            if (newProduct != null) {
                int index = -1;
                for (Product prod : Inventory.getAllProducts()) {
                    index++;
                    if (prod.getId() == newProduct.getId()) {
                        Inventory.getAllProducts().set(index, newProduct);
                        cancelProductModify(actionEvent);
                    }
                }
            }
        }else{
            //alert user
            showAlertDialog(1);
        }

    }

    /**
     * Cancel update and return to the MainForm scene
     * @param actionEvent cancel button clicked event
     * @throws IOException error when load method fails
     */
    @FXML
    private void cancelProductModify(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Remove part associated with product being constructed
     * @param actionEvent remove button click event
     */
    @FXML
    private void removeAssociatedPart(ActionEvent actionEvent) {
        if (tblvPartsList.getSelectionModel().getSelectedItem() != null ) {
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.setTitle("Confirm");
            newAlert.setContentText("Are you sure you want to remove the selected part from the product?");
            Optional<ButtonType> result = newAlert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(tblvPartsList.getSelectionModel().getSelectedItem());
            }
        }else {
            showAlertDialog(4);
        }
    }

    /**
     * Add the selected item to the product being constructed.
     * @param actionEvent add button click event
     */
    @FXML
    private void addPartToProduct(ActionEvent actionEvent) {
        if (tblvPartsSearch.getSelectionModel().getSelectedItem() != null) {
            associatedParts.add(tblvPartsSearch.getSelectionModel().getSelectedItem());
        } else {
            //Alert User to select part
            showAlertDialog(3);
        }
    }

    /**
     * Validates user input and constructs new product object
     * @return newly constructed product object to replace selected object
     */
    private Product validateAndBuildProductToAdd() {
        //ID does not need inout validation as it is not input by user
        if ((Integer.parseInt(tfModPartMin.getText()) > Integer.parseInt(tfModPartInv.getText())) ||
                (Integer.parseInt(tfModPartInv.getText()) > Integer.parseInt(tfModPartMax.getText())) ||
                (Integer.parseInt(tfModPartMax.getText()) < Integer.parseInt(tfModPartMin.getText()))) {
            //alertUser - alert 2
            showAlertDialog(2);
            return null;
        }
        int id = Integer.parseInt(tfModPartID.getText());
        String name = tfModPartName.getText();
        double price;
        int inv;
        int min;
        int max;

        //Catch invalid numerical input by user
        try {
            price = Double.parseDouble(tfModPartPrice.getText());
            inv = Integer.parseInt(tfModPartInv.getText());
            max = Integer.parseInt(tfModPartMax.getText());
            min = Integer.parseInt(tfModPartMin.getText());
        } catch (NumberFormatException nfe) {
            //alertUser
            showAlertDialog(1);
            return null;
        }

        return new Product(associatedParts, id, name, price, inv, min, max);
    }

    /**
     * Display appropriate alert message to user contingent upon input
     * @param alertType integer indicating the alert to display
     */
    private void showAlertDialog(int alertType) {
        //Create new alert
        Alert anAlert = new Alert(Alert.AlertType.ERROR);
        //Use switch statement to populate dialog box and display
        switch (alertType) {
            case 1 -> {
                anAlert.setTitle("Invalid Input Error");
                anAlert.setHeaderText("Error while attempting to modify product!");
                anAlert.setContentText("Please verify all inputs and resubmit modified product.");
                anAlert.showAndWait();
            }
            case 2 -> {
                anAlert.setTitle("Invalid Inventory Error");
                anAlert.setHeaderText("Error while attempting to modify product!");
                anAlert.setContentText("Please verify all inventory inputs and resubmit modified product.");
                anAlert.showAndWait();
            }
            case 3 -> {
                anAlert.setTitle("Invalid Selection Error");
                anAlert.setHeaderText("Error while attempting to modify product!");
                anAlert.setContentText("Please select part to add to product.");
                anAlert.showAndWait();
            }
            case 4 -> {
                anAlert.setTitle("Invalid Selection Error");
                anAlert.setHeaderText("Error while attempting to modify product!");
                anAlert.setContentText("Please select part to remove from product.");
                anAlert.showAndWait();
            }
            case 5 -> {
                anAlert.setTitle("Search Error");
                anAlert.setHeaderText("Error while attempting a search!");
                anAlert.setContentText("The search criteria yielded no results.");
                anAlert.showAndWait();
            }
        }
    }
}
