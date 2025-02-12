package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Market;
import model.Product;

// Represents a reader that reads Market from JSON data stored in file
// Referenced: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String sourceFile;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    // EFFECTS: reads market from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Market read() throws IOException {
        String jsonText = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonText);
        return parseMarket(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String sourceFile) throws IOException {
        StringBuilder textBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> textBuilder.append(s));
        }

        return textBuilder.toString();
    }

    // EFFECTS: parses Market from JSON object and returns it
    private Market parseMarket(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double totalCost = jsonObject.getDouble("totalCost");
        Double sales = jsonObject.getDouble("sales");
        Double profit = jsonObject.getDouble("profit");
        Market market = new Market(name, totalCost, sales, profit);
        addProducts(market, jsonObject);
        addSaleLogs(market, jsonObject);
        return market;
    }

    // MODIFIES: market
    // EFFECTS: parses the inventory list of products from JSON object and adds them
    // to Market
    private void addProducts(Market market, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventoryList");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProduct(market, nextProduct);
        }
    }

    // MODIFIES: market
    // EFFECTS: parses Product from JSON object and adds it to workroom
    private void addProduct(Market market, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int amountOwned = jsonObject.getInt("amountOwned");
        double cost = jsonObject.getDouble("cost");
        double price = jsonObject.getDouble("price");
        int amountSold = jsonObject.getInt("amountSold");
        Product product = new Product(name, amountOwned, cost, price);
        product.increaseAmountSold(amountSold);
        market.getInventoryList().add(product);
    }

    // MODIFIES: market
    // EFFECTS: parses the inventory list of products from JSON object and adds them
    // to Market
    private void addSaleLogs(Market market, JSONObject jsonObject) {
        JSONArray saleLogs = jsonObject.getJSONArray("saleLogs");
        int index = 0;
        for (Object j : saleLogs) {
            JSONObject json = (JSONObject) j;
            String stringIndex = "" + index;
            String text = json.getString(stringIndex);
            market.getSaleLogs().add(text);
            index++;
        }
    }
}
