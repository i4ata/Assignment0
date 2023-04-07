import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestClient {
    @Test
    public void testConstructor() {
        Client client = new Client(1, "1%", "2%", "3%");
        assertEquals(1, client.getId());
        assertEquals(1, client.getBasicClientDiscount());
        assertEquals(2, client.getAdditionalVolumeDiscountAbove10k());
        assertEquals(3, client.getAdditionalVolumeDiscountAbove30k());
    }

    @Test
    public void testConstructorInvalidDiscounts() {
        assertThrows(IllegalArgumentException.class, () -> new Client(1, "-5%", "2%", "3%"));
        assertThrows(IllegalArgumentException.class, () -> new Client(1, "105%", "2%", "3%"));

        assertThrows(IllegalArgumentException.class, () -> new Client(1, "1%", "-5%", "3%"));
        assertThrows(IllegalArgumentException.class, () -> new Client(1, "1%", "105%", "3%"));

        assertThrows(IllegalArgumentException.class, () -> new Client(1, "1%", "2%", "-5%"));
        assertThrows(IllegalArgumentException.class, () -> new Client(1, "1%", "2%", "105%"));
    }

    @Test
    public void testAddOrder() {
        Client client = new Client(1, "1%", "2%", "3%");
        client.addToOrder(new Product('A', 1, "0%", "30% off"), 10);
        assertEquals(0.99 * 0.7 * 10, client.finishOrder());
    }

    @Test
    public void testFinishOrderAbove30K() {
        Client client = new Client(1, "0%", "2%", "3%");
        client.addToOrder(new Product('A', 1, "0%"), 30000);
        assertEquals(0.97 * 1 * 30000, client.finishOrder());
    }


    @Test
    public void testFinishOrderAbove10K() {
        Client client = new Client(1, "0%", "2%", "3%");
        client.addToOrder(new Product('A', 1, "0%"), 10000);
        assertEquals(0.98 * 1 * 10000, client.finishOrder());
    }

    @Test
    public void testFinishOrderBasicAndAbove30k() {
        Client client = new Client(1, "5%", "2%", "3%");
        client.addToOrder(new Product('A', 1, "0%"), 35000);
        assertEquals(0.97 * 0.95 * 1 * 35000, client.finishOrder());
    }
}
