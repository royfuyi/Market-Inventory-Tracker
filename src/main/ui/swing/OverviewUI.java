package ui.swing;

import java.awt.*;
import javax.swing.*;

import model.Market;

// Represents A Overview UI JPanel class that displays the overview information
public class OverviewUI extends DisplayInfoUI {

    private JPanel overview;
    private JLabel overviewTitle;
    private JLabel overviewText;
    private JLabel borderImage;
    private ImageIcon border;

    // EFFECTS: Constructs a Overview UI JPanel that displays the overview of market
    public OverviewUI(Market market) {
        super(market);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Formats the displayed text in panel
    protected void formatText() {
        overviewTitle = new JLabel("Total Costs, Sales, and Profit");
        overviewTitle.setFont(new Font("Arial", Font.BOLD, 30));
        overviewTitle.setHorizontalAlignment(SwingConstants.CENTER);
        overviewTitle.setVerticalAlignment(SwingConstants.TOP);

        overview = new JPanel(new GridBagLayout());
        overviewText = new JLabel();
        overviewText.setFont(new Font("Arial", Font.PLAIN, 30));
        overviewText.setHorizontalAlignment(SwingConstants.CENTER);
        border = new ImageIcon("data/line.png");
        borderImage = new JLabel(border);

        overview.add(overviewText, format);
        overview.add(borderImage, format);

        add(overviewTitle, BorderLayout.PAGE_START);
        add(overview, BorderLayout.CENTER);
    }

    @Override
    // REQUIRES: market != null
    // MODIFIES: this
    // EFFECTS: Updates the text according to information from market
    public void updateText(Market market) {
        overviewText.setText(market.costSalesProfit());
    }
}
