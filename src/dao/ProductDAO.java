package dao;

import model.Product;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data Access Object (DAO) for Product.
 * Encapsulates all database operations: add, read, update, delete.
 */
public class ProductDAO {
    // Insert a new product into DB
    public boolean add(Product p) {
        String sql = "INSERT INTO products(name, quantity, price) VALUES (?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, p.getName());
            s.setInt(2, p.getQuantity());
            s.setDouble(3, p.getPrice());
            return s.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    // Fetch all products sorted by id
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id, name, quantity, price FROM products ORDER BY id";
        try (Connection c = DatabaseConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    // Update product quantity by id
    public boolean updateQuantity(int id, int qty) {
        String sql = "UPDATE products SET quantity=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {
            s.setInt(1, qty);
            s.setInt(2, id);
            return s.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    // Update product price by id
    public boolean updatePrice(int id, double price) {
        String sql = "UPDATE products SET price=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {
            s.setDouble(1, price);
            s.setInt(2, id);
            return s.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    // Delete product by id
    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {
            s.setInt(1, id);
            return s.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}

