import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProduct {
    @Test
    public void testConstructorNoPromotion() {
        Product product = new Product('A', 1.5, "20%");
        assertEquals('A', product.getId());
        assertEquals(1.5, product.getUnitCost());
        assertEquals("20%", product.getMarkup());
        assertNull(product.getProductPromotion());
    }

    @Test
    public void testConstructorWithPromotion() {
        Product product = new Product('A', 1.5, "20%", "30% off");
        assertEquals('A', product.getId());
        assertEquals(1.5, product.getUnitCost());
        assertEquals("20%", product.getMarkup());
        assertEquals("30% off", product.getProductPromotion());
    }

    @Test
    public void testConstructorInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Product('A', -5, "0%"));
        assertThrows(IllegalArgumentException.class, () -> new Product('A', 5, "-10%"));
        assertThrows(IllegalArgumentException.class, () -> new Product('A', 5, "wrongly formatted markup"));
        assertThrows(IllegalArgumentException.class, () -> new Product('A', 5, "10%", "wrongly formatted promotion"));
    }

    @Test
    public void testGetUnitPriceWithMarkup() {
        Product product = new Product('A', 1.5, "0.9 EUR/unit");
        assertEquals(1.5 + 0.9, product.getUnitPriceWithMarkup());

        product = new Product('A', 1.5, "90%");
        assertEquals(1.5 + 0.9 * 1.5, product.getUnitPriceWithMarkup());
    }


    private double round2(double number) {
        return Math.round(number * 100.0) / 100.0;
    }

    @Test
    public void testCheckTotalPrice() {
        int amount = 3;

        Product product = new Product('A', 1.5, "0.9 EUR/unit", "Buy 2, get 3rd free");
        assertEquals((1.5 + 0.9) * 2, product.checkTotalPrice(amount));

        assertThrows(IllegalArgumentException.class, () -> product.checkTotalPrice(-5));

        Product product1 = new Product('A', 1.5, "0.9 EUR/unit", "30% off");
        assertEquals(round2(((1.5 + 0.9) * amount) * 0.7), product1.checkTotalPrice(amount));

        Product product2 = new Product('A', 1.5, "0.9 EUR/unit");
        assertEquals(round2((1.5 + 0.9) * amount), product2.checkTotalPrice(amount));
    }
}
