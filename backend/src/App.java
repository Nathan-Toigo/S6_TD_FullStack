import javax.swing.text.Document;

import dao.ProductsDAO;
import model.Product;

public class App {
    public static void main(String[] args) throws Exception {
        ProductsDAO db = new ProductsDAO();
        var products = db.findAll();
        for(Product p : products)
        {
            System.out.println(p.name());
        }
    }
}
