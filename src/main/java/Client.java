/**
 * Class that represents a client of the baverage producer.
 */
public class Client {
    private final int id;
    private double basicClientDiscount;
    private double additionalVolumeDiscountAbove10k;
    private double additionalVolumeDiscountAbove30k;
    private double totalEURSpent = 0;

    /**
     * Create a new client.
     * @param id unique identifier of the client.
     * @param basicClientDiscount must be between 0% and 100%.
     * @param additionalVolumeDiscountAbove10k must be between 0% and 100%.
     * @param additionalVolumeDiscountAbove30k must be between 0% and 100%.
     */
    public Client(int id, String basicClientDiscount, String additionalVolumeDiscountAbove10k, String additionalVolumeDiscountAbove30k) {
        this.id = id;
        this.basicClientDiscount = Double.parseDouble(removeLastCharacter(basicClientDiscount));
        this.additionalVolumeDiscountAbove10k = Double.parseDouble(removeLastCharacter(additionalVolumeDiscountAbove10k));
        this.additionalVolumeDiscountAbove30k = Double.parseDouble(removeLastCharacter(additionalVolumeDiscountAbove30k));
        if (!(this.basicClientDiscount >= 0 && this.basicClientDiscount <= 100)) {
            throw new IllegalArgumentException("Basic Client Discount needs to be between 0% and 100%");
        }
        if (!(this.additionalVolumeDiscountAbove10k >= 0 && this.additionalVolumeDiscountAbove10k <= 100)) {
            throw new IllegalArgumentException("Additional Volume Discount Above EUR 10 000 needs to be a between 0% and 100%");
        }
        if (!(this.additionalVolumeDiscountAbove30k >= 0 && this.additionalVolumeDiscountAbove30k <= 100)) {
            throw new IllegalArgumentException("Additional Volume Discount Above EUR 30 000 needs to be a between 0% and 100%");
        }
    }

    /**
     * Used to omit the '%' of the discount strings.
     * @param s
     * @return s with the last character omitted.
     */
    private String removeLastCharacter(String s) {
        return s.substring(0, s.length() - 1);
    }

    public int getId() {
        return id;
    }

    public double getBasicClientDiscount() {
        return basicClientDiscount;
    }

    public void setBasicClientDiscount(double basicClientDiscount) {
        this.basicClientDiscount = basicClientDiscount;
    }

    public double getAdditionalVolumeDiscountAbove10k() {
        return additionalVolumeDiscountAbove10k;
    }

    public void setAdditionalVolumeDiscountAbove10k(double additionalVolumeDiscountAbove10k) {
        this.additionalVolumeDiscountAbove10k = additionalVolumeDiscountAbove10k;
    }

    public double getAdditionalVolumeDiscountAbove30k() {
        return additionalVolumeDiscountAbove30k;
    }

    public void setAdditionalVolumeDiscountAbove30k(double additionalVolumeDiscountAbove30k) {
        this.additionalVolumeDiscountAbove30k = additionalVolumeDiscountAbove30k;
    }

    /**
     * Used to round a number (price in EUR) to 2 decimals.
     * @param number to be rounded.
     * @return the number rounded to 2 decimals.
     */
    private double round2(double number) {
        return Math.round(number * 100.0) / 100.0;
    }

    /**
     * Add a specified amount of a specified product to the client's order.
     * Print details and update the client's expenditure.
     * @param product to be added to the order.
     * @param amount of the product to be added to the order.
     */
    public void addToOrder(Product product, int amount) {
        double totalProductPrice = product.checkTotalPrice(amount);
        double unitProductPriceWithMarkup = product.getUnitPriceWithMarkup();
        System.out.println("Product: " + product.getId());
        System.out.println("Amount: " + amount);
        System.out.println("Unit price: " + unitProductPriceWithMarkup);
        if (round2(unitProductPriceWithMarkup * amount) != totalProductPrice) {
            System.out.println("Mean promotional unit price: " + totalProductPrice / amount);
        }
        System.out.println("Total: " + totalProductPrice + '\n');
        totalEURSpent = round2(totalEURSpent + totalProductPrice);
    }

    /**
     * Finish order and apply the client's discounts.
     * Print details and update the client's expenditure.
     * @return the total amount spent for the order in EUR.
     */
    public double finishOrder() {
        System.out.println("Total price before client discounts: " + totalEURSpent + " EUR.");
        if (basicClientDiscount != 0) {
            totalEURSpent = round2(totalEURSpent * (100 - basicClientDiscount) / 100);
            System.out.println("Total price after " + basicClientDiscount + "% client discount: " + totalEURSpent + " EUR");
        }
        if (totalEURSpent >= 30000 && additionalVolumeDiscountAbove30k > 0) {
            totalEURSpent = round2(totalEURSpent * (100 - additionalVolumeDiscountAbove30k) / 100);
            System.out.println("Total price after " + additionalVolumeDiscountAbove30k + "% client discount for spending above 30 000 EUR: " + totalEURSpent + " EUR");
        } else if (totalEURSpent >= 10000) {
            totalEURSpent = round2(totalEURSpent * (100 - additionalVolumeDiscountAbove10k) / 100);
            System.out.println("Total price after " + additionalVolumeDiscountAbove10k + "% client discount for spending above 10 000 EUR: " + totalEURSpent + " EUR");
        }
        System.out.println("Total price: " + totalEURSpent + " EUR");
        return totalEURSpent;
    }
}
