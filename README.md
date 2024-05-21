# PREPARATION

## BOITE A OUTILS

Le repo github donné à bien été téléchargé :ok_hand: : 

<p align="center" >
    <image src="image.png" >
</p>

## MYSQL

La base de donnée à bien été créée via phpMyAdmin avec les bons champs :

<p align="center" >
    <image src="image-1.png" width="80%">
</p>

<p align="center" >
    <image src="image-2.png" width="80%">
</p>

Et des lignes d'exemple ont bien étés insérés :

<p align="center" >
    <image src="image-3.png" width="80%">
</p>

# BACKEND - BASE DE DONNEES

## NOUVEAU PROJET

Après création du projet, la fenêtre s'ouvre bien et j'obtiens ceci après execution :

<p align="center" >
    <image src="image-4.png" width="80%">
</p>

## DRIVER MYSQL

L'ajout du fichier .jar s'est déroulé correctement. J'ai maintenant le fichier dans le dossier lib de mon projet : 

<p align="center" >
    <image src="image-5.png" width="20%">
</p>

## MYSQLDATABASE

Comme pour l'ajout précédent, celui-ci s'est bien passé :

<p align="center" >
    <image src="image-6.png" width="20%">
</p>

## POLYBAYDATABASE

Après avoir créé la classe suivante dans le fichier `database` :

```java
package database;

import java.sql.SQLException;

public class PolyBayDatabase extends MySQLDatabase {

    public PolyBayDatabase(String host, int port, String databaseName, String user, String password) throws SQLException{
        super(host, port, databaseName, user, password);

    }
    
}
```

J'essaye maintenant de créer un objet de ce type dans le main avec la ligne :

```java
PolyBayDatabase db = new PolyBayDatabase("127.0.0.1", 3306, "poly_bay", "root", "");
```

Et j'obtiens l'erreur suivante :

<p align="center" >
    <image src="image-7.png" width="90%">
</p>

Ce n'est pas la première fois que j'ai cette erreur. La dernière fois que c'est arrivé était au TP précédent, lors de la connection à la base de donnée SQL. La librairie installée plus tôt est pourtant bien là, comme vu sur la capture ci-dessous :

<p align="center" >
    <image src="image-8.png" height="10%" width="200" >
</p>

C'est de ce problème dont je vous avais parlé en CM. La dernière fois, M.Chassel m'avais fais changer la version de Java pour tester mais l'erreur changeais juste. Pour l'instant, je vais simplement finir l'initialisation du projet pour avoir une base. J'essayerais par la suite de régler le problème...

## DAO

Ajout de la classe `ProductsDAO` dans le dossier dao :

```java
public class ProductsDAO {
    
    PolyBayDatabase pbDatabase;

    public ProductsDAO(){
        try{
            this.pbDatabase = new PolyBayDatabase();
        }
        catch(SQLException e)
        {
            System.err.println(e);
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
```

Du fait du non fonctionnement de l'import de la librairie JDBC, ce main ne marche pas (j'ai demandé à un camarade vérification de ce code pour être sur qu'il fonctionne).

## MODELE

Ajout du record `Product` :

```java
package model;

public record Product(
    int id,
    String name,
    String owner,
    float bid
){}

```

Et changement du main de `App` :

```java
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
```

Comme pour la partie dao, je ne peux pas tester ce main, et j'ai ici aussi demandé à un camarade vérification du code.