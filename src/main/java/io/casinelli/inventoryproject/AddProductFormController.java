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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

    Stage thisStage;
    Parent scene;
    //Observable lists for populating tableviews in scene
    ObservableList<Part> inventoryList = Inventory.getAllParts();
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * @param url
     * @param resourceBundle
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





    @FXML
    private TextField tfPartSearch;
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
    @FXML
    private TableColumn colAssocPartPrice;
    @FXML
    private TableColumn colAssocPartInv;
    @FXML
    private TableColumn colAssocPartName;
    @FXML
    private TableColumn colAssocPartID;
    @FXML
    private TableColumn colPriceSearch;
    @FXML
    private TableColumn colPartInvSearch;
    @FXML
    private TableColumn colPartNameSearch;
    @FXML
    private TableColumn colPartIDSearch;

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
    private void addPartToProduct(ActionEvent actionEvent) {
        if (tblvPartsSearch.getSelectionModel().getSelectedItem() != null) {
            associatedParts.add((Part) tblvPartsSearch.getSelectionModel().getSelectedItem());
        } else {
                //Alert User to select part
        }

    }

    @FXML
    private void removeAssociatedPart(ActionEvent actionEvent) {
        if (tblvPartsList.getSelectionModel().getSelectedItem() != null) {
            associatedParts.remove((Part) tblvPartsList.getSelectionModel().getSelectedItem());
        } else {
            //Alert User to select part
        }
    }

    @FXML
    private void cancelProductUpdate(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @FXML
    private void saveProductUpdate(ActionEvent actionEvent) {
        Product newProduct = validateAndBuildProductToAdd();
        if (newProduct != null) {
            Inventory.addProduct(newProduct);
        }
    }

    private Product validateAndBuildProductToAdd() {
        //ID does not need inout validation as it is not input by user
        if (Integer.parseInt(tfAddPartMin.getText()) >
                Integer.parseInt(tfAddPartMax.getText())) {
            //alertUser
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
            return null;
        }

        return new Product(associatedParts, id, name, price, inv, min, max);
    }

}
