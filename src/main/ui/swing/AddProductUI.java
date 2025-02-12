package ui.swing;

import java.awt.*;
import javax.swing.*;

import model.Market;

// Represents a JPanel UI for adding new products
public class AddProductUI extends TextFieldUI {

    private JPanel textPanel;
    private JLabel title;
    private JLabel warning;
    private JLabel secondWarning;
    private JLabel status;
    private JLabel instructions;
    private JTextField name;
    private JTextField amountOwned;
    private JTextField cost;
    private JTextField price;

    // EFFECTS: Constructs a UI that contains buttons and fields to add a new
    // product
    public AddProductUI() {
        super();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets up the subpanel containing the title, warning texts,
    // and status updates text
    protected void setUpText() {
        textPanel = new JPanel(new GridBagLayout());
        title = new JLabel("Add a New Product!");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        String warningText = "NOTE: Name of product must be not already in the list, "
                + "amount owned must be a integer greater or equal to zero, "
                + "and cost and price must be a number greater than one";

        warning = new JLabel(warningText);
        warning.setFont(new Font("Arial", Font.ITALIC, 15));
        warning.setForeground(Color.GRAY);

        secondWarning = new JLabel("**ALL FIELDS MUST BE FILLED OUT**");
        secondWarning.setFont(new Font("Arial", Font.ITALIC, 15));
        secondWarning.setForeground(Color.RED);

        status = new JLabel(" ");
        status.setFont(new Font("Arial", Font.ITALIC, 20));

        instructions = new JLabel("Please enter the name, amount owned, cost, and price of the new product: ");
        instructions.setFont(new Font("Arial", Font.PLAIN, 20));

        textPanel.add(title, format);
        textPanel.add(secondWarning, format);
        textPanel.add(warning, format);
        textPanel.add(status, format);
        textPanel.add(instructions, format);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets up fields for product information and submit button
    protected void setUpField() {
        name = new JTextField("Name of product");
        textField(name);

        amountOwned = new JTextField("Amount owned of product");
        textField(amountOwned);

        cost = new JTextField("Cost to make product");
        textField(cost);

        price = new JTextField("Selling price of product");
        textField(price);

        submit = new JButton("Add this product");
        submit.setFont(new Font("Arial", Font.PLAIN, 30));
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Format and add components to the main AddProductUI panel
    protected void formatPanel() {
        setLayout(new GridLayout(0, 1, 0, 1));
        add(textPanel);
        add(name, format);
        add(amountOwned, format);
        add(cost, format);
        add(price, format);
        add(submit, format);
    }

    // REQUIRES: market != null
    // MODIFIES: this, market
    // EFFECTS: Takes in input from fields to add new product to market if fields
    // fit requirements and updates status text accordingly
    public void addProduct(Market market) {
        String name = this.name.getText();
        int amountOwned = -1;
        double cost = -1;
        double price = -1;
        status.setForeground(Color.RED);
        try {
            amountOwned = Integer.parseInt(this.amountOwned.getText());
            cost = Double.parseDouble(this.cost.getText());
            price = Double.parseDouble(this.price.getText());
            if (market.findProduct(name) != null) {
                status.setText("A product of name \"" + name + "\" already exists, please try again.");
            } else if (amountOwned < 0) {
                status.setText("Amount owned must be greater or equal to zero, please try again.");
            } else if ((cost <= 0) || (price <= 0)) {
                status.setText("Cost and Price must be greater than zero, please try again.");
            } else {
                market.addProduct(name, amountOwned, cost, price);
                addedMessage();
                resetFields();
            }
        } catch (NumberFormatException e) {
            status.setText("Amount owned must be an integer, and cost and price must be numbers, please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets status text to show that product was added
    private void addedMessage() {
        status.setForeground(Color.GREEN);
        status.setText("Product added!");
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Resets the text to default
    public void resetMessage() {
        status.setText(" ");
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Resets fields to placeholder text and color
    protected void resetFields() {
        name.setText("Name of product");
        textField(name);
        name.setFocusable(false);
        name.setFocusable(true);

        amountOwned.setText("Amount owned of product");
        textField(amountOwned);

        cost.setText("Cost to make product");
        textField(cost);

        price.setText("Selling price of product");
        textField(price);
    }
}
