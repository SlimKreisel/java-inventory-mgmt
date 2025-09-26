package app;



import dao.ProductDAO;
import model.Product;

public class ConsoleApp {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();

        System.out.println("Initial data:");
        System.out.println(dao.getAll());

        System.out.println("\nAdding: USB-C Cable...");
        dao.add(new Product("USB-C Cable", 20, 7.99));
        System.out.println(dao.getAll());

        System.out.println("\nUpdate quantity of id=1 to 9...");
        dao.updateQuantity(1, 9);
        System.out.println(dao.getAll());

        System.out.println("\nUpdate price of id=2 to 24.99...");
        dao.updatePrice(2, 24.99);
        System.out.println(dao.getAll());

        System.out.println("\nTry delete id=999 (likely no-op)...");
        dao.delete(999);
        System.out.println(dao.getAll());
    }
}

