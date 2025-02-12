package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a Market with a name, a list of Products called inventory, 
// a log of sales made, total cost of all products in inventory, total sale money made,
// and total profit made.
public class Market {

    private String name;
    private ArrayList<Product> inventoryList;
    private ArrayList<String> saleLogs;
    private double totalCost;
    private double sales;
    private double profit;
    private EventLog log;

    // EFFECTS: Creates a default Market with a name, an empty list of products,
    // empty sale log, and total cost, sales, and profit at zero
    public Market(String name) {
        this.name = name;
        inventoryList = new ArrayList<>();
        saleLogs = new ArrayList<>();
        totalCost = 0;
        sales = 0;
        profit = 0;
        log = EventLog.getInstance();
    }

    // REQUIRES: totalCost >= 0, sales >= 0
    // EFFECTS: Creates a Market with a name, an empty list of products,
    // empty sale log, and total cost at zero, totalCost as totalCost, sales as
    // sales, and profit as profit
    public Market(String name, double totalCost, double sales, double profit) {
        this.name = name;
        inventoryList = new ArrayList<>();
        saleLogs = new ArrayList<>();
        this.totalCost = totalCost;
        this.sales = sales;
        this.profit = profit;
        log = EventLog.getInstance();
    }

    // REQUIRES: amountOwned >= 0, cost > 0, price > 0,
    // name is different from other Product names already in the list
    // MODIFIES: this
    // EFFECTS: Adds a product with name, amount owned, cost, price, and amount
    // sold, initially at 0, to the list and adds the cost of amount owned of
    // product to total cost
    public void addProduct(String name, int amountOwned, double cost, double price) {
        Product tempProduct = new Product(name, amountOwned, cost, price);
        inventoryList.add(tempProduct);
        totalCost += amountOwned * cost;
        log.logEvent(new Event("Added product \"" + name + "\" to Market"));
    }

    // MODIFIES: this
    // EFFECTS: Removes the product with name from the list and subtracts the amount
    // owned cost from total cost
    public void removeProduct(String name) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).getName().equals(name)) {
                totalCost -= inventoryList.get(i).getAmountOwned() * inventoryList.get(i).getCost();
                inventoryList.remove(i);
                break;
            }
        }
        log.logEvent(new Event("Product \"" + name + "\" removed from Market"));
    }

    // REQUIRES: amount <= amount owned of the product being sold, amount > 0
    // MODIFIES: this
    // EFFECTS: Makes a sale of the product by name and of the amount provided by
    // amount, therefore decreasing the amount owned of the product, makes sales,
    // makes profit then logs sale
    public void makeSale(String name, int amount) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).getName().equals(name)) {
                Product tempProduct = inventoryList.get(i);
                tempProduct.decreaseAmountOwned(amount);
                tempProduct.increaseAmountSold(amount);
                makeSaleMoney(tempProduct.getPrice(), amount);
                makeProfit(tempProduct.getPrice(), tempProduct.getCost(), amount);
                logSale(name, tempProduct.getPrice(), tempProduct.getCost(), amount);
                break;
            }
        }
        log.logEvent(new Event("Sold " + amount + " of product \"" + name + "\"!"));
    }

    // MODIFIES: this
    // EFFECTS: Add a log of the sale including which product, amount sold, and
    // profit to the log list
    private void logSale(String name, double price, double cost, int amount) {

        double tempSale = price * amount;
        double tempProfit = tempSale - cost * amount;

        String text = "Sold " + amount + " of the product \"" + name + "\" and made $" + tempProfit + " profit!";
        saleLogs.add(text);
    }

    // MODIFIES: this
    // EFFECTS: Calculate new sale money made and add to total sales made
    private void makeSaleMoney(double price, int amount) {
        sales += price * amount;
    }

    // MODIFIES: this
    // EFFECTS: Calculate new profits made and add to total profit made
    private void makeProfit(double price, double cost, int amount) {
        profit += (price - cost) * amount;
    }

    // EFFECTS: Returns the total cost of all products in inventory verses the total
    // sales made and the total profit made
    public String costSalesProfit() {
        return "Total Cost: $" + totalCost + ", Total sales: $" + sales + ", Total profit: $" + profit;
    }

    // MODIFIES: this
    // EFFECTS: Increases product amount owned of string name by amount and adding
    // the extra costs to total cost
    public void increaseAmountOwnedOfProduct(String name, int amount) {
        findProduct(name).increaseAmountOwned(amount);
        totalCost += amount * findProduct(name).getCost();
    }

    // EFFECTS: Returns the product with string name if in list, otherwise null
    public Product findProduct(String name) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (name.equals(inventoryList.get(i).getName())) {
                return inventoryList.get(i);
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSaleLogs() {
        return saleLogs;
    }

    // EFFECTS: Returns the details of the sale logs as one string
    public String getSaleLogsAsString() {
        String logs = "\r\n";
        for (String s : saleLogs) {
            logs += s + "\r\n";
        }
        return logs;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getSales() {
        return sales;
    }

    public double getProfit() {
        return profit;
    }

    public ArrayList<Product> getInventoryList() {
        return inventoryList;
    }

    public EventLog getEventLog() {
        return log;
    }

    // EFFECTS: Returns the details of the Product list, inventoryList, in the form
    // of a String
    public String toString() {
        String text = "\r\n";
        for (Product p : inventoryList) {
            text += p.toString() + "\r\n";
        }
        return text;
    }

    // EFFECTS: returns Market as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("inventoryList", productsToJson());
        json.put("saleLogs", saleLogsToJson());
        json.put("totalCost", totalCost);
        json.put("sales", sales);
        json.put("profit", profit);
        return json;
    }

    // EFFECTS: returns inventoryList as a json array
    private JSONArray productsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product t : inventoryList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns inventoryList as a json array
    private JSONArray saleLogsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < saleLogs.size(); i++) {
            JSONObject json = new JSONObject();
            String key = "" + i;
            json.put(key, saleLogs.get(i));
            jsonArray.put(json);
        }

        return jsonArray;
    }
}
