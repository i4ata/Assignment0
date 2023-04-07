import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestProductCollection {
    @Test
    public void testAdd() {
        ProductCollection productCollection = new ProductCollection();
        Product product = new Product('A', 1, "0%");
        productCollection.add(product);
        assertSame(productCollection.get(product.getId()), product);
    }

    @Test
    public void testAddConflictingIDs() {
        ProductCollection productCollection = new ProductCollection();
        productCollection.add(new Product('A', 1, "0%"));
        assertThrows(IllegalArgumentException.class, () -> productCollection.add(new Product('A', 10, "5%")));
    }

    @Test
    public void testGetId() {
        ProductCollection productCollection = new ProductCollection();
        Product product = new Product('A', 1, "0%");
        productCollection.add(product);
        assertSame(product, productCollection.get(product.getId()));
    }

    @Test
    public void testGetInt() {
        ProductCollection productCollection = new ProductCollection();
        Product product = new Product('A', 1, "0%");
        productCollection.add(product);
        assertSame(product, productCollection.get(0));
    }

    @Test
    public void testRemove() {
        ProductCollection productCollection = new ProductCollection();
        Product product = new Product('A', 1, "0%");
        productCollection.add(product);
        assertNotNull(productCollection.get(product.getId()));
        productCollection.remove(product.getId());
        assertNull(productCollection.get(product.getId()));
    }
}
