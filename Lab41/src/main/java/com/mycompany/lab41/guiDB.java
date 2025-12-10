package com.mycompany.lab41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class guiDB extends JFrame {
    
    public guiDB() {
        createDatabaseWindow();
    }
    
    private void createDatabaseWindow() {
        setTitle("Database Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Database manager", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Database Status"));
        
        String storageType = DBconnector.isDatabaseAvailable() ? 
                           "SQLite Database" : "File Storage";
        JLabel dbTypeLabel = new JLabel("Storage type: " + storageType);
        JLabel dbFileLabel = new JLabel("Database file: products.db");
        JLabel dbStatusLabel = new JLabel("Records in DB: " + DBconnector.getRecordCount());
        
        Lab41 mainApp = Lab41.getInstance();
        int memoryCount = (mainApp != null && mainApp.getProducts() != null) ? 
                         mainApp.getProducts().size() : 0;
        JLabel memoryLabel = new JLabel("Products in memory: " + memoryCount);
        
        infoPanel.add(dbTypeLabel);
        infoPanel.add(dbFileLabel);
        infoPanel.add(dbStatusLabel);
        infoPanel.add(memoryLabel);
        
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
        
        JButton saveButton = new JButton("Save Data to Database");
        JButton loadButton = new JButton("Load from Database");
        JButton backupButton = new JButton("Create Backup");
        JButton deleteButton = new JButton("Delete Database");
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        saveButton.setFont(buttonFont);
        loadButton.setFont(buttonFont);
        backupButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurrentData();
            }
        });
        
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromDatabase();
            }
        });
        
        backupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBackup();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDatabase();
            }
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(backupButton);
        buttonPanel.add(deleteButton);
        
        JButton closeButton = new JButton("Close");
        closeButton.setFont(buttonFont);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(closeButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void saveCurrentData() {
        Lab41 mainApp = Lab41.getInstance();
        
        if (mainApp == null) {
            JOptionPane.showMessageDialog(this,
                "Main application not found!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ArrayList<product> currentProducts = mainApp.getProducts();
        
        if (currentProducts == null || currentProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Cannot save: product list is empty!\n" +
                "Please add some products first.",
                "Empty List",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Save " + currentProducts.size() + " products to database?\n" +
            "This will overwrite existing data.",
            "Confirm Save",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = DBconnector.saveAllProducts(currentProducts);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Successfully saved " + currentProducts.size() + " products!",
                    "Save Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error saving data!",
                    "Save Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void loadFromDatabase() {
        if (!DBconnector.isDatabaseAvailable()) {
            JOptionPane.showMessageDialog(this,
                "Database driver not working!\n" +
                "Using file storage only.",
                "Database Not Available",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Load data from database?\n" +
            "This will replace current in-memory data.",
            "Confirm Load",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            ArrayList<product> loadedProducts = DBconnector.loadAllProducts();
            
            if (loadedProducts.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Database is empty!\n" +
                    "No data loaded.",
                    "Empty Database",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            Lab41 mainApp = Lab41.getInstance();
            if (mainApp != null) {
                mainApp.setProducts(loadedProducts);
                JOptionPane.showMessageDialog(this,
                    "Successfully loaded " + loadedProducts.size() + " products!",
                    "Load Complete",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
    }
    
    private void createBackup() {
        Lab41 mainApp = Lab41.getInstance();
        if (mainApp == null || mainApp.getProducts() == null || mainApp.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No data to backup!\n" +
                "Product list is empty.",
                "No Data",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean success = DBconnector.saveAllProducts(mainApp.getProducts());
        
        if (success) {
            JOptionPane.showMessageDialog(this,
                "Backup created successfully!\n" +
                "Data saved to: " + DBconnector.getDatabaseInfo(),
                "Backup Complete",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Error creating backup!",
                "Backup Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteDatabase() {
        if (!DBconnector.isDatabaseAvailable()) {
            JOptionPane.showMessageDialog(this,
                "Database not available!",
                "No Database",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "WARNING: This will delete ALL data from database!\n" +
            "This action cannot be undone!\n\n" +
            "Are you absolutely sure?",
            "Confirm Database Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            File dbFile = new File("products.db");
            if (dbFile.exists() && dbFile.delete()) {
                JOptionPane.showMessageDialog(this,
                    "Database deleted successfully!",
                    "Deletion Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error deleting database!",
                    "Deletion Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void showDatabaseWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new guiDB().setVisible(true);
            }
        });
    }
}