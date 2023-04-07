import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents a product of the beverage producer.
 */
public class Product {
    private final char id;
    private double unitCost;
    private String markup;
    private double markupTerm;
    private String productPromotion;

    /**
     * Create a product with no promotion.
     * @param id unique identifier of the product.
     * @param unitCost in EUR.
     * @param markup either in the format "n%" or "n EUR/unit"
     */
    public Product(char id, double unitCost, String markup) {
        this(id, unitCost, markup, null);
    }

    /**
     * Create a new product.
     * @param id unique identifier of the product.
     * @param unitCost in EUR.
     * @param markup either in the format "n%" or "n EUR/unit"
     * @param productPromotion either in the format "n% off" or "Buy x, get x+1 free.
     */
    public Product(char id, double unitCost, String markup, String productPromotion) {
        this.id = id;
        this.unitCost = unitCost;
        if (unitCost <= 0) {
            throw new IllegalArgumentException("Unit cost must be positive");
        }
        this.markup = markup;
        markupTerm = getMarkupTerm();
        if (markupTerm < 0) {
            throw new IllegalArgumentException("Markup must be positive");
        }
        this.productPromotion = productPromotion;
        checkPromotion();
    }

    public char getId() {
        return id;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
        markupTerm = getMarkupTerm();
    }

    public String getProductPromotion() {
        return productPromotion;
    }

    public void setProductPromotion(String productPromotion) {
        this.productPromotion = productPromotion;
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
     * Parse the markup such that it can be added to the unit cost.
     * @return markup term. It can be added to the unit cost to get the value of the product
     * with the markup applied.
     */
    private double getMarkupTerm() {

        // Markup is in the format "n%"
        Pattern pattern1 = Pattern.compile("(\\d+(?:\\.\\d+)?)%");
        Matcher matcher = pattern1.matcher(markup);
        if (matcher.matches()) {
            return unitCost * Double.parseDouble(matcher.group(1)) / 100;
        }

        // Markup is in the format "n EUR/unit"
        Pattern pattern2 = Pattern.compile("(\\d+(?:\\.\\d+)?) EUR/unit");
        matcher = pattern2.matcher(markup);
        if (matcher.matches()) {
            return Double.parseDouble(matcher.group(1));
        }

        throw new IllegalArgumentException("Unsupported markup format.");
    }

    /**
     * Ensure that the product promotion is in a correct format and, therefore,
     * can be parsed in used in the calculations.
     */
    private void checkPromotion() {
        if (productPromotion == null) {
            return;
        }
        Pattern discountPattern = Pattern.compile("(\\d+(?:\\.\\d+)?)% off");
        Pattern bogoPattern = Pattern.compile("Buy (\\d+), get \\d+[a-z]{2} free");
        if (!(discountPattern.matcher(productPromotion).matches() || bogoPattern.matcher(productPromotion).matches())) {
            throw new IllegalArgumentException("Unsupported product promotion.");
        }
    }

    /**
     * Update the price of the product by applying the product promotion.
     * @param amount of the product requested for purchase.
     * @param price of the product with the markup included in EUR.
     * @return the new price after applying the product promotion, if applicable.
     */
    private double applyPromotion(int amount, double price) {

        // There is no promotion.
        if (productPromotion == null) {
            return amount * price;
        }

        // Promotion is in the format "n off"
        Pattern discountPattern = Pattern.compile("(\\d+(?:\\.\\d+)?)% off");
        Matcher matcher = discountPattern.matcher(productPromotion);
        if (matcher.matches()) {
            double discount = 1 - Double.parseDouble(matcher.group(1)) / 100;
            return amount * price * discount;
        }

        // Promotion is in the format "buy x, get (x+1) free"
        Pattern bogoPattern = Pattern.compile("Buy (\\d+), get \\d+[a-z]{2} free");
        matcher = bogoPattern.matcher(productPromotion);
        if (matcher.matches()) {
            int paidAmount = amount - amount / (Integer.parseInt(matcher.group(1)) + 1);
            return paidAmount * price;
        }

        return 0;
    }

    /**
     * Get the full price of the product for a specified amount.
     * @param amount of the product requested for purchase.
     * @return the full price of the product after applying markup and product promotion.
     */
    public double checkTotalPrice(int amount) {
        if(!(amount >= 0)) {
            throw new IllegalArgumentException("Cannot buy a negative amount.");
        }
        return round2(applyPromotion(amount, getUnitPriceWithMarkup()));
    }

    /**
     * Get the unit price with the markup applied.
     * @return price in EUR.
     */
    public double getUnitPriceWithMarkup() {
        return round2(unitCost + markupTerm);
    }
}
