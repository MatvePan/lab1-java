package com.mycompany.lab41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class guiSEARCH {
    private ArrayList<product> prods;
    private JFrame searchFrame;
    private JTextArea resultsArea;
    
    public guiSEARCH(ArrayList<product> prods) {
        this.prods = prods;
        createSearchWindow();
    }
    
    private void createSearchWindow() {
        searchFrame = new JFrame("Product Search");
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.setSize(800, 600);
        searchFrame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Product Search", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel searchPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton nameSearchButton = new JButton("Search by Name");
        JButton idSearchButton = new JButton("Search by ID");
        JButton priceSearchButton = new JButton("Show Products with Price");
        JButton descSearchButton = new JButton("Search in Description");
        JButton consistSearchButton = new JButton("Search in Consist");
        JButton closeButton = new JButton("Close");
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        nameSearchButton.setFont(buttonFont);
        idSearchButton.setFont(buttonFont);
        priceSearchButton.setFont(buttonFont);
        descSearchButton.setFont(buttonFont);
        consistSearchButton.setFont(buttonFont);
        closeButton.setFont(buttonFont);
        
        nameSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByName();
            }
        });
        
        idSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchById();
            }
        });
        
        priceSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByPrice();
            }
        });
        
        descSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByDescription();
            }
        });
        
        consistSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByConsist();
            }
        });
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFrame.dispose();
            }
        });
        
        searchPanel.add(nameSearchButton);
        searchPanel.add(idSearchButton);
        searchPanel.add(priceSearchButton);
        searchPanel.add(descSearchButton);
        searchPanel.add(consistSearchButton);
        searchPanel.add(closeButton);
        
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultsArea.setBackground(new Color(240, 240, 240));
        
        JScrollPane resultsScrollPane = new JScrollPane(resultsArea);
        resultsScrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
        resultsScrollPane.setPreferredSize(new Dimension(600, 300));
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.WEST);
        mainPanel.add(resultsScrollPane, BorderLayout.CENTER);
        
        searchFrame.add(mainPanel);
    }
    
    private void searchByName() {
        if (prods.isEmpty()) {
            showNoProductsMessage();
            return;
        }
        
        String searchTerm = JOptionPane.showInputDialog(searchFrame, 
            "Enter product name or name fragment:", 
            "Search by Name", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            ArrayList<product> results = new ArrayList<>();
            
            for (product p : prods) {
                if (p.name.toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(p);
                }
            }
            
            displaySearchResults(results, "Name search: '" + searchTerm + "'");
        }
    }
    
    private void searchById() {
        if (prods.isEmpty()) {
            showNoProductsMessage();
            return;
        }
        
        String idStr = JOptionPane.showInputDialog(searchFrame, 
            "Enter product ID:", 
            "Search by ID", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int searchId = Integer.parseInt(idStr.trim());
                ArrayList<product> results = new ArrayList<>();
                
                for (product p : prods) {
                    if (p.articul == searchId) {
                        results.add(p);
                        break;
                    }
                }
                
                displaySearchResults(results, "ID search: " + searchId);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(searchFrame, 
                    "No find", 
                    "Incorrect Input", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchByPrice() {
        if (prods.isEmpty()) {
            showNoProductsMessage();
            return;
        }
        
        ArrayList<product> results = new ArrayList<>();
        
        for (product p : prods) {
            if (p.price != 0) {
                results.add(p);
            }
        }
        
        displaySearchResults(results, "Products with price");
    }
    
    private void searchByDescription() {
        if (prods.isEmpty()) {
            showNoProductsMessage();
            return;
        }
        
        String searchTerm = JOptionPane.showInputDialog(searchFrame, 
            "Enter description fragment to search:", 
            "Search in Description", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            ArrayList<product> results = new ArrayList<>();
            
            for (product p : prods) {
                if (p.description != null && p.description.toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(p);
                }
            }
            
            displaySearchResults(results, "Description search: '" + searchTerm + "'");
        }
    }
    
    private void searchByConsist() {
        if (prods.isEmpty()) {
            showNoProductsMessage();
            return;
        }
        
        String searchTerm = JOptionPane.showInputDialog(searchFrame, 
            "Enter consist fragment to search:", 
            "Search in Consist", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            ArrayList<product> results = new ArrayList<>();
            
            for (product p : prods) {
                if (p.consist != null && p.consist.toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(p);
                }
            }
            
            displaySearchResults(results, "Consist search: '" + searchTerm + "'");
        }
    }
    
    private void displaySearchResults(ArrayList<product> results, String searchTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(searchTitle).append(" ===\n");
        sb.append("Found: ").append(results.size()).append(" product(s)\n\n");
        
        if (results.isEmpty()) {
            sb.append("No products\n");
        } else {
            sb.append(String.format("%-6s %-20s %-10s %-20s %-20s\n", 
                "ID", "Name", "Price", "Description", "Consist"));
            sb.append("=".repeat(90)).append("\n");
            
            for (product p : results) {
                String desc = (p.description != null) ? 
                    (p.description.length() > 18 ? p.description.substring(0, 18) + "..." : p.description) : "---";
                String consist = (p.description != null) ? 
                    (p.description.length() > 18 ? p.description.substring(0, 18) + "..." : p.description) : "---";
                
                sb.append(String.format("%-6d %-20s %-10d %-20s %-20s\n", 
                    p.articul, 
                    p.name.length() > 18 ? p.name.substring(0, 18) + "..." : p.name,
                    p.price, 
                    desc,
                    consist));
            }
        }
        
        resultsArea.setText(sb.toString());
        
        resultsArea.setCaretPosition(0);
    }
    
    private void showNoProductsMessage() {
        JOptionPane.showMessageDialog(searchFrame, 
            "No products available for search", 
            "Empty list", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void show() {
        searchFrame.setVisible(true);
    }
    
    public static void showSearchWindow(ArrayList<product> prods) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new guiSEARCH(prods).show();
            }
        });
    }
}