/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vending_machine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author YehWolf
 */
public class Validation {
    
    public static boolean isDrinkNameTaken(String drinkName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("drinks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[0].equalsIgnoreCase(drinkName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            }
        return false;
    }
}