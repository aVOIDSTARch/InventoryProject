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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    //Controller instance variables
    Stage thisStage;
    Parent scene;

    /**
     * Initialize the scene interface for MainForm.fxml
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize parts tableview and associated columns
        tblvwMainParts.setItems(Inventory.getAllParts());
        colPartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        colPartInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        //Initialize products tableview and associated columns
        tblvwMainProd.setItems(Inventory.getAllProducts());
        colProdID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        colProdName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colProdInv.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        colProdPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
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


    @FXML
    private void exitProgram(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    private void deleteSelectedProduct(ActionEvent actionEvent) {
    }

    @FXML
    private void modifySelectedProduct(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("ModifyProductForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @FXML
    private void addNewProduct(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @FXML
    private void deleteSelectedPart(ActionEvent actionEvent) {
    }

    @FXML
    private void modifySelectedPart(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("ModifyPartForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @FXML
    private void addNewPart(ActionEvent actionEvent) throws IOException {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

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
            //AlertUser
            //Assign Empty List to tableView
            tblvwMainProd.setItems(resultsList);
            return false;
        }
        //Search returned results
        tblvwMainProd.setItems(resultsList);
        return true;

    }

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
            //AlertUser
            //Assign Empty List to tableView
            tblvwMainParts.setItems(resultsList);
            return false;
        }
        //Search returned results
        tblvwMainParts.setItems(resultsList);
        return true;
    }


}