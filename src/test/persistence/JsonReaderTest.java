package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import model.Market;

public class JsonReaderTest {

    @Test
    void testReadFileDoesNotExist() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            Market testMarket = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadEmptyMarket() {
        JsonReader reader = new JsonReader("./data/testReadEmptyMarket.json");
        try {
            Market testMarket = reader.read();
            assertEquals("test", testMarket.getName());
            assertTrue(testMarket.getInventoryList().isEmpty());
            assertTrue(testMarket.getSaleLogs().isEmpty());
            assertEquals(0, testMarket.getTotalCost());
            assertEquals(0, testMarket.getSales());
            assertEquals(0, testMarket.getProfit());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReadMarket() {
        try {
            JsonReader reader = new JsonReader("./data/testReadMarket.json");
            Market testMarket = reader.read();

            assertEquals("test", testMarket.getName());
            assertEquals(58.75, testMarket.getTotalCost());
            assertEquals(70, testMarket.getSales());
            assertEquals(45, testMarket.getProfit());

            assertEquals(1, testMarket.getSaleLogs().size());
            assertEquals("Sold 20 of the product \"test2\" and made $45.0 profit!", testMarket.getSaleLogs().get(0));

            assertEquals(2, testMarket.getInventoryList().size());
            assertEquals("test1", testMarket.getInventoryList().get(0).getName());
            assertEquals(20, testMarket.getInventoryList().get(0).getAmountOwned());
            assertEquals(0.75, testMarket.getInventoryList().get(0).getCost());
            assertEquals(1.5, testMarket.getInventoryList().get(0).getPrice());
            assertEquals(0, testMarket.getInventoryList().get(0).getAmountSold());
            assertEquals("test2", testMarket.getInventoryList().get(1).getName());
            assertEquals(15, testMarket.getInventoryList().get(1).getAmountOwned());
            assertEquals(1.25, testMarket.getInventoryList().get(1).getCost());
            assertEquals(3.5, testMarket.getInventoryList().get(1).getPrice());
            assertEquals(20, testMarket.getInventoryList().get(1).getAmountSold());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
