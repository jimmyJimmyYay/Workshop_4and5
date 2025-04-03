/**********************************************
 Workshop #4 & 5
 Course: APD
 Last Name: Pahurkar
 First Name: Harsh
 ID: 115587222
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Date: March 16, 2025
 **********************************************/
package com.harshpahurkar.workshop_4.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;
    private static int partIdCounter = 1;
    private static int productIdCounter = 1;

    public Inventory() {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
        loadInitialData();
    }

    public static int getNextPartId() {
        return partIdCounter++;
    }

    public static int getNextProductId() {
        return productIdCounter++;
    }

    public void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public Part searchPartByID(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public Product searchProductByID(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public ObservableList<Part> searchPartByName(String name) {
        ObservableList<Part> resultList = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(name.toLowerCase())) {
                resultList.add(part);
            }
        }
        return resultList;
    }

    public ObservableList<Product> searchProductByName(String name) {
        ObservableList<Product> resultList = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                resultList.add(product);
            }
        }
        return resultList;
    }

    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct) {
        if (selectedProduct.getAllAssociatedParts().size() > 0) {
            return false;
        }
        return allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    private void loadInitialData() {
        Part part1 = new InHouse(getNextPartId(), "Wheel", 15.99, 20, 5, 50, 101);
        Part part2 = new InHouse(getNextPartId(), "Frame", 89.99, 10, 2, 20, 102);
        Part part3 = new Outsourced(getNextPartId(), "Seat", 24.99, 15, 3, 30, "SeatCo");
        Part part4 = new Outsourced(getNextPartId(), "Chain", 12.99, 25, 5, 40, "ChainMakers");
        Part part5 = new InHouse(getNextPartId(), "Pedal", 8.99, 30, 10, 60, 103);
        Part part6 = new InHouse(getNextPartId(), "Handlebar", 19.99, 12, 3, 25, 104);
        Part part7 = new Outsourced(getNextPartId(), "Brake", 14.99, 18, 5, 30, "BrakeSystems");
        Part part8 = new InHouse(getNextPartId(), "Gear Set", 34.99, 8, 2, 15, 105);
        Part part9 = new Outsourced(getNextPartId(), "Light", 9.99, 22, 8, 35, "LightTech");
        Part part10 = new InHouse(getNextPartId(), "Bell", 4.99, 40, 15, 70, 106);
        Part part11 = new Outsourced(getNextPartId(), "Basket", 16.99, 10, 2, 20, "BasketWeavers");
        Part part12 = new InHouse(getNextPartId(), "Shock", 29.99, 12, 4, 25, 107);

        addPart(part1);
        addPart(part2);
        addPart(part3);
        addPart(part4);
        addPart(part5);
        addPart(part6);
        addPart(part7);
        addPart(part8);
        addPart(part9);
        addPart(part10);
        addPart(part11);
        addPart(part12);

        Product product1 = new Product(getNextProductId(), "Mountain Bike", 299.99, 5, 1, 10);
        product1.addAssociatedPart(part1);  // Wheel
        product1.addAssociatedPart(part2);  // Frame
        product1.addAssociatedPart(part4);  // Chain
        product1.addAssociatedPart(part5);  // Pedal

        Product product2 = new Product(getNextProductId(), "Road Bike", 349.99, 3, 1, 8);
        product2.addAssociatedPart(part1);  // Wheel
        product2.addAssociatedPart(part2);  // Frame
        product2.addAssociatedPart(part3);  // Seat

        Product product3 = new Product(getNextProductId(), "Kids Bike", 149.99, 8, 2, 15);
        product3.addAssociatedPart(part1);  // Wheel
        product3.addAssociatedPart(part10); // Bell

        Product product4 = new Product(getNextProductId(), "Racing Bike", 599.99, 2, 1, 5);
        product4.addAssociatedPart(part1);  // Wheel
        product4.addAssociatedPart(part2);  // Frame
        product4.addAssociatedPart(part8);  // Gear Set

        Product product5 = new Product(getNextProductId(), "City Bike", 279.99, 6, 2, 12);
        product5.addAssociatedPart(part1);  // Wheel
        product5.addAssociatedPart(part11); // Basket

        Product product6 = new Product(getNextProductId(), "BMX Bike", 229.99, 4, 1, 8);
        product6.addAssociatedPart(part1);  // Wheel
        product6.addAssociatedPart(part6);  // Handlebar

        Product product7 = new Product(getNextProductId(), "Cruiser Bike", 259.99, 5, 1, 10);
        product7.addAssociatedPart(part1);  // Wheel
        product7.addAssociatedPart(part3);  // Seat

        Product product8 = new Product(getNextProductId(), "Electric Bike", 699.99, 2, 1, 5);
        product8.addAssociatedPart(part1);  // Wheel
        product8.addAssociatedPart(part9);  // Light

        Product product9 = new Product(getNextProductId(), "Hybrid Bike", 379.99, 3, 1, 7);
        product9.addAssociatedPart(part1);  // Wheel
        product9.addAssociatedPart(part7);  // Brake

        Product product10 = new Product(getNextProductId(), "Mountain Pro Bike", 899.99, 1, 1, 3);
        product10.addAssociatedPart(part1);  // Wheel
        product10.addAssociatedPart(part12); // Shock

        addProduct(product1);
        addProduct(product2);
        addProduct(product3);
        addProduct(product4);
        addProduct(product5);
        addProduct(product6);
        addProduct(product7);
        addProduct(product8);
        addProduct(product9);
        addProduct(product10);
    }
}