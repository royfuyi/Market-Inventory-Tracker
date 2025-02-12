package ui.swing;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

import model.Market;

// Represents A SaleLog UI JPanel class that displays the sale log of market
public class SaleLogUI extends DisplayInfoUI {

    private JPanel saleLog;
    private JLabel saleLogTitle;
    private JPanel saleLogText;

    // EFFECTS: Constructs a SaleLog UI JPanel that displays the sale log of market
    public SaleLogUI(Market market) {
        super(market);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Formats the displayed text in panel
    protected void formatText() {
        saleLog = new JPanel(new GridBagLayout());
        saleLogTitle = new JLabel("Sale Logs");
        saleLogTitle.setFont(new Font("Arial", Font.BOLD, 30));
        saleLogText = new JPanel(new GridBagLayout());
        saleLog.add(saleLogText, format);

        saleLogTitle.setHorizontalAlignment(SwingConstants.CENTER);
        saleLogTitle.setVerticalAlignment(SwingConstants.TOP);
        add(saleLogTitle, BorderLayout.PAGE_START);
        add(saleLog);
    }

    @Override
    // REQUIRES: market != null
    // MODIFIES: this
    // EFFECTS: Updates the text according to information from market
    public void updateText(Market market) {
        ArrayList<String> saleLog = market.getSaleLogs();
        saleLogText.removeAll();
        if (saleLog.isEmpty()) {
            JLabel temp = new JLabel("No Sales Made");
            temp.setFont(new Font("Arial", Font.ITALIC, 20));
            temp.setForeground(Color.GRAY);
            saleLogText.add(temp, format);
        } else {
            for (String s : saleLog) {
                JLabel temp = new JLabel(s);
                temp.setFont(new Font("Arial", Font.PLAIN, 20));
                temp.setForeground(Color.BLACK);
                saleLogText.add(temp, format);
            }
        }
    }
}
