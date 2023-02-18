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
import java.util.ResourceBundle;

public class ModifyProductFormController implements Initializable {
    Stage thisStage;
    Parent scene;

    ObservableList<Part> inventoryList = Inventory.getAllParts();
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Product thisProduct;


    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thisProduct = MainFormController.getSelectedProduct();
        associatedParts = thisProduct.getAssociatedParts();
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


    @FXML
    private TableView tblvPartsSearch;
    @FXML
    private TableView tblvPartsList;
    @FXML
    private Button btnAddPartToProduct;
    @FXML
    private Button btnRemoveAssociatedPart;
    @FXML
    private Button btnCancelModify;
    @FXML
    private Button btnSaveUpdatedProduct;
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
    @FXML
    private TableColumn colModAssocPartPrice;
    @FXML
    private TableColumn colModAssocPartInv;
    @FXML
    private TableColumn colModAssocPartName;
    @FXML
    private TableColumn colModAssocPartID;
    @FXML
    private TableColumn colModPartPrice;
    @FXML
    private TableColumn colModPartInv;
    @FXML
    private TableColumn colModPartName;
    @FXML
    private TableColumn colModPartID;

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
            if (resultsList.contains(part)) {
                continue;
            } else if (String.valueOf(part.getId()).contains(searchText)) {
                resultsList.add(part);
            }
        }
        //Search returned no results
        if (resultsList.isEmpty()) {
            //AlertUser
            //Assign Empty List to tableView
            tblvPartsSearch.setItems(resultsList);
        }
        //Search returned results
        tblvPartsSearch.setItems(resultsList);

    }

    @FXML
    private void saveProductUpdate(ActionEvent actionEvent) {
        Product newProduct = validateAndBuildProductToAdd();
        if (newProduct != null) {
            int index = -1;
            for (Product prod : Inventory.getAllProducts()) {
                index++;
                if (thisProduct.getId() == newProduct.getId()){
                    Inventory.getAllProducts().set(index, newProduct);
                    break;
                }
            }
        }
    }

    @FXML
    private void cancelProductUpdate(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @FXML
    private void removeAssociatedPart(ActionEvent actionEvent) {
        if (tblvPartsList.getSelectionModel().getSelectedItem() != null ) {
            associatedParts.remove(tblvPartsList.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void addPartToProduct(ActionEvent actionEvent) {
        if (tblvPartsSearch.getSelectionModel().getSelectedItem() != null) {
            associatedParts.add((Part) tblvPartsSearch.getSelectionModel().getSelectedItem());
        } else {
            //Alert User to select part
        }
    }

    private Product validateAndBuildProductToAdd() {
        //ID does not need inout validation as it is not input by user
        if (Integer.parseInt(tfModPartMin.getText()) >
                Integer.parseInt(tfModPartMax.getText())) {
            //alertUser
            return null;
        }
        int id = Integer.parseInt(tfModPartID.getText());
        String name = tfModPartName.getText();
        double price = 0.0;
        int inv = 0;
        int min = 0;
        int max = 0;

        //Catch invalid numerical input by user
        try {
            price = Double.parseDouble(tfModPartPrice.getText());
            inv = Integer.parseInt(tfModPartInv.getText());
            max = Integer.parseInt(tfModPartMax.getText());
            min = Integer.parseInt(tfModPartMin.getText());
        } catch (NumberFormatException nfe) {
            //alertUser
            return null;
        }

        return new Product(associatedParts, id, name, price, inv, min, max);
    }
}
