package com.mycompany.lab41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class guiLIST {
    private ArrayList<product> prods;
    private JFrame listFrame;
    
    public guiLIST(ArrayList<product> prods) {
        this.prods = prods;
        createListWindow();
    }
    
    private void createListWindow() {
        listFrame = new JFrame("Product List");
        listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listFrame.setSize(700, 500);
        listFrame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Product List Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea productsArea = new JTextArea();
        productsArea.setEditable(false);
        productsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(productsArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        JButton editButton = new JButton("Edit Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton deleteAllButton = new JButton("Delete All");
        JButton closeButton = new JButton("Close");
        
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllProducts(productsArea);
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiSEARCH.showSearchWindow(prods);
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiEDITOR.showEditorWindow(prods);
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteDialog();
            }
        });
        
        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAllProducts();
            }
        });
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listFrame.dispose();
            }
        });
        
        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(deleteAllButton);
        buttonPanel.add(closeButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        listFrame.add(mainPanel);
        
        showAllProducts(productsArea);
    }
    
    private void showAllProducts(JTextArea productsArea) {
        if (prods.isEmpty()) {
            productsArea.setText("No products available.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s %-20s %-10s %-15s %-15s\n", 
            "ID", "Name", "Price", "Description", "Consist"));
        sb.append("=".repeat(80)).append("\n");
        
        for (product p : prods) {
            String desc = (p.description != null && !p.description.isEmpty()) ? 
                (p.description.length() > 12 ? p.description.substring(0, 12) + "..." : p.description) : "---";
            String consist = (p.consist != null && !p.consist.isEmpty()) ? 
                (p.consist.length() > 12 ? p.consist.substring(0, 12) + "..." : p.consist) : "---";
            
            sb.append(String.format("%-6d %-20s %-10d %-15s %-15s\n", 
                p.articul, 
                p.name.length() > 18 ? p.name.substring(0, 18) + "..." : p.name,
                p.price, 
                desc,
                consist));
        }
        
        productsArea.setText(sb.toString());
    }
    
    private void showDeleteDialog() {
        if (prods.isEmpty()) {
            JOptionPane.showMessageDialog(listFrame, 
                "No products available for deletion", 
                "Info", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String idStr = JOptionPane.showInputDialog(listFrame, 
            "Enter product ID to delete:", 
            "Delete Product", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                product foundProduct = findProductById(id);
                
                if (foundProduct != null) {
                    deleteProduct(foundProduct);
                } else {
                    JOptionPane.showMessageDialog(listFrame, 
                        "Product with ID " + id + " not found", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(listFrame, 
                    "Invalid ID format", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteProduct(product productToDelete) {
        int confirm = JOptionPane.showConfirmDialog(listFrame, 
            "Are you want delete product:\n" +
            "ID: " + productToDelete.articul + "\n" +
            "Name: " + productToDelete.name + "\n\n" +
            "Product not return!",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            prods.remove(productToDelete);
            JOptionPane.showMessageDialog(listFrame, 
                "Product deleted", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            showAllProducts(getTextAreaFromFrame());
        }
    }
    
    private void deleteAllProducts() {
        if (prods.isEmpty()) {
            JOptionPane.showMessageDialog(listFrame, 
                "The list is empty", 
                "Info", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm1 = JOptionPane.showConfirmDialog(listFrame, 
            "Are you sure want delete ALL products?\n" +
            "This will remove " + prods.size() + " products!\n\n" +
            "This action cannot be undone!", 
            "", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm1 == JOptionPane.YES_OPTION) {
            int confirm2 = JOptionPane.showConfirmDialog(listFrame, 
                "Are you SERIOUSLY?\n" +
                "All data will delete!\n\n" +
                "", 
                "",
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.ERROR_MESSAGE);
            
            if (confirm2 == JOptionPane.YES_OPTION) {
                prods.clear();
                JOptionPane.showMessageDialog(listFrame, 
                    "List deleted\nHave a nice day!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                showAllProducts(getTextAreaFromFrame());
            }
        }
    }
    
    private product findProductById(int id) {
        for (product p : prods) {
            if (p.articul == id) {
                return p;
            }
        }
        return null;
    }
    
    private JTextArea getTextAreaFromFrame() {
        JScrollPane scrollPane = (JScrollPane) ((BorderLayout) listFrame.getContentPane().getLayout())
            .getLayoutComponent(BorderLayout.CENTER);
        return (JTextArea) scrollPane.getViewport().getView();
    }
    
    public void show() {
        listFrame.setVisible(true);
    }
    
    public static void showListWindow(ArrayList<product> prods) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new guiLIST(prods).show();
            }
        });
    }
}