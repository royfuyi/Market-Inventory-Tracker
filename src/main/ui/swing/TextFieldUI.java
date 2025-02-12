package ui.swing;

import java.awt.*;
import javax.swing.*;

//Represents a abstract UI with TextFields and a submit button
public abstract class TextFieldUI extends JPanel {

    protected static GridBagConstraints format;
    protected JButton submit;

    // EFFECTS: Constructs a UI that contains buttons and fields
    public TextFieldUI() {
        format = new GridBagConstraints();
        format.gridx = 0;
        format.gridy = GridBagConstraints.RELATIVE;
        format.anchor = GridBagConstraints.CENTER;
        format.fill = GridBagConstraints.NONE;
        setUpText();
        setUpField();
        formatPanel();
    }

    // MODIFIES: this, PlaceHolderFocusListener
    // EFFECTS: Formats fields fonts and to have placeholder text
    protected void textField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 20));
        field.setForeground(Color.GRAY);
        field.addFocusListener(new PlaceHolderFocusListener(field));
    }

    public JButton getButton() {
        return submit;
    }

    // MODIFIES: this
    // EFFECTS: Sets up the subpanel with text
    protected abstract void setUpText();

    // MODIFIES: this
    // EFFECTS: Sets up the subpanel with fields
    protected abstract void setUpField();

    // MODIFIES: this
    // EFFECTS: Format and add components to the main panel
    protected abstract void formatPanel();

    // MODIFIES: this
    // EFFECTS: Resets fields to placeholder text and color
    protected abstract void resetFields();

    // MODIFIES: this
    // EFFECTS: Resets the text to default
    public abstract void resetMessage();
}
