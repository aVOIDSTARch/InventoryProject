package io.casinelli.inventoryproject;

import io.casinelli.inventoryproject.Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("" +
                "MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Create data to initialize app//
        //Create necessary parts
        InHouse inhouse1 = new InHouse(Inventory.getNextPartID(),
                "motherboard", 159.96, 25, 2, 50, 12);
        InHouse inhouse2 = new InHouse(Inventory.getNextPartID(),
                "graphics card", 899.99, 15, 1, 30, 11);
        InHouse inhouse3 = new InHouse(Inventory.getNextPartID(),
                "wifi adapter", 49.99, 57, 1, 100, 14);
        Outsourced outsourced1 = new Outsourced(Inventory.getNextPartID(),
                "power supply", 119.99, 21, 4, 25, "ThermalTake");
        Outsourced outsourced2 = new Outsourced(Inventory.getNextPartID(),
                "midtower case", 129.99, 34, 1, 50, "Rad ATX Cases");
        Outsourced outsourced3 = new Outsourced(Inventory.getNextPartID(),
                "USB dock external", 47.99, 75, 10, 100, "Altec");
        //Add parts to inventory
        Inventory.addPart(inhouse1);
        Inventory.addPart(inhouse2);
        Inventory.addPart(inhouse3);
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);
        Inventory.addPart(outsourced3);
        //Create list of parts for product and add parts
        ObservableList<Part> theseParts = FXCollections.observableArrayList();
        theseParts.addAll(inhouse1, inhouse2, inhouse3);
        //Create product
        Product aProduct = new Product(theseParts, Inventory.getNextProductID(),
                "barebones kit", 599.99, 3, 1, 5);
        //Add product to inventory
        Inventory.addProduct(aProduct);

        System.out.println(Inventory.getAllParts());
        System.out.println(Inventory.getAllProducts());

        launch();
    }

}