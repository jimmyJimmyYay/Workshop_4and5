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

public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public void setMachine(int machineId) {
        this.machineId = machineId;
    }

    public int getMachine() {
        return machineId;
    }
}