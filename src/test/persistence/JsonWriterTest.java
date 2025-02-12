package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import model.Market;

public class JsonWriterTest {

    private Market testMarket;

    @BeforeEach
    void runBefore() {
        testMarket = new Market("test");
    }

    @Test
    void testWriteInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyMarket() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyMarket.json");
            writer.open();
            writer.write(testMarket);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyMarket.json");
            testMarket = reader.read();
            assertEquals("test", testMarket.getName());
            assertTrue(testMarket.getInventoryList().isEmpty());
            assertTrue(testMarket.getSaleLogs().isEmpty());
            assertEquals(0, testMarket.getTotalCost());
            assertEquals(0, testMarket.getSales());
            assertEquals(0, testMarket.getProfit());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteMarket() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriteMarket.json");
            testMarket.addProduct("test1", 20, 0.75, 1.5);
            testMarket.addProduct("test2", 35, 1.25, 3.5);
            testMarket.makeSale("test2", 20);

            writer.open();
            writer.write(testMarket);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteMarket.json");
            testMarket = reader.read();

            assertEquals("test", testMarket.getName());
            assertEquals(58.75, testMarket.getTotalCost());
            assertEquals(70, testMarket.getSales());
            assertEquals(45, testMarket.getProfit());

            assertEquals(1, testMarket.getSaleLogs().size());
            assertEquals("Sold 20 of the product \"test2\" and made $45.0 profit!", testMarket.getSaleLogs().get(0));

            assertEquals(2, testMarket.getInventoryList().size());
            assertEquals("test1", testMarket.getInventoryList().get(0).getName());
            assertEquals("test2", testMarket.getInventoryList().get(1).getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
