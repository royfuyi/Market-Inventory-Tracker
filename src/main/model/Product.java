package model;

import org.json.JSONObject;

// Represents a product with a name, number of such product owned, 
// the cost to manufacture/make that product, the selling price of said product,
// and the total amount sold of said product
public class Product {

    private String name;
    private int amountOwned;
    private double cost;
    private double price;
    private int amountSold;

    // REQUIRES: amountOwned >= 0, cost > 0, price > 0
    // EFFECTS: Creates a Product with a name, amount owned, cost, selling price,
    // and amount sold, with amount sold initially at zero
    public Product(String name, int amountOwned, double cost, double price) {
        this.name = name;
        this.amountOwned = amountOwned;
        this.cost = cost;
        this.price = price;
        amountSold = 0;
    }

    // REQUIRES: amount > 0 && amount <= amount owned
    // MODIFIES: this
    // EFFECTS: Increases the amount sold of the Product by amount
    public void increaseAmountSold(int amount) {
        amountSold += amount;
    }

    // REQUIRES: amount > 0 && amount <= amount owned
    // MODIFIES: this
    // EFFECTS: Decreases the amount owned of the Product by the int amount provided
    public void decreaseAmountOwned(int amount) {
        amountOwned -= amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: increases the amount owned of the Product by the int amount provided
    public void increaseAmountOwned(int amount) {
        amountOwned += amount;
    }

    public String getName() {
        return name;
    }

    public int getAmountOwned() {
        return amountOwned;
    }

    public double getCost() {
        return cost;
    }

    public double getPrice() {
        return price;
    }

    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: Changes the price of the Product to the price provided
    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmountSold() {
        return amountSold;
    }

    // EFFECTS: Returns the details of the Product in the form of a String
    public String toString() {

        String text = "Product: " + name;

        text += ", Amount owned: " + amountOwned;
        text += ", Cost: $" + cost;
        text += ", Price: $" + price;
        text += ", Amount Sold: " + amountSold;

        return text;
    }

    // EFFECTS: returns Market as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amountOwned", amountOwned);
        json.put("cost", cost);
        json.put("price", price);
        json.put("amountSold", amountSold);
        return json;
    }
}
