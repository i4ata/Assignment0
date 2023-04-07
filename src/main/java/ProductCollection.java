import java.util.*;

/**
 * Class that facilitates storing and managing multiple products.
 */
public class ProductCollection {
    private SortedMap<Character, Product> products = new TreeMap<>();

    /**
     * Add a product to the collection. Assert that there are
     * no other products with the same id already in the collection.
     * @param product
     */
    public void add(Product product) {
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("There is already a product with ID: " + product.getId());
        }
        products.put(product.getId(), product);
    }

    /**
     * Retrieve a product from the collection, keyed by product id.
     * @param id of the requested product.
     * @return the product with the corresponding id.
     */
    public Product get(char id) {
        return products.get(id);
    }

    /**
     * Remove a product from the collection by giving the product id.
     * @param id of the product to be removed.
     */
    public void remove(char id) {
        products.remove(id);
    }

    /**
     * Retrieve a product from the collection, keyed by the index of the product id in the ordered ids.
     * @param id index of the corresponding product.
     * @return the product with the corresponding id.
     */
    public Product get(int id) {
        return products.get(new ArrayList<>(products.keySet()).get(id));
    }
}
