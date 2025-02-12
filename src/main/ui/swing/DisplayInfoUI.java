package ui.swing;

import java.awt.*;
import javax.swing.*;

import model.Market;

// Represents a abstract UI class that Displays Info using Jpanel
public abstract class DisplayInfoUI extends JPanel {

    protected static GridBagConstraints format;

    // EFFECTS: Constructs a Display Info UI JPanel that displays the overview
    public DisplayInfoUI(Market market) {
        setLayout(new BorderLayout());
        format = new GridBagConstraints();
        format.gridx = 0;
        format.gridy = GridBagConstraints.RELATIVE;
        format.anchor = GridBagConstraints.CENTER;
        format.fill = GridBagConstraints.NONE;
        formatText();
        updateText(market);
    }

    // MODIFIES: this
    // EFFECTS: Formats the displayed text in panel
    protected abstract void formatText();

    // MODIFIES: this
    // EFFECTS: Updates the text according to information from market
    public abstract void updateText(Market market);
}
