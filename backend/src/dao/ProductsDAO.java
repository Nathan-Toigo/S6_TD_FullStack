package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.PolyBayDatabase;
import model.Product;

public class ProductsDAO {
    
    PolyBayDatabase pbDatabase;

    public ProductsDAO(){
        try{
            this.pbDatabase = new PolyBayDatabase();
        }
        catch(SQLException e)
        {
            
        }
    }

    public ArrayList<Product> findAll() throws SQLException{
        PreparedStatement ps = this.pbDatabase.prepareStatement("SELECT * FROM product ORDER BY name");
        ResultSet results = ps.executeQuery();
        ArrayList<Product> products = new ArrayList<Product>();
        while (results.next()) {
            products.add(generateProductFromResultSet(results));
        }
        return products;
    }

    private Product generateProductFromResultSet(ResultSet results) throws SQLException {
        final int id = results.getInt("id");
        final String name = results.getString("name");
        final String owner = results.getString("owner");
        final float bid = results.getFloat("price");
        return new Product(id, name, owner, bid);
    }
}
