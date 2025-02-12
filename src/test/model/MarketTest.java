package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarketTest {

    private Market testMarket;

    @BeforeEach
    void runBefore() {
        testMarket = new Market("testMarket");
    }

    @Test
    void testConstuctor() {
        assertEquals("testMarket", testMarket.getName());
        assertTrue(testMarket.getInventoryList().isEmpty());
        assertEquals(0, testMarket.getInventoryList().size());
        assertTrue(testMarket.getSaleLogs().isEmpty());
        assertEquals(0, testMarket.getSaleLogs().size());
        assertEquals(0, testMarket.getTotalCost());
        assertEquals(0, testMarket.getSales());
        assertEquals(0, testMarket.getProfit());
    }

    @Test
    void testConstuctorWithSalesAndProfit() {
        Market testMarketSpecial = new Market("test", 35, 10, 20);
        assertEquals("test", testMarketSpecial.getName());
        assertTrue(testMarketSpecial.getInventoryList().isEmpty());
        assertEquals(0, testMarketSpecial.getInventoryList().size());
        assertTrue(testMarketSpecial.getSaleLogs().isEmpty());
        assertEquals(0, testMarketSpecial.getSaleLogs().size());
        assertEquals(35, testMarketSpecial.getTotalCost());
        assertEquals(10, testMarketSpecial.getSales());
        assertEquals(20, testMarketSpecial.getProfit());
    }

    @Test
    void testAddProduct() {
        testMarket.addProduct("test", 20, 0.75, 1.5);

        assertEquals(1, testMarket.getInventoryList().size());
        assertEquals("test", testMarket.getInventoryList().get(0).getName());
        assertEquals(20, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(0.75, testMarket.getInventoryList().get(0).getCost());
        assertEquals(1.5, testMarket.getInventoryList().get(0).getPrice());
        assertEquals(0, testMarket.getInventoryList().get(0).getAmountSold());
        double costTotal1 = 20 * 0.75;
        assertEquals(costTotal1, testMarket.getTotalCost());
    }

    @Test
    void testMultipleAddProduct() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);

        assertEquals(1, testMarket.getInventoryList().size());
        assertEquals("test1", testMarket.getInventoryList().get(0).getName());
        assertEquals(20, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(0.75, testMarket.getInventoryList().get(0).getCost());
        assertEquals(1.5, testMarket.getInventoryList().get(0).getPrice());
        assertEquals(0, testMarket.getInventoryList().get(0).getAmountSold());
        double costTotal1 = 20 * 0.75;
        assertEquals(costTotal1, testMarket.getTotalCost());

        testMarket.addProduct("test2", 35, 1.25, 3.5);

        assertEquals(2, testMarket.getInventoryList().size());
        assertEquals("test2", testMarket.getInventoryList().get(1).getName());
        assertEquals(35, testMarket.getInventoryList().get(1).getAmountOwned());
        assertEquals(1.25, testMarket.getInventoryList().get(1).getCost());
        assertEquals(3.5, testMarket.getInventoryList().get(1).getPrice());
        assertEquals(0, testMarket.getInventoryList().get(1).getAmountSold());
        double costTotal2 = (20 * 0.75) + (35 * 1.25);
        assertEquals(costTotal2, testMarket.getTotalCost());
    }

    @Test
    void testRemoveProduct() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);
        assertEquals(2, testMarket.getInventoryList().size());
        double costTotal = (20 * 0.75) + (35 * 1.25);
        assertEquals(costTotal, testMarket.getTotalCost());

        testMarket.removeProduct("test1");

        assertEquals(1, testMarket.getInventoryList().size());
        assertEquals("test2", testMarket.getInventoryList().get(0).getName());
        assertEquals(35, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(1.25, testMarket.getInventoryList().get(0).getCost());
        assertEquals(3.5, testMarket.getInventoryList().get(0).getPrice());
        assertEquals(0, testMarket.getInventoryList().get(0).getAmountSold());
        double costTotal2 = (35 * 1.25);
        assertEquals(costTotal2, testMarket.getTotalCost());
    }

    @Test
    void testMultipleRemoveProduct() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);
        assertEquals(2, testMarket.getInventoryList().size());
        double costTotal = (20 * 0.75) + (35 * 1.25);
        assertEquals(costTotal, testMarket.getTotalCost());

        testMarket.removeProduct("test2");

        assertEquals(1, testMarket.getInventoryList().size());
        assertEquals("test1", testMarket.getInventoryList().get(0).getName());
        assertEquals(20, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(0.75, testMarket.getInventoryList().get(0).getCost());
        assertEquals(1.5, testMarket.getInventoryList().get(0).getPrice());
        assertEquals(0, testMarket.getInventoryList().get(0).getAmountSold());
        double costTotal1 = (20 * 0.75);
        assertEquals(costTotal1, testMarket.getTotalCost());

        testMarket.removeProduct("test1");

        assertTrue(testMarket.getInventoryList().isEmpty());
        assertEquals(0, testMarket.getInventoryList().size());
        assertEquals(0, testMarket.getTotalCost());
    }

    @Test
    void testRemoveProductNotInList() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);
        assertEquals(2, testMarket.getInventoryList().size());
        double costTotal = (20 * 0.75) + (35 * 1.25);
        assertEquals(costTotal, testMarket.getTotalCost());

        testMarket.removeProduct("test3");

        assertEquals(2, testMarket.getInventoryList().size());
        assertEquals("test1", testMarket.getInventoryList().get(0).getName());
        assertEquals(20, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(0.75, testMarket.getInventoryList().get(0).getCost());
        assertEquals(1.5, testMarket.getInventoryList().get(0).getPrice());
        assertEquals(0, testMarket.getInventoryList().get(0).getAmountSold());
        assertEquals("test2", testMarket.getInventoryList().get(1).getName());
        assertEquals(35, testMarket.getInventoryList().get(1).getAmountOwned());
        assertEquals(1.25, testMarket.getInventoryList().get(1).getCost());
        assertEquals(3.5, testMarket.getInventoryList().get(1).getPrice());
        assertEquals(0, testMarket.getInventoryList().get(1).getAmountSold());
        assertEquals(costTotal, testMarket.getTotalCost());
    }

    @Test
    void testToString() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);

        String text = testMarket.toString();
        String product1 = "Product: test1, Amount owned: 20, Cost: $0.75, Price: $1.5, Amount Sold: 0";
        String product2 = "Product: test2, Amount owned: 35, Cost: $1.25, Price: $3.5, Amount Sold: 0";

        assertEquals("\r\n" + product1 + "\r\n" + product2 + "\r\n", text);
    }

    @Test
    void testMakeSale() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);

        testMarket.makeSale("test1", 11);
        assertEquals(9, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(11, testMarket.getInventoryList().get(0).getAmountSold());

        String text1 = testMarket.getSaleLogs().get(0);
        String correctText1 = "Sold 11 of the product \"test1\" and made $8.25 profit!";
        assertEquals(1, testMarket.getSaleLogs().size());
        assertEquals(correctText1, text1);

        assertEquals((1.5 * 11), testMarket.getSales());
        assertEquals(((1.5 * 11) - (0.75 * 11)), testMarket.getProfit());
    }

    @Test
    void testMultipleMakeSale() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);

        testMarket.makeSale("test1", 11);
        assertEquals(9, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(11, testMarket.getInventoryList().get(0).getAmountSold());

        testMarket.makeSale("test1", 3);
        assertEquals(6, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(14, testMarket.getInventoryList().get(0).getAmountSold());

        testMarket.makeSale("test2", 15);
        assertEquals(20, testMarket.getInventoryList().get(1).getAmountOwned());
        assertEquals(15, testMarket.getInventoryList().get(1).getAmountSold());

        String text2 = testMarket.getSaleLogs().get(2);
        String correctText2 = "Sold 15 of the product \"test2\" and made $33.75 profit!";

        assertEquals(3, testMarket.getSaleLogs().size());
        assertEquals(correctText2, text2);

        assertEquals(73.5, testMarket.getSales());
        assertEquals(44.25, testMarket.getProfit());
    }

    @Test
    void testMakeSaleNotInList() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);

        testMarket.makeSale("test3", 11);
        assertEquals(20, testMarket.getInventoryList().get(0).getAmountOwned());
        assertEquals(0, testMarket.getInventoryList().get(0).getAmountSold());
        assertEquals(35, testMarket.getInventoryList().get(1).getAmountOwned());
        assertEquals(0, testMarket.getInventoryList().get(1).getAmountSold());
        assertEquals(0, testMarket.getSaleLogs().size());
    }

    @Test
    void testCostSalesProfit() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);

        testMarket.makeSale("test1", 11);
        testMarket.makeSale("test2", 15);

        double cost = testMarket.getTotalCost();
        double sales = testMarket.getSales();
        double profit = testMarket.getProfit();
        String text = "Total Cost: $" + cost + ", Total sales: $" + sales + ", Total profit: $" + profit;

        assertEquals(text, testMarket.costSalesProfit());
    }

    @Test
    void testIncreaseAmountOwnedOfProduct() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);

        double costTotal = (20 * 0.75);
        assertEquals(costTotal, testMarket.getTotalCost());

        testMarket.increaseAmountOwnedOfProduct("test1", 2);

        costTotal += (2 * 0.75);
        assertEquals(costTotal, testMarket.getTotalCost());
    }

    @Test
    void testFindProduct() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);

        assertEquals("test1", testMarket.findProduct("test1").getName());
        assertEquals(20, testMarket.findProduct("test1").getAmountOwned());
        assertEquals(0.75, testMarket.findProduct("test1").getCost());
        assertEquals(1.5, testMarket.findProduct("test1").getPrice());
        assertEquals(0, testMarket.findProduct("test1").getAmountSold());
    }

    @Test
    void testFindProductNotInList() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);

        assertEquals(null, testMarket.findProduct("testy"));
    }

    @Test
    void testGetSaleLogsAsString() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);

        testMarket.makeSale("test1", 11);
        String correctText = "\r\n" + "Sold 11 of the product \"test1\" and made $8.25 profit!" + "\r\n";

        assertEquals(correctText, testMarket.getSaleLogsAsString());

        testMarket.makeSale("test2", 15);
        correctText += "Sold 15 of the product \"test2\" and made $33.75 profit!" + "\r\n";

        assertEquals(correctText, testMarket.getSaleLogsAsString());
    }

    @Test
    void testToJson() {
        testMarket.addProduct("test1", 20, 0.75, 1.5);
        testMarket.addProduct("test2", 35, 1.25, 3.5);
        testMarket.makeSale("test2", 20);
        String correctText = "Sold 20 of the product \"test2\" and made $45.0 profit!";
        JSONObject testJsonMarket = testMarket.toJson();

        assertEquals("testMarket", testJsonMarket.get("name"));
        assertEquals(58.75, testJsonMarket.getDouble("totalCost"));
        assertEquals(70, testJsonMarket.getDouble("sales"));
        assertEquals(45, testJsonMarket.getDouble("profit"));

        JSONObject testSale = testJsonMarket.getJSONArray("saleLogs").getJSONObject(0);
        assertEquals(correctText, testSale.get("0"));

        JSONObject testJsonProduct1 = testJsonMarket.getJSONArray("inventoryList").getJSONObject(0);
        assertEquals("test1", testJsonProduct1.get("name"));
        assertEquals(20, testJsonProduct1.getInt("amountOwned"));
        assertEquals(0.75, testJsonProduct1.getDouble("cost"));
        assertEquals(1.5, testJsonProduct1.getDouble("price"));
        assertEquals(0, testJsonProduct1.getInt("amountSold"));

        JSONObject testJsonProduct2 = testJsonMarket.getJSONArray("inventoryList").getJSONObject(1);
        assertEquals("test2", testJsonProduct2.get("name"));
        assertEquals(15, testJsonProduct2.getInt("amountOwned"));
        assertEquals(1.25, testJsonProduct2.getDouble("cost"));
        assertEquals(3.5, testJsonProduct2.getDouble("price"));
        assertEquals(20, testJsonProduct2.getInt("amountSold"));
    }
}
