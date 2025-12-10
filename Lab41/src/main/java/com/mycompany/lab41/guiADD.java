package com.mycompany.lab41;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class guiADD {
    public static void showAddDia(ArrayList<product> prods) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField descField = new JTextField();
        JTextField consistField = new JTextField();
        
        int newId = generateSimpleId(prods);
        idField.setText(String.valueOf(newId));
        idField.setEditable(false);
        
        panel.add(new JLabel("ID*:"));
        panel.add(idField);
        panel.add(new JLabel("Name*:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Consist:"));
        panel.add(consistField);
        
        int result = JOptionPane.showConfirmDialog(null, panel, 
                  "Add Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int price = 0;
            String priceText = priceField.getText().trim();
            if (!priceText.isEmpty()) {
                try {
                    price = Integer.parseInt(priceText);
                    if (price < 0) {
                        JOptionPane.showMessageDialog(null, "Incorrect input", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Incorrect input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            product newProduct = new product();
            newProduct.setart(newId);
            newProduct.setname(nameField.getText().trim());
            newProduct.setprice(price);
            
            String description = descField.getText().trim();
            if (!description.isEmpty()) {
                newProduct.setdesk(description);
            }
            
            String consist = consistField.getText().trim();
            if (!consist.isEmpty()) {
                newProduct.setcons(consist);
            }
            
            prods.add(newProduct);
            JOptionPane.showMessageDialog(null, 
                "Product added successfully!\nID: " + newId + "\nName: " + nameField.getText().trim(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private static int generateSimpleId(ArrayList<product> prods) {
        if (prods.isEmpty()) {
            return 1;
        }
        
        int maxId = 0;
        for (product p : prods) {
            if (p.articul > maxId) {
                maxId = p.articul;
            }
        }
        return maxId + 1;
    }
}