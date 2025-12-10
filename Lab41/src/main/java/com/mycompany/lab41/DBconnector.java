package com.mycompany.lab41;

import java.sql.*;
import java.util.ArrayList;
import java.io.*;

public class DBconnector {
    private static boolean driverAvailable = false;
    
    static {
        checkDriver();
        initializeDatabase();
    }
    
    private static void checkDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
            driverAvailable = true;
            System.out.println("SQLite driver loaded successfully");
        } catch (ClassNotFoundException e) {
            driverAvailable = false;
            System.out.println("SQLite driver not found, using file storage");
        }
    }
    
    private static void initializeDatabase() {
        if (!driverAvailable) return;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            String sql = "CREATE TABLE IF NOT EXISTS products (\n" +
                         "    id INTEGER PRIMARY KEY,\n" +
                         "    name TEXT NOT NULL,\n" +
                         "    price INTEGER DEFAULT 0,\n" +
                         "    description TEXT,\n" +
                         "    consist TEXT\n" +
                         ")";
            
            stmt.execute(sql);
            System.out.println("Database initialized");
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
    
    private static Connection getConnection() throws SQLException {
        if (!driverAvailable) {
            throw new SQLException("Database driver not available");
        }
        return DriverManager.getConnection("jdbc:sqlite:products.db");
    }
    
    public static boolean saveAllProducts(ArrayList<product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("Cannot save: product list is empty");
            return false;
        }
        
        if (!driverAvailable) {
            System.out.println("Cannot save: database driver not available");
            return saveToFile(products);
        }
        
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM products");
            }
            
            String sql = "INSERT INTO products(id, name, price, description, consist) VALUES(?,?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (product p : products) {
                    pstmt.setInt(1, p.articul);
                    pstmt.setString(2, p.name);
                    pstmt.setInt(3, p.price);
                    pstmt.setString(4, p.description);
                    pstmt.setString(5, p.consist);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            
            conn.commit();
            
            saveToFile(products);
            
            System.out.println("Saved " + products.size() + " products to database");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Database save error: " + e.getMessage());
            return saveToFile(products);
        }
    }
    
    public static ArrayList<product> loadAllProducts() {
        if (!driverAvailable) {
            return loadFromFile();
        }
        
        ArrayList<product> products = new ArrayList<>();
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products ORDER BY id")) {
            
            while (rs.next()) {
                product p = new product();
                p.articul = rs.getInt("id");
                p.name = rs.getString("name");
                p.price = rs.getInt("price");
                p.description = rs.getString("description");
                p.consist = rs.getString("consist");
                products.add(p);
            }
            
            System.out.println("Loaded " + products.size() + " products from database");
            
        } catch (SQLException e) {
            System.err.println("Database load error: " + e.getMessage());
            products = loadFromFile();
        }
        
        return products;
    }
    
    public static ArrayList<product> autoLoadOnStartup() {
        return loadAllProducts();
    }
    
    private static boolean saveToFile(ArrayList<product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("products_backup.dat"))) {
            oos.writeObject(products);
            System.out.println("Saved " + products.size() + " products to file");
            return true;
        } catch (IOException e) {
            System.err.println("File save error: " + e.getMessage());
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    private static ArrayList<product> loadFromFile() {
        File file = new File("products_backup.dat");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("products_backup.dat"))) {
            ArrayList<product> products = (ArrayList<product>) ois.readObject();
            System.out.println("Loaded " + products.size() + " products from file");
            return products;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("File load error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public static String getDatabaseInfo() {
        if (!driverAvailable) {
            return "File Storage (products_backup.dat)";
        }
        return "SQLite Database (products.db)";
    }
    
    // Проверить, доступна ли БД
    public static boolean isDatabaseAvailable() {
        return driverAvailable;
    }
    
    public static int getRecordCount() {
        if (!driverAvailable) {
            return loadFromFile().size();
        }
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM products")) {
            
            return rs.next() ? rs.getInt("count") : 0;
            
        } catch (SQLException e) {
            System.err.println("Count error: " + e.getMessage());
            return 0;
        }
    }
}