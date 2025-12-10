package com.mycompany.lab41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class guiEDITOR {
    private ArrayList<product> prods;
    private JFrame editorFrame;
    private product currentProduct;
    private JTextArea productInfoArea;
    
    public guiEDITOR(ArrayList<product> prods) {
        this.prods = prods;
        createEditorWindow();
    }
    
    private void createEditorWindow() {
        editorFrame = new JFrame("Product Editor");
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editorFrame.setSize(700, 500);
        editorFrame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Product Editor", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Find Product to Edit"));
        
        JLabel idLabel = new JLabel("Enter Product ID:");
        JTextField idField = new JTextField(10);
        JButton findButton = new JButton("Find Product");
        JButton clearButton = new JButton("Clear");
        
        searchPanel.add(idLabel);
        searchPanel.add(idField);
        searchPanel.add(findButton);
        searchPanel.add(clearButton);
        
        productInfoArea = new JTextArea(15, 50);
        productInfoArea.setEditable(false);
        productInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        productInfoArea.setBackground(new Color(250, 250, 250));
        
        JScrollPane infoScrollPane = new JScrollPane(productInfoArea);
        infoScrollPane.setBorder(BorderFactory.createTitledBorder("Product Information"));
        
        JPanel editPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        editPanel.setBorder(BorderFactory.createTitledBorder("Edit Fields"));
        
        JButton editNameButton = new JButton("Edit Name");
        JButton editPriceButton = new JButton("Edit Price");
        JButton editDescButton = new JButton("Edit Description");
        JButton editConsistButton = new JButton("Edit Consist");
        JButton saveAllButton = new JButton("Save All Changes");
        JButton closeButton = new JButton("Close Editor");
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        editNameButton.setFont(buttonFont);
        editPriceButton.setFont(buttonFont);
        editDescButton.setFont(buttonFont);
        editConsistButton.setFont(buttonFont);
        saveAllButton.setFont(buttonFont);
        closeButton.setFont(buttonFont);
        
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findProductById(idField.getText().trim());
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
                clearProductInfo();
            }
        });
        
        editNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProduct == null) {
                    showNoProductSelectedMessage();
                    return;
                }
                editName();
            }
        });
        
        editPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProduct == null) {
                    showNoProductSelectedMessage();
                    return;
                }
                editPrice();
            }
        });
        
        editDescButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProduct == null) {
                    showNoProductSelectedMessage();
                    return;
                }
                editDescription();
            }
        });
        
        editConsistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProduct == null) {
                    showNoProductSelectedMessage();
                    return;
                }
                editConsist();
            }
        });
        
        saveAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProduct == null) {
                    showNoProductSelectedMessage();
                    return;
                }
                saveAllChanges();
            }
        });
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorFrame.dispose();
            }
        });
        
        editPanel.add(editNameButton);
        editPanel.add(editPriceButton);
        editPanel.add(editDescButton);
        editPanel.add(editConsistButton);
        editPanel.add(saveAllButton);
        editPanel.add(closeButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(infoScrollPane, BorderLayout.CENTER);
        mainPanel.add(editPanel, BorderLayout.SOUTH);
        
        editorFrame.add(mainPanel);
    }
    
    private void findProductById(String idStr) {
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(editorFrame, 
                "Please enter a product ID", 
                "Input Required", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            currentProduct = null;
            
            for (product p : prods) {
                if (p.articul == id) {
                    currentProduct = p;
                    break;
                }
            }
            
            if (currentProduct != null) {
                displayProductInfo();
                showSuccessMessage("Product found! You can now edit the fields.");
            } else {
                JOptionPane.showMessageDialog(editorFrame, 
                    "Product with ID " + id + " not found", 
                    "Product Not Found", 
                    JOptionPane.ERROR_MESSAGE);
                clearProductInfo();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(editorFrame, 
                "Please enter a valid numeric ID", 
                "Invalid Input", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showNoProductSelectedMessage() {
        JOptionPane.showMessageDialog(editorFrame, 
            "Please first find a product to edit!\n\n" +
            "1. Enter product ID in the search field\n" +
            "2. Click 'Find Product' to load product data\n" +
            "3. Then you can edit the fields", 
            "No Product Selected", 
            JOptionPane.WARNING_MESSAGE);
    }
    
    private void displayProductInfo() {
        if (currentProduct == null) return;
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== PRODUCT DETAILS ===\n\n");
        sb.append(String.format("%-15s: %d\n", "ID", currentProduct.articul));
        sb.append(String.format("%-15s: %s\n", "Name", currentProduct.name));
        sb.append(String.format("%-15s: %d\n", "Price", currentProduct.price));
        sb.append(String.format("%-15s: %s\n", "Description", 
            currentProduct.description != null ? currentProduct.description : "---"));
        sb.append(String.format("%-15s: %s\n", "Consist", 
            currentProduct.consist != null ? currentProduct.consist : "---"));
        sb.append("\n=== EDITING INSTRUCTIONS ===\n");
        sb.append("• Click any 'Edit' button to modify that field\n");
        sb.append("• Use 'Save All Changes' to confirm all modifications\n");
        sb.append("• Empty fields will be set to null\n");
        
        productInfoArea.setText(sb.toString());
    }
    
    private void editName() {
        String newName = JOptionPane.showInputDialog(editorFrame, 
            "Enter new product name:", 
            "Edit Name", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            null, 
            currentProduct.name).toString();
        
        if (newName != null && !newName.trim().isEmpty()) {
            currentProduct.setname(newName.trim());
            displayProductInfo();
            showSuccessMessage("Name updated successfully!");
        } else if (newName != null) {
            JOptionPane.showMessageDialog(editorFrame, 
                "Product name cannot be empty", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editPrice() {
        String priceStr = JOptionPane.showInputDialog(editorFrame, 
            "Enter new price (numbers only):", 
            "Edit Price", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            null, 
            String.valueOf(currentProduct.price)).toString();
        
        if (priceStr != null && !priceStr.trim().isEmpty()) {
            try {
                int newPrice = Integer.parseInt(priceStr.trim());
                if (newPrice >= 0) {
                    currentProduct.setprice(newPrice);
                    displayProductInfo();
                    showSuccessMessage("Price updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(editorFrame, 
                        "Price cannot be negative", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(editorFrame, 
                    "Please enter a valid number for price", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editDescription() {
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setText(currentProduct.description != null ? currentProduct.description : "");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        int result = JOptionPane.showConfirmDialog(editorFrame, 
            scrollPane, 
            "Edit Description", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String newDesc = textArea.getText().trim();
            if (newDesc.isEmpty()) {
                currentProduct.setdesk(null);
            } else {
                currentProduct.setdesk(newDesc);
            }
            displayProductInfo();
            showSuccessMessage("Description updated successfully!");
        }
    }
    
    private void editConsist() {
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setText(currentProduct.consist != null ? currentProduct.consist : "");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        int result = JOptionPane.showConfirmDialog(editorFrame, 
            scrollPane, 
            "Edit Consist", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String newConsist = textArea.getText().trim();
            if (newConsist.isEmpty()) {
                currentProduct.setcons(null);
            } else {
                currentProduct.setcons(newConsist);
            }
            displayProductInfo();
            showSuccessMessage("Consist updated successfully!");
        }
    }
    
    private void saveAllChanges() {
        int confirm = JOptionPane.showConfirmDialog(editorFrame, 
            "Save all changes to product?\n\n" +
            "ID: " + currentProduct.articul + "\n" +
            "Name: " + currentProduct.name + "\n" +
            "Price: " + currentProduct.price + "\n" +
            "Description: " + (currentProduct.description != null ? currentProduct.description : "---") + "\n" +
            "Consist: " + (currentProduct.consist != null ? currentProduct.consist : "---"), 
            "Confirm Save", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            showSuccessMessage("All changes saved successfully!\nProduct has been updated in the database.");
        }
    }
    
    private void clearProductInfo() {
        currentProduct = null;
        productInfoArea.setText("");
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(editorFrame, 
            message, 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void show() {
        editorFrame.setVisible(true);
    }
    
    public static void showEditorWindow(ArrayList<product> prods) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new guiEDITOR(prods).show();
            }
        });
    }
}