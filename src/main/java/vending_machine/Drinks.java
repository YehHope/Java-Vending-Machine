/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vending_machine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author YehWolf
 */

public class Drinks {
    private String name;
    private double price;
    private int quantity;
    private String imagePath;

    public Drinks(String name, double price, int quantity, String imagePath) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }
    
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return ": " + name + " - Price: $" + price + " - Quantity: " + quantity;
    }

    public static void saveDrinkToFile(Drinks drink) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("drinks.txt", true))) {
            writer.write(drink.getName() + "," +
                         drink.getPrice() + "," +
                         drink.getQuantity() + "," +
                         drink.getImagePath());
            writer.newLine();
            writer.flush();

            System.out.println("Drink added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while adding drink.");
        }
    }
}