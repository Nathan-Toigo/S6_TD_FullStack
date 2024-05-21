import database.PolyBayDatabase;

public class App {
    public static void main(String[] args) throws Exception {
        PolyBayDatabase db = new PolyBayDatabase("127.0.0.1", 3306, "poly_bay", "root", "");

    }
}
