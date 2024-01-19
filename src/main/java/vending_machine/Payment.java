/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vending_machine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YehWolf
 */
public class Payment {

    public Payment() {
        
    }
    
    public double calculateTotal(List<AddedDrink> addedDrinksList) {
        double total = 0.0;
        for (AddedDrink addedDrink : addedDrinksList) {
            total += addedDrink.getPrice() * addedDrink.getQuantity();
        }
        return total;
    }
    
    public boolean processPayment(List<AddedDrink> addedDrinksList, double cash) {
        double totalAmount = calculateTotal(addedDrinksList);
        
        if (cash >= totalAmount) {
            for (AddedDrink addedDrink : addedDrinksList) {
                int quantityToDeduct = addedDrink.getQuantity();
                updateDrinksQuantity(addedDrink.getDrinkName(), quantityToDeduct);
            }
            return true;
        } else {
            return false;
        }
    }
    
    private void updateDrinksQuantity(String drinkName, int quantityToDeduct) {
        List<String> updatedLines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("drinks.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    if (name.equals(drinkName)) {
                        int currentQuantity = Integer.parseInt(parts[2]);
                        int updatedQuantity = currentQuantity - quantityToDeduct;
                        updatedLines.add(parts[0] + "," + parts[1] + "," + updatedQuantity + "," + parts[3]);
                    } else {
                        updatedLines.add(line);
                    }
                } else {
                    updatedLines.add(line);
                }
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter("drinks.txt"));
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
