package io.casinelli.inventoryproject;

import io.casinelli.inventoryproject.Model.Part;
import io.casinelli.inventoryproject.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    //Controller instance variables
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
    private TextField tfMainProdSearch;
    @FXML
    private TextField tfMainPartSearch;
    @FXML
    private TableColumn<> colProdPrice;
    @FXML
    private TableColumn<> colProdInv;
    @FXML
    private TableColumn<> colProdName;
    @FXML
    private TableColumn<> colProdID;
    @FXML
    private TableView<Product> tblvwMainProd;
    @FXML
    private TableColumn<> colPartPrice;
    @FXML
    private TableColumn<> colPartInv;
    @FXML
    private TableColumn<> colPartName;
    @FXML
    private TableColumn<> colPartID;
    @FXML
    private TableView<Part> tblvwMainParts;

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
        scene   = FXMLLoader.load(getClass().getResource("ModifyProductForm.fxml"));
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
    private void searchProdMain(ActionEvent actionEvent) {
    }

    @FXML
    private void searchPartsMain(ActionEvent actionEvent) {
    }


}