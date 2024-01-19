/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vending_machine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YehWolf
 */
public class AddedDrink {
    private static List<AddedDrink> addedDrinksList = new ArrayList<>();
    private String drinkName;
    private int quantity;
    private double price;

    public AddedDrink(String drinkName, int quantity, double price) {
        this.drinkName = drinkName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public static List<AddedDrink> getAddedDrinksList() {
        return addedDrinksList;
    }
    
    public static boolean updateOrAddToList(AddedDrink newDrink) {
        for (AddedDrink addedDrink : addedDrinksList) {
            if (addedDrink.getDrinkName().equals(newDrink.getDrinkName())) {
                addedDrink.setQuantity(newDrink.getQuantity());
                return true;
            }
        }
        addedDrinksList.add(newDrink);
        return false;
    }

    public static boolean removeFromList(List<AddedDrink> addedDrinksList, String drinkName) {
        for (AddedDrink addedDrink : addedDrinksList) {
            if (addedDrink.getDrinkName().equals(drinkName)) {
                addedDrinksList.remove(addedDrink);
                return true;
            }
        }
        return false;
    }
}



