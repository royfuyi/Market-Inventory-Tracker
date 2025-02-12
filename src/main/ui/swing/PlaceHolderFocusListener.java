package ui.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JTextField;

// Represents a EventListener that handles JTextField's placeholder text
public class PlaceHolderFocusListener implements FocusListener {

    private JTextField searchText;
    private String placeHolder;

    // EFFECTS: Creates a PlaceHolderFocusListener with a Jtextfield and placeholder
    // string
    public PlaceHolderFocusListener(JTextField searchText) {
        this.searchText = searchText;
        this.placeHolder = searchText.getText();
    }

    // MODIFIES: this
    // EFFECTS: When user clicks on the text field, it clears the text field and
    // sets text color to black
    @Override
    public void focusGained(FocusEvent e) {
        if (searchText.getText().equals(placeHolder)) {
            searchText.setText("");
            searchText.setForeground(Color.BLACK);
        }
    }

    // MODIFIES: this
    // EFFECTS: When user is not clicked in the text field, it sets the text field
    // to placeholder text and color to gray
    @Override
    public void focusLost(FocusEvent e) {
        if (searchText.getText().isEmpty()) {
            searchText.setForeground(Color.GRAY);
            searchText.setText(placeHolder);
        }
    }
}
