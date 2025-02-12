package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {
    
    private Product testProduct;

    @BeforeEach
    void runBefore() {
        testProduct = new Product("test", 20, 0.75, 1.5);
    }

    @Test
    void testConstuctor() {
        assertEquals("test", testProduct.getName());
        assertEquals(20, testProduct.getAmountOwned());
        assertEquals(0.75, testProduct.getCost());
        assertEquals(1.5, testProduct.getPrice());
        assertEquals(0, testProduct.getAmountSold());
    }

    @Test
    void testIncreaseAmountSold() {
        testProduct.increaseAmountSold(10);
        assertEquals(10, testProduct.getAmountSold());
    }

    @Test
    void testMultipleIncreaseAmountSold() {
        testProduct.increaseAmountSold(5);
        assertEquals(5, testProduct.getAmountSold());

        testProduct.increaseAmountSold(8);
        assertEquals(13, testProduct.getAmountSold());
    }

    @Test
    void testDecreaseAmountOwned() {
        testProduct.decreaseAmountOwned(10);
        assertEquals(10, testProduct.getAmountOwned());
    }

    @Test
    void testMultipleDecreaseAmountOwned() {
        testProduct.decreaseAmountOwned(10);
        assertEquals(10, testProduct.getAmountOwned());

        testProduct.decreaseAmountOwned(3);
        assertEquals(7, testProduct.getAmountOwned());
    }

    @Test
    void testIncreaseAmountOwned() {
        testProduct.increaseAmountOwned(10);
        assertEquals(30, testProduct.getAmountOwned());
    }

    @Test
    void testMultipleIncreasesAmountOwned() {
        testProduct.increaseAmountOwned(10);
        assertEquals(30, testProduct.getAmountOwned());

        testProduct.increaseAmountOwned(3);
        assertEquals(33, testProduct.getAmountOwned());
    }

    @Test
    void testSetPrice() {
        assertEquals(1.5, testProduct.getPrice());
        testProduct.setPrice(3);
        assertEquals(3, testProduct.getPrice());
    }

    @Test
    void testMultipleSetPrice() {
        assertEquals(1.5, testProduct.getPrice());
        testProduct.setPrice(3);
        assertEquals(3, testProduct.getPrice());

        testProduct.setPrice(45);
        assertEquals(45, testProduct.getPrice());
    }

    @Test
    void testToString() {
        String text = testProduct.toString();
        assertEquals("Product: test, Amount owned: 20, Cost: $0.75, Price: $1.5, Amount Sold: 0", text);
    }

    @Test
    void testToJson() {
        JSONObject testJsonProduct = testProduct.toJson();
        assertEquals("test", testJsonProduct.get("name"));
        assertEquals(20, testJsonProduct.getInt("amountOwned"));
        assertEquals(0.75, testJsonProduct.getDouble("cost"));
        assertEquals(1.5, testJsonProduct.getDouble("price"));
        assertEquals(0, testJsonProduct.getInt("amountSold"));
    }
}
