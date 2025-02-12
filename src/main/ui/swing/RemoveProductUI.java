package ui.swing;

import java.awt.*;
import javax.swing.*;

import model.Market;

// Represents a JPanel UI for removing products
public class RemoveProductUI extends TextFieldUI {

    private JPanel textPanel;
    private JPanel submitPanel;
    private JLabel title;
    private JLabel warning;
    private JLabel secondWarning;
    private JLabel status;
    private JLabel instructions;
    private JTextField name;

    // EFFECTS: Constructs a UI that contains buttons and fields to remove
    // an existing product
    public RemoveProductUI() {
        super();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets up the subpanel containing the title, warning texts,
    // and status updates text
    protected void setUpText() {
        textPanel = new JPanel(new GridBagLayout());
        title = new JLabel("Remove a Product!");
        title.setFont(new Font("Arial", Font.BOLD, 40));

        String warningText = "NOTE: Name of product must be already in the list";
        warning = new JLabel(warningText);
        warning.setFont(new Font("Arial", Font.ITALIC, 15));
        warning.setForeground(Color.GRAY);

        secondWarning = new JLabel("**ALL FIELDS MUST BE FILLED OUT**");
        secondWarning.setFont(new Font("Arial", Font.ITALIC, 20));
        secondWarning.setForeground(Color.RED);

        status = new JLabel(" ");
        status.setFont(new Font("Arial", Font.ITALIC, 25));

        instructions = new JLabel("Please enter the name of the product you wish to remove: ");
        instructions.setFont(new Font("Arial", Font.PLAIN, 20));

        textPanel.add(title, format);
        textPanel.add(secondWarning, format);
        textPanel.add(warning, format);
        textPanel.add(status, format);
        textPanel.add(instructions, format);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets up name field and submit button
    protected void setUpField() {
        submitPanel = new JPanel();
        name = new JTextField("Name of product");
        textField(name);
        name.setPreferredSize(new Dimension(600, 50));

        submit = new JButton("Remove this product");
        submit.setFont(new Font("Arial", Font.PLAIN, 30));

        submitPanel.add(name);
        submitPanel.add(submit);

    }

    @Override
    // MODIFIES: this
    // EFFECTS: Format and add components to the main AddProductUI panel
    protected void formatPanel() {
        setLayout(new GridLayout(0, 1, 0, 1));
        add(textPanel);
        add(submitPanel, format);
    }

    // REQUIRES: market != null
    // MODIFIES: this, market
    // EFFECTS: Takes in input from fields to remove a product from market if the
    // name field
    // contains the name of an existing product from inventory and updates status
    // text accordingly
    public void removeProduct(Market market) {
        String name = this.name.getText();
        if (market.findProduct(name) == null) {
            status.setForeground(Color.RED);
            status.setText("A product of name \"" + name + "\" is not in the inventory, please try again.");
        } else {
            market.removeProduct(name);
            status.setForeground(Color.GREEN);
            resetFields();
            status.setText("Product removed!");
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
    }

}
