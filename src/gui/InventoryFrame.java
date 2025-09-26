package gui;

import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * Swing-based GUI for inventory management.
 * Features:
 *  - Display products in table
 *  - Add, update qty, update price, delete
 *  - Highlights low stock (qty < 5) in red
 */
public class InventoryFrame extends JFrame {

    private ProductDAO dao = new ProductDAO();

    // Table model with non-editable cells
    private DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Name", "Qty", "Price"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    private JTable table = new JTable(model);

    // Input fields

    private JTextField nameField = new JTextField(12);
    private JTextField qtyField = new JTextField(6);
    private JTextField priceField = new JTextField(6);

    // Action buttons
    private JButton addBtn = new JButton("Add");
    private JButton updQtyBtn = new JButton("Update Qty");
    private JButton updPriceBtn = new JButton("Update Price");
    private JButton delBtn = new JButton("Delete");
    private JButton refreshBtn = new JButton("Refresh");

    public InventoryFrame() {
        setTitle("Inventory Management");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Top panel: product form
        JPanel form = new JPanel();
        form.add(new JLabel("Name"));
        form.add(nameField);
        form.add(new JLabel("Qty"));
        form.add(qtyField);
        form.add(new JLabel("Price"));
        form.add(priceField);
        form.add(addBtn);

        // Bottom panel with actions
        JPanel actions = new JPanel();
        actions.add(updQtyBtn);
        actions.add(updPriceBtn);
        actions.add(delBtn);
        actions.add(refreshBtn);

        // Layout
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.NORTH);
        add(actions, BorderLayout.SOUTH);

        // Custom renderer: mark low stock red
        table.getColumnModel().getColumn(2).setCellRenderer(
                new javax.swing.table.DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable t, Object v,boolean s, boolean f, int r, int c) {
                        Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
                        try {
                            int qty = Integer.parseInt(t.getValueAt(r, 2).toString());
                            if (qty < 5) {
                                comp.setBackground(new Color(255, 200, 200)); // light red
                            } else {
                                comp.setBackground(Color.WHITE);
                            }
                        } catch (Exception e) {
                            comp.setBackground(Color.WHITE);
                        }
                        return comp;
                    }
                }
        );


        // Wire up events
        wire();
        refreshTable();
    }
    /**
     * Event handlers for buttons (add, update, delete, refresh).
     */
    private void wire() {
        // Add product
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String n = nameField.getText().trim();
                    int q = Integer.parseInt(qtyField.getText().trim());
                    double p = Double.parseDouble(priceField.getText().trim());

                    if (n.isEmpty()) {
                        JOptionPane.showMessageDialog(InventoryFrame.this, "⚠️ Product name cannot be empty.");
                        return;
                    }
                    if (q < 0) {
                        JOptionPane.showMessageDialog(InventoryFrame.this, "⚠️ Quantity cannot be negative.");
                        return;
                    }
                    if (p < 0) {
                        JOptionPane.showMessageDialog(InventoryFrame.this, "⚠️ Price cannot be negative.");
                        return;
                    }

                    dao.add(new Product(n, q, p));
                    clearForm();
                    refreshTable();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(InventoryFrame.this,
                            "Error: " + ex.getMessage());
                }
            }
        });

        // Update quantity
        updQtyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Select a row");
                    return;
                }
                int id = (int) model.getValueAt(row, 0);
                String s = JOptionPane.showInputDialog("New quantity:", model.getValueAt(row, 2));

                if (s != null) {
                    try {
                        int q = Integer.parseInt(s);
                        if (q < 0) throw new NumberFormatException();
                        dao.updateQuantity(id, q);
                        refreshTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(InventoryFrame.this, "Invalid quantity");
                    }
                }
            }
        });

        // Update price
        updPriceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Select a row");
                    return;
                }
                int id = (int) model.getValueAt(row, 0);
                String s = JOptionPane.showInputDialog("New price:", model.getValueAt(row, 3));

                if (s != null) {
                    try {
                        double p = Double.parseDouble(s);
                        if (p < 0) throw new NumberFormatException();
                        dao.updatePrice(id, p);
                        refreshTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(InventoryFrame.this, "Invalid price");
                    }
                }
            }
        });

        // Delete product
        delBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Select a row");
                    return;
                }
                int id = (int) model.getValueAt(row, 0);
                int ok = JOptionPane.showConfirmDialog(
                        InventoryFrame.this,
                        "Delete product " + id + "?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

                if (ok == JOptionPane.YES_OPTION) {
                    dao.delete(id);
                    refreshTable();
                }
            }
        });

        // Refresh table
        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
    }
    /**
     * Refresh table with latest data from DB.
     */
    private void refreshTable() {
        model.setRowCount(0);
        List<Product> list = dao.getAll();
        for (Product p : list) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getQuantity(),
                    p.getPrice()
            });
        }
    }

    private void clearForm() {
        nameField.setText("");
        qtyField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        new InventoryFrame().setVisible(true);
    }
}
