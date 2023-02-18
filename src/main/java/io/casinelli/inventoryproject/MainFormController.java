package io.casinelli.inventoryproject;

import io.casinelli.inventoryproject.Model.Inventory;
import io.casinelli.inventoryproject.Model.Part;
import io.casinelli.inventoryproject.Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    //Controller instance variables
    Stage thisStage;
    Parent scene;


    /**
     * Part selected in parts tableview
     */
    //Selected Part
    private static Part selectedPart;

    /**
     * Getter function for selected part member field
     * @return part selected in the Parts tableview
     */
    public static Part  getSelectedPart() {
        return selectedPart;
    }

    /**
     * Stores the selected part from the Parts tableview control
     * @param selectedPart Part selected in the Parts tableview control
     */
    public void setSelectedPart(Part selectedPart) {
        this.selectedPart = selectedPart;
    }

    //Selected Product
    /**
     * Product selected in Product tableview
     */
    private static Product selectedProduct;
    /**
     * Getter function for selected product member field
     * @return product selected in the Products tableview
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }
    /**
     * Stores the selected product from the Products tableview control
     * @param selectedProduct Product selected in the Products tableview control
     */
    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }



    /**
     * Initializes the scene interface for MainForm.fxml
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initialize parts tableview and associated columns
        tblvwMainParts.setItems(Inventory.getAllParts());
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Initialize products tableview and associated columns
        tblvwMainProd.setItems(Inventory.getAllProducts());
        colProdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProdInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    //All Buttons
    @FXML
    private Button btnExit;
    @FXML
    private Button btnDeleteProd;
    @FXML
    private Button btnModProd;
    @FXML
    private Button btnAddProd;
    @FXML
    private Button btnDeletePart;
    @FXML
    private Button btnModPart;
    @FXML
    private Button btnAddPart;
    @FXML
    //Search TextFields
    private TextField tfMainProdSearch;
    @FXML
    private TextField tfMainPartSearch;

    //Product TableView and Associated Columns
    @FXML
    private TableView<Product> tblvwMainProd;
    @FXML
    private TableColumn<Product, Double> colProdPrice;
    @FXML
    private TableColumn<Product, Integer> colProdInv;
    @FXML
    private TableColumn<Product, String> colProdName;
    @FXML
    private TableColumn<Product, Integer> colProdID;

    //Parts TableView and Associated Columns
    @FXML
    private TableView<Part> tblvwMainParts;
    @FXML
    private TableColumn<Part, Double> colPartPrice;
    @FXML
    private TableColumn<Part, Integer> colPartInv;
    @FXML
    private TableColumn<Part, String> colPartName;
    @FXML
    private TableColumn<Part, Integer> colPartID;


    /**
     * Exits the program
     * @param actionEvent
     */
    @FXML
    private void exitProgram(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Deletes the selected Product from the inventory
     * @param actionEvent delete button clicked
     */
    @FXML
    private void deleteSelectedProduct(ActionEvent actionEvent) {
        if (tblvwMainProd.getSelectionModel().getSelectedItem().getAssociatedParts().isEmpty()) {
            if (tblvwMainProd.getSelectionModel().getSelectedItem() != null ) {

                Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
                newAlert.setTitle("Confirm");
                newAlert.setContentText("Are you sure you want to delete the selected product?");
                Optional<ButtonType> result = newAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int selectedIndex = tblvwMainProd.getSelectionModel().getSelectedIndex();
                    Inventory.getAllProducts().remove(selectedIndex);
                }
            }else {
                showAlertDialog(2);
            }
        } else {
            //alert parts associated with product
            showAlertDialog(4);
        }

    }

    /**
     * Stores selected part and changes to the ModifyPart scene
     *
     * RUNTIME ERROR - a run time error was corrected in this method.  The error
     * was a NullPointerException, and it was corrected by ensuing the correct
     * path to the FXML document resource was provided to the load method.
     *
     * @param actionEvent Modify button in t parts controls clicked
     * @throws IOException the FXMLLoader load() function may fail
     */
    @FXML
    private void modifySelectedProduct(ActionEvent actionEvent) throws IOException {
            //Set Selected item
            setSelectedProduct(tblvwMainProd.getSelectionModel().getSelectedItem());
            if (getSelectedProduct() != null){
                System.out.println(getSelectedProduct());
            //switch scenes
            thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("ModifyProductForm.fxml"));
            thisStage.setScene(new Scene(scene));
            thisStage.show();
        } else {
                //Alert User to select an item - ALERT 1
                showAlertDialog(1);
        }
    }

    /**
     * Changes the scene to the AddProduct scene
     * @param actionEvent add button in the product controls area clicked
     * @throws IOException
     */
    @FXML
    private void addNewProduct(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Deletes selected part from the inventory ArrayList
     * @param actionEvent delete part button clicked
     */
    @FXML
    private void deleteSelectedPart(ActionEvent actionEvent) {
        if (tblvwMainParts.getSelectionModel().getSelectedItem() != null ) {

            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.setTitle("Confirm");
            newAlert.setContentText("Are you sure you want to delete the selected part?");
            Optional<ButtonType> result = newAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int selectedIndex = tblvwMainParts.getSelectionModel().getSelectedIndex();
                Inventory.getAllParts().remove(selectedIndex);
            }
        }else {
            showAlertDialog(2);
        }
    }

    /**
     * Sets the selected part for the ModifyPartForm to load and loads the ModifyPart scene
     * @param actionEvent modify part button clicked
     * @throws IOException when the FXMLLoader object's load() method fails to load the new scene
     */
    @FXML
    private void modifySelectedPart(ActionEvent actionEvent) throws IOException {
        setSelectedPart(tblvwMainParts.getSelectionModel().getSelectedItem());
        //Confirm object was selected
        if(getSelectedPart() != null) {
            //switch scenes
            thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("ModifyPartForm.fxml"));
            thisStage.setScene(new Scene(scene));
            thisStage.show();
        } else {
            //Alert  user to select item - ALERT 1
            showAlertDialog(1);
        }
    }

    /**
     * Loads the AddPartForm scene
     * @param actionEvent add part button clicked
     * @throws IOException when the FXMLLoader object's load() method fails to load the new scene
     */
    @FXML
    private void addNewPart(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Searches the Product ArrayList for the ID or Name in the input field
     * @param actionEvent search field contains input and return is pressed
     * @return
     */
    @FXML
    private boolean searchProdMain(ActionEvent actionEvent) {
        ObservableList<Product> resultsList = FXCollections.observableArrayList();
        String searchText = tfMainProdSearch.getText();
        //Add all matches of the string and name
        for(Product prod : Inventory.getAllProducts()){
            if (prod.getName().contains(searchText) ) {
                resultsList.add(prod);
            }
        }
        //Add all matches of a number and an ID that are not already in the list
        for (Product prod : Inventory.getAllProducts()) {
            if (resultsList.contains(prod)) {
                continue;
            } else if (String.valueOf(prod.getId()).contains(searchText)) {
                resultsList.add(prod);
            }
        }
        //Search returned no results
        if (resultsList.isEmpty()) {
            //AlertUser of failed searchALERT 3
            showAlertDialog(3);
            //Assign Empty List to tableView
            tblvwMainProd.setItems(resultsList);
            return false;
        }
        //Search returned results
        tblvwMainProd.setItems(resultsList);
        return true;

    }

    /**
     * Searches the Part ArrayList for the ID or Name in teh input field
     * @param actionEvent search field contains input and return is pressed
     * @return boolean indicating success
     */
    @FXML
    private boolean searchPartsMain(ActionEvent actionEvent) {
        ObservableList<Part> resultsList = FXCollections.observableArrayList();
        String searchText = tfMainPartSearch.getText();
        //Add all matches of the string and name
        for(Part part : Inventory.getAllParts()){
            if (part.getName().contains(searchText) ) {
                resultsList.add(part);
            }
        }
        //Add all matches of a number and an ID that are not already in the list
        for (Part part : Inventory.getAllParts()) {
            if (resultsList.contains(part)) {
                continue;
            } else if (String.valueOf(part.getId()).contains(searchText)) {
                resultsList.add(part);
            }
        }
        //Search returned no results
        if (resultsList.isEmpty()) {
            //AlertUser of search failure - ALERT 3
            showAlertDialog(3);
            //Assign Empty List to tableView
            tblvwMainParts.setItems(resultsList);
            return false;
        }
        //Search returned results
        tblvwMainParts.setItems(resultsList);
        return true;
    }

    /**
     * Display appropriate alert message to user contingent upon input
     * @param alertType integer indicating the alert to display
     */
    //User Alert Methods
    private void showAlertDialog(int alertType) {
        //Create new alert
        Alert anAlert = new Alert(Alert.AlertType.ERROR);
        //Use switch statement to populate dialog box and display
        switch (alertType) {
            case 1:
                anAlert.setTitle("Selection Error");
                anAlert.setHeaderText("Error while attempting to modify!");
                anAlert.setContentText("Please select an item from the list above before clicking the Modify button.");
                anAlert.showAndWait();
                break;
            case 2:
                anAlert.setTitle("Selection Error");
                anAlert.setHeaderText("Error while attempting to delete!");
                anAlert.setContentText("Please select an item from the list above before clicking the Delete button.");
                anAlert.showAndWait();
                break;
            case 3:
                anAlert.setTitle("Search Error");
                anAlert.setHeaderText("Error while attempting a search!");
                anAlert.setContentText("The search criteria yielded no results.");
                anAlert.showAndWait();
                break;
            case 4:
                anAlert.setTitle("Deletion Error");
                anAlert.setHeaderText("Error while attempting to delete product!");
                anAlert.setContentText("This product has associated parts. Please remove ALL parts " +
                        "and attempt deletion again.");
                anAlert.showAndWait();
                break;
        }
    }

}