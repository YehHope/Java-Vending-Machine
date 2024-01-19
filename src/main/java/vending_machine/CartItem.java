/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vending_machine;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author YehWolf
 */
public class CartItem {
    private String name;
    private double price;
    private int quantity;

    public CartItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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
    
    public static void addToCartTable(DefaultTableModel cartTableModel, CartItem cartItem) {
        int rowIndex = getCartTableRowIndex(cartTableModel, cartItem.getName());

        if (rowIndex != -1) {
            cartTableModel.setValueAt(cartItem.getQuantity(), rowIndex, 1);
        } else {
            cartTableModel.addRow(new Object[]{
                cartItem.getName(),
                cartItem.getQuantity(),
                "RM" + String.format("%.2f", cartItem.getPrice())
            });
        }
    }

    public static void removeFromCartTable(DefaultTableModel cartTableModel, String itemName) {
        int rowIndex = getCartTableRowIndex(cartTableModel, itemName);
        if (rowIndex != -1) {
            cartTableModel.removeRow(rowIndex);
        }
    }

    private static int getCartTableRowIndex(DefaultTableModel cartTableModel, String itemName) {
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            String name = (String) cartTableModel.getValueAt(i, 0);
            if (name.equals(itemName)) {
                return i;
            }
        }
        return -1;
    }
}
