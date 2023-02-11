package io.casinelli.inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductFormController implements Initializable {
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
    }

    @FXML
    private void saveProductUpdate(ActionEvent actionEvent) {
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
    }

    @FXML
    private void addPartToProduct(ActionEvent actionEvent) {
    }


}
