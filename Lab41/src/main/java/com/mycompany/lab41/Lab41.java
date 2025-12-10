package com.mycompany.lab41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Lab41 extends JFrame {
    private static Lab41 instance;
    private ArrayList<product> prods;
    
    private JButton dbButton;
    private JLabel statusLabel;
    
    public Lab41() {
        instance = this;
        prods = new ArrayList<>();
        initializeGUI();
        autoLoadData();
    }
    
    public static Lab41 getInstance() {
        return instance;
    }
    
    public ArrayList<product> getProducts() {
        return prods;
    }
    
    public void setProducts(ArrayList<product> products) {
        this.prods = products;
        updateStatusLabel();
    }
    
    private void autoLoadData() {
        ArrayList<product> loadedProducts = DBconnector.autoLoadOnStartup();
        
        if (loadedProducts != null && !loadedProducts.isEmpty()) {
            prods = loadedProducts;
            updateStatusLabel();
            
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                    "Database detected!\n" +
                    "Successfully loaded " + prods.size() + " products from database.\n" +
                    "Storage: " + DBconnector.getDatabaseInfo(),
                    "Auto-load Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            });
        } else {
            prods = new ArrayList<>();
            updateStatusLabel();
        }
    }
    
    private void initializeGUI() {
        setTitle("Product Database Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        
        JButton addButton = new JButton("ADD");
        JButton listButton = new JButton("LIST");
        JButton scanButton = new JButton("SCAN");
        dbButton = new JButton("DATABASE");
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        addButton.setFont(buttonFont);
        listButton.setFont(buttonFont);
        scanButton.setFont(buttonFont);
        dbButton.setFont(buttonFont);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (prods == null) prods = new ArrayList<>();
                guiADD.showAddDia(prods);
                updateStatusLabel();
            }
        });
        
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (prods == null) prods = new ArrayList<>();
                guiLIST.showListWindow(prods);
            }
        });
        
        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Lab41.this, 
                    "Future", 
                    "SCAN", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        dbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDB.showDatabaseWindow();
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(scanButton);
        buttonPanel.add(dbButton);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Products in memory: 0");
        statusPanel.add(statusLabel);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        updateStatusLabel();
    }
    
    private void updateStatusLabel() {
        if (statusLabel != null) {
            int count = (prods != null) ? prods.size() : 0;
            statusLabel.setText("Products in memory: " + count);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Lab41().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}