package ui.swing;

import java.awt.*;
import javax.swing.*;

import model.Market;

// Represents a JPanel UI for making sales
public class MakeSaleUI extends TextFieldUI {

    private JPanel textPanel;
    private JLabel title;
    private JLabel warning;
    private JLabel secondWarning;
    private JLabel status;
    private JLabel instructions;
    private JTextField name;
    private JTextField amountSold;

    // EFFECTS: Constructs a UI that contains buttons and fields to make a sale
    public MakeSaleUI() {
        super();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets up the subpanel containing the title, warning texts, and status
    // updates text
    protected void setUpText() {
        textPanel = new JPanel(new GridBagLayout());
        title = new JLabel("Make a Sale!");
        title.setFont(new Font("Arial", Font.BOLD, 40));

        String warningText = "NOTE: Name of product must be in the list "
                + " and amount sold must be a positive non-zero integer, "
                + "smaller or equal to amount owned of that product";

        warning = new JLabel(warningText);
        warning.setFont(new Font("Arial", Font.ITALIC, 15));
        warning.setForeground(Color.GRAY);

        secondWarning = new JLabel("**ALL FIELDS MUST BE FILLED OUT**");
        secondWarning.setFont(new Font("Arial", Font.ITALIC, 20));
        secondWarning.setForeground(Color.RED);

        status = new JLabel(" ");
        status.setFont(new Font("Arial", Font.ITALIC, 25));

        instructions = new JLabel("Please enter the name and amount sold of the product you sold: ");
        instructions.setFont(new Font("Arial", Font.PLAIN, 20));

        textPanel.add(title, format);
        textPanel.add(secondWarning, format);
        textPanel.add(warning, format);
        textPanel.add(status, format);
        textPanel.add(instructions, format);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets up fields and submit button to make sale
    protected void setUpField() {
        name = new JTextField("Name of product");
        textField(name);

        amountSold = new JTextField("Amount sold of product");
        textField(amountSold);

        submit = new JButton("Make Sale");
        submit.setFont(new Font("Arial", Font.PLAIN, 30));
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Format and add components to the main AddProductUI panel
    protected void formatPanel() {
        setLayout(new GridLayout(0, 1, 0, 1));
        add(textPanel);
        add(name, format);
        add(amountSold, format);
        add(submit, format);
    }

    // REQUIRES: market != null
    // MODIFIES: this, market
    // EFFECTS: Takes in input from fields to make sale of product in marked
    // if fields fit requirements and updates status text accordingly
    public void makeSale(Market market) {
        String name = this.name.getText();
        int amountSold = -1;
        status.setForeground(Color.RED);
        try {
            amountSold = Integer.parseInt(this.amountSold.getText());
            if (market.findProduct(name) == null) {
                status.setText("The product \"" + name
                        + "\" is not in the inventory, please add the product first or choose another product.");
            } else if (amountSold <= 0) {
                status.setText("Amount sold must be greater than zero, please try again.");
            } else if (amountSold > market.findProduct(name).getAmountOwned()) {
                status.setText(
                        "Amount sold cannot be more than the amount owned of \"" + name + "\", please try again.");
            } else {
                market.makeSale(name, amountSold);
                status.setForeground(Color.GREEN);
                resetFields();
                status.setText("Sold " + amountSold + " of product \"" + name + "\"!");
            }
        } catch (NumberFormatException e) {
            status.setText("Amount sold must be a number, please try again.");
        }
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

        amountSold.setText("Amount sold of product");
        textField(amountSold);
        amountSold.setFocusable(false);
        amountSold.setFocusable(true);
    }

}
