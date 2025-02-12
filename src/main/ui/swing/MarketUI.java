package ui.swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

import model.Market;
import model.Event;

// Represents the JavaSwing UI application for Market
public class MarketUI extends JFrame implements ActionListener, WindowListener {

    private JPanel main;
    private JPanel background;
    private JLabel reminderText;
    private JTabbedPane tabbedPane;
    private FileManagerUI fileManagerUI;
    private AddProductUI addProductUI;
    private RemoveProductUI removeProductUI;
    private MakeSaleUI makeSaleUI;
    private DisplayInfoUI inventoryUI;
    private DisplayInfoUI saleLogUI;
    private DisplayInfoUI overviewUI;
    private Market market;

    private static final ImageIcon ICON = new ImageIcon("data/icon.png");
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    public static void main(String[] args) {
        new MarketUI();
    }

    // EFFECTS: Creates a MarketUI JavaSwing UI application
    public MarketUI() {
        init();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the ui by creating a market and setting up JTabbedPane on a
    // JPanel as the content panel
    private void init() {
        market = new Market("210");
        tabbedPane = new JTabbedPane();
        main = new JPanel(new BorderLayout());
        setUp();
        tabResetListener();
        setReminderText();
        setTitle("Market Manager App");
        setIconImage(ICON.getImage());
        setSize(WIDTH, HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setContentPane(main);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(this);
    }

    // EFFECTS: Prints EventLog of market and exits the program if the window is
    // getting closed
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event next : market.getEventLog()) {
            System.out.println(next.toString());
        }
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Do nothing
    }

    // MODIFIES: this
    // EFFECTS: Resets response messages to nothing when switching tabs
    private void tabResetListener() {
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                addProductUI.resetMessage();
                removeProductUI.resetMessage();
                makeSaleUI.resetMessage();
                fileManagerUI.resetResponse();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Adds reminder text at the top and the tabbedPane under
    private void setReminderText() {
        background = new JPanel();
        background.setBackground(Color.BLUE);
        reminderText = new JLabel("Remember to save before quiting application! Any unsaved changes will be lost.");
        reminderText.setFont(new Font("Arial", Font.ITALIC, 20));
        reminderText.setForeground(Color.WHITE);
        background.add(reminderText);

        main.add(background, BorderLayout.PAGE_START);
        main.add(tabbedPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the ui by setting up Panels on JTabbedPane
    private void setUp() {
        setUpFileManager();
        setUpAddProduct();
        setUpRemoveProduct();
        setUpMakeSale();
        setUpInventory();
        setUpSaleLog();
        setUpOverview();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the File Manager panel and adds to JTabbedPane
    private void setUpFileManager() {
        fileManagerUI = new FileManagerUI();
        fileManagerUI.getLoadButton().addActionListener(this);
        fileManagerUI.getSaveButton().addActionListener(this);
        tabbedPane.addTab("Load/Save", fileManagerUI);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the Add Product panel and adds to JTabbedPane
    private void setUpAddProduct() {
        addProductUI = new AddProductUI();
        addProductUI.getButton().addActionListener(this);
        tabbedPane.addTab("Add Product", addProductUI);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the Remove Product panel and adds to JTabbedPane
    private void setUpRemoveProduct() {
        removeProductUI = new RemoveProductUI();
        removeProductUI.getButton().addActionListener(this);
        tabbedPane.addTab("Remove Product", removeProductUI);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the Make Sale panel and adds to JTabbedPane
    private void setUpMakeSale() {
        makeSaleUI = new MakeSaleUI();
        makeSaleUI.getButton().addActionListener(this);
        tabbedPane.addTab("Make Sale", makeSaleUI);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the Inventory panel and adds to JTabbedPane
    private void setUpInventory() {
        inventoryUI = new InventoryUI(market);
        tabbedPane.addTab("Inventory List", inventoryUI);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the Sale Log panel and adds to JTabbedPane
    private void setUpSaleLog() {
        saleLogUI = new SaleLogUI(market);
        tabbedPane.addTab("Sale Log", saleLogUI);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the Overview panel and adds to JTabbedPane
    private void setUpOverview() {
        overviewUI = new OverviewUI(market);
        tabbedPane.addTab("Overview", overviewUI);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Manages what actions are made when specific buttons are pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileManagerUI.getLoadButton()) {
            if (fileManagerUI.loadFile() != null) {
                market = fileManagerUI.loadFile();
                updateText();
            }
        } else if (e.getSource() == fileManagerUI.getSaveButton()) {
            fileManagerUI.saveFile(market);
        } else if (e.getSource() == addProductUI.getButton()) {
            addProductUI.addProduct(market);
            updateText();
        } else if (e.getSource() == makeSaleUI.getButton()) {
            makeSaleUI.makeSale(market);
            updateText();
        } else if (e.getSource() == removeProductUI.getButton()) {
            removeProductUI.removeProduct(market);
            updateText();
        }
    }

    // MODIFIES: this
    // EFFECTS: Updates text on inventory, sale log, and overview panels
    private void updateText() {
        inventoryUI.updateText(market);
        saleLogUI.updateText(market);
        overviewUI.updateText(market);
    }
}
