package io.casinelli.inventoryproject;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

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
    }

    @FXML
    private void addPartToProduct(ActionEvent actionEvent) {
    }

    @FXML
    private void removeAssociatedPart(ActionEvent actionEvent) {
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
    }


}
