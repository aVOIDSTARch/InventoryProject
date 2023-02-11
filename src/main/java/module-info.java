module io.casinelli.inventoryproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens io.casinelli.inventoryproject to javafx.fxml;
    exports io.casinelli.inventoryproject;
    exports io.casinelli.inventoryproject.Model;
    opens io.casinelli.inventoryproject.Model to javafx.fxml;

}