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
 *
 */
public class AddProductFormController implements Initializable {

    Stage thisStage;
    Parent scene;
    //Observable lists for populating tableviews in scene
    ObservableList<Part> inventoryList = Inventory.getAllParts();
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * @param url The location used to resolve relative paths for the
     *            root object, or null if the location is not known.
     *            This is not used in this implementation.
     * @param resourceBundle The resources used to localize the root
     *                       object, or null if the root object was
     *                       not localized. This is not used in this
     *                       implementation.
dle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize Product Id
        tfAddPartID.setText(String.valueOf(Inventory.getNextProductID()));
        //Initialize Search tableview

        tblvPartsSearch.setItems(inventoryList);
        colPartIDSearch.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartNameSearch.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvSearch.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPriceSearch.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Initialize associated parts tableview
        tblvPartsList.setItems(associatedParts);
        colAssocPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAssocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAssocPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colAssocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    //TableViews
    @FXML
    private TableView tblvPartsSearch;
    @FXML
    private TableView tblvPartsList;
    //Buttons
    @FXML
    private Button btnAddPartToProduct;
    @FXML
    private Button btnRemoveAssociatedPart;
    @FXML
    private Button btnCancelAddProduct;
    @FXML
    private Button btnSaveProduct;
    //Text-field
    @FXML
    private TextField tfPartSearch;
    @FXML
    private TextField tfAddPartID;
    @FXML
    private TextField tfAddPartName;
    @FXML
    private TextField tfAddPartInv;
    @FXML
    private TextField tfAddPartPrice;
    @FXML
    private TextField tfAddPartMax;
    @FXML
    private TextField tfAddPartMin;
    //Columns for associated parts TableVIew
    @FXML
    private TableColumn colAssocPartPrice;
    @FXML
    private TableColumn colAssocPartInv;
    @FXML
    private TableColumn colAssocPartName;
    @FXML
    private TableColumn colAssocPartID;
    //Columns for available parts TableVIew
    @FXML
    private TableColumn colPriceSearch;
    @FXML
    private TableColumn colPartInvSearch;
    @FXML
    private TableColumn colPartNameSearch;
    @FXML
    private TableColumn colPartIDSearch;

    /**
     * Search in available products TableView
     * @param actionEvent text-field changed event
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
     * Add the selected item to the product being constructed.
     * @param actionEvent add button click event
     */
    @FXML
    private void addPartToProduct(ActionEvent actionEvent) {
        if (tblvPartsSearch.getSelectionModel().getSelectedItem() != null) {
            associatedParts.add((Part) tblvPartsSearch.getSelectionModel().getSelectedItem());
        } else {
                showAlertDialog(3);
        }

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
     * Cancel Product construction and return to MainForm scene
     * @param actionEvent cancel button click event
     * @throws IOException error occurs when FXMLLoader object fails to locate
     *                     or load teh appropriate FXML document
     */
    @FXML
    private void cancelAddProduct(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Save newly constructed product to inventory
     * @param actionEvent save button click event
     */
    @FXML
    private void saveProduct(ActionEvent actionEvent) {
        if (!tfAddPartInv.getText().isEmpty() && !tfAddPartName.getText().isEmpty() &&
                !tfAddPartMin.getText().isEmpty() && !tfAddPartMax.getText().isEmpty() &&
                !tfAddPartPrice.getText().isEmpty()){
        Product newProduct = validateAndBuildProductToAdd();
        if (newProduct != null) {
            Inventory.addProduct(newProduct);
            try {
                cancelAddProduct(actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    } else {
            //Alert User
            showAlertDialog(1);
        }
    }

    /**
     * Validates user input and constructs new product object
     * @return newly constructed product object
     */
    private Product validateAndBuildProductToAdd() {
        //ID does not need inout validation as it is not input by user
        if ((Integer.parseInt(tfAddPartMin.getText()) > Integer.parseInt(tfAddPartInv.getText())) ||
                (Integer.parseInt(tfAddPartInv.getText()) > Integer.parseInt(tfAddPartMax.getText())) ||
                (Integer.parseInt(tfAddPartMax.getText()) < Integer.parseInt(tfAddPartMin.getText()))) {
            //alertUser
            showAlertDialog(2);
            return null;
        }
        int id = Integer.parseInt(tfAddPartID.getText());
        String name = tfAddPartName.getText();
        double price = 0.0;
        int inv = 0;
        int min = 0;
        int max = 0;

        //Catch invalid numerical input by user
        try {
            price = Double.parseDouble(tfAddPartPrice.getText());
            inv = Integer.parseInt(tfAddPartInv.getText());
            max = Integer.parseInt(tfAddPartMax.getText());
            min = Integer.parseInt(tfAddPartMin.getText());
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
            case 1:
                anAlert.setTitle("Invalid Input Error");
                anAlert.setHeaderText("Error while attempting to modify product!");
                anAlert.setContentText("Please verify all inputs and resubmit modified product.");
                anAlert.showAndWait();
                break;
            case 2:
                anAlert.setTitle("Invalid Inventory Error");
                anAlert.setHeaderText("Error while attempting to modify product!");
                anAlert.setContentText("Please verify all inventory inputs and resubmit modified product.");
                anAlert.showAndWait();
                break;
            case 3:
                anAlert.setTitle("Invalid Selection Error");
                anAlert.setHeaderText("Error while attempting to modify product!");
                anAlert.setContentText("Please select part to add to product.");
                anAlert.showAndWait();
                break;
            case 4:
                anAlert.setTitle("Invalid Selection Error");
                anAlert.setHeaderText("Error while attempting to modify product!");
                anAlert.setContentText("Please select part to remove from product.");
                anAlert.showAndWait();
                break;
            case 5:
                anAlert.setTitle("Search Error");
                anAlert.setHeaderText("Error while attempting a search!");
                anAlert.setContentText("The search criteria yielded no results.");
                anAlert.showAndWait();
                break;
        }
    }
}
