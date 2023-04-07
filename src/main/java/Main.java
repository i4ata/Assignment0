import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Main class of the application.
 */
public class Main {
    public static void main(String[] args) {
        ProductCollection productCollection = new ProductCollection();
        productCollection.add(new Product('A', 0.52, "80%"));
        productCollection.add(new Product('B', 0.38, "120%", "30% off"));
        productCollection.add(new Product('C', 0.41, "0.9 EUR/unit"));
        productCollection.add(new Product('D', 0.60, "1 EUR/unit", "Buy 2, get 3rd free"));

        ClientCollection clientCollection = new ClientCollection();
        clientCollection.add(new Client(1, "5%", "0%", "2%"));
        clientCollection.add(new Client(2, "4%", "1%", "2%"));
        clientCollection.add(new Client(3, "3%", "1%", "3%"));
        clientCollection.add(new Client(4, "2%", "3%", "5%"));
        clientCollection.add(new Client(5, "0%", "5%", "7%"));

        Client client = clientCollection.get(Integer.parseInt(args[0]));

        System.out.println("--------------------------------");
        System.out.println("SUMMARY ORDER CLIENT " + client.getId());
        System.out.println("--------------------------------\n");
        for (int i = 1; i < args.length; i++) {
            int amount = Integer.parseInt(args[i]);
            if (amount != 0) {
                client.addToOrder(productCollection.get(i-1), amount);
            }
        }
        client.finishOrder();
        System.out.println("--------------------------------");
    }
}