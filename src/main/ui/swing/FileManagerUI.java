package ui.swing;

import javax.swing.*;

import model.Market;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.awt.*;

// Represents a JPanel UI for loading and saving files
public class FileManagerUI extends JPanel {

    private JPanel loadPanel;
    private JPanel savePanel;
    private JPanel title;
    private JButton loadButton;
    private JButton saveButton;
    private JLabel loadWarningText;
    private JLabel saveWarningText;
    private JLabel titleText;
    private JLabel responseText;
    private JLabel responseImage;
    private ImageIcon successIcon;
    private ImageIcon failedIcon;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static GridBagConstraints formatLoad;
    private static final String JsonFile = "./data/market.json";

    // EFFECTS: Constructs a UI that contains buttons to load/save file, jsonreader,
    // and jsonwriter to read and write to file
    public FileManagerUI() {
        jsonWriter = new JsonWriter(JsonFile);
        jsonReader = new JsonReader(JsonFile);
        setLayout(new GridLayout(0, 1, 0, 1));
        formatLoad = new GridBagConstraints();
        formatLoad.gridx = 0;
        formatLoad.gridy = GridBagConstraints.RELATIVE;
        formatLoad.anchor = GridBagConstraints.CENTER;
        formatLoad.fill = GridBagConstraints.NONE;
        setActionTextPanel();
        setLoadPanel();
        setSavePanel();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the load sub-panel and adds to the main FileManagerUI panel
    private void setLoadPanel() {
        loadPanel = new JPanel(new GridBagLayout());

        loadButton = new JButton("Load File");
        loadButton.setFont(new Font("Arial", Font.PLAIN, 30));
        loadButton.setPreferredSize(new Dimension(1000, 200));
        loadWarningText = new JLabel("NOTE: Loading previous file will override current file information.");
        loadWarningText.setFont(new Font("Arial", Font.ITALIC, 15));
        loadWarningText.setForeground(Color.RED);
        loadPanel.add(loadWarningText, formatLoad);
        loadPanel.add(loadButton, formatLoad);

        add(loadPanel);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the save sub-panel and adds to the main FileManagerUI panel
    private void setSavePanel() {
        savePanel = new JPanel(new GridBagLayout());

        saveButton = new JButton("Save Current File");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 30));
        saveButton.setPreferredSize(new Dimension(1000, 200));
        saveWarningText = new JLabel("NOTE: Saving current file will override previously saved file.");
        saveWarningText.setFont(new Font("Arial", Font.ITALIC, 15));
        saveWarningText.setForeground(Color.RED);
        savePanel.add(saveWarningText, formatLoad);
        savePanel.add(saveButton, formatLoad);

        add(savePanel);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the text sub-panel that contains the title and response text
    // and adds to the main FileManagerUI panel
    private void setActionTextPanel() {
        title = new JPanel(new GridBagLayout());
        titleText = new JLabel("Load/Save Market Information");
        titleText.setFont(new Font("Arial", Font.BOLD, 40));
        responseText = new JLabel(" ");
        responseText.setFont(new Font("Arial", Font.ITALIC, 25));
        successIcon = new ImageIcon("data/greencheck.png");
        failedIcon = new ImageIcon("data/redx.jpg");

        responseImage = new JLabel();
        responseImage.setPreferredSize(new Dimension(100, 100));

        title.add(titleText, formatLoad);
        title.add(responseText, formatLoad);
        title.add(responseImage, formatLoad);

        add(title);
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    // MODIFIES: this
    // EFFECTS: Loads json file from Jsonfile, returns it as a Market, and updates
    // responseText and responseIcon accordingly
    public Market loadFile() {
        Market market = null;
        try {
            market = jsonReader.read();
            responseText.setForeground(Color.GREEN);
            responseText.setText("File Loaded!");
            responseImage.setIcon(successIcon);

        } catch (IOException e) {
            responseText.setForeground(Color.RED);
            responseText.setText("Unable to read from file: " + JsonFile);
            responseImage.setIcon(failedIcon);
        }
        return market;
    }

    // REQUIRES: market != null
    // MODIFIES: this
    // EFFECTS: Save market to the json file at Jsonfile and updates responseText
    // and responseIcon accordingly
    public void saveFile(Market market) {
        try {
            jsonWriter.open();
            jsonWriter.write(market);
            jsonWriter.close();
            responseText.setForeground(Color.GREEN);
            responseText.setText("Market saved to " + JsonFile + "!");
            responseImage.setIcon(successIcon);
        } catch (FileNotFoundException e) {
            responseText.setForeground(Color.RED);
            responseText.setText("File " + JsonFile + " not found");
            responseImage.setIcon(failedIcon);
        }
    }

    // MODIFIES: this
    // EFFECTS: Resets the response text and image to default
    public void resetResponse() {
        responseText.setText(" ");
        responseImage.setIcon(null);
    }

}
