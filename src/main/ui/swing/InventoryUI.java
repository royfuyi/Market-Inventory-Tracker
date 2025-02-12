package ui.swing;

import model.Market;
import model.Product;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

// Represents A Inventory UI JPanel class that displays the inventory
public class InventoryUI extends DisplayInfoUI {

    private JPanel inventory;
    private JLabel inventoryTitle;
    private JPanel inventoryText;

    // EFFECTS: Constructs A Inventory UI JPanel that displays the inventory of
    // market
    public InventoryUI(Market market) {
        super(market);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Formats the displayed text in panel
    protected void formatText() {
        inventory = new JPanel(new GridBagLayout());
        inventoryTitle = new JLabel("Inventory List");
        inventoryTitle.setFont(new Font("Arial", Font.BOLD, 30));
        inventoryText = new JPanel(new GridBagLayout());
        inventory.add(inventoryText, format);

        inventoryTitle.setHorizontalAlignment(SwingConstants.CENTER);
        inventoryTitle.setVerticalAlignment(SwingConstants.TOP);
        add(inventoryTitle, BorderLayout.PAGE_START);
        add(inventory);
    }

    @Override
    // REQUIRES: market != null
    // MODIFIES: this
    // EFFECTS: Updates the text according to information from market
    public void updateText(Market market) {
        ArrayList<Product> inventory = market.getInventoryList();
        inventoryText.removeAll();
        if (inventory.isEmpty()) {
            JLabel temp = new JLabel("No Products Added");
            temp.setFont(new Font("Arial", Font.ITALIC, 20));
            temp.setForeground(Color.GRAY);
            inventoryText.add(temp, format);
        } else {
            for (Product p : inventory) {
                JLabel temp = new JLabel(p.toString());
                temp.setFont(new Font("Arial", Font.PLAIN, 20));
                temp.setForeground(Color.BLACK);
                inventoryText.add(temp, format);
            }
        }
    }
}
