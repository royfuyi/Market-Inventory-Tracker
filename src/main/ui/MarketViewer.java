package ui;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

import model.Market;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.InvalidAmountException;

// Represents the ui console application for Market
public class MarketViewer {

    private Market market;
    private Scanner scanner;
    private boolean programRunning;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JsonFile = "./data/market.json";

    // EFFECTS: Creates a MarketViewer console ui application
    public MarketViewer() {
        setUp();

        while (programRunning) {
            mainMenu();
        }
    }

    // EFFECTS: Prints a line to seperate text sections
    private void printLine() {
        System.out.println(
                "--------------------------------------------------------------------------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: Sets up the ui by creating a market with name provided by user and
    // making the program to be running
    private void setUp() {
        intro();
        String fileInput = scanner.nextLine();

        while (!fileInput.equals("1") && !fileInput.equals("2")) {
            System.out.println("Invalid option inputted :( please try again.");
            fileInput = scanner.nextLine();
        }

        printLine();
        if (fileInput.equals("1")) {
            fileLoad();
            if (market.getName().equals("THEDEFAULTMKT")) {
                System.out.println("No previously saved market, please create a new one");
                createNewMarket();
            }
        } else if (fileInput.equals("2")) {
            createNewMarket();
        }
        printLine();
    }

    // MODIFIES: this
    // EFFECTS: Sets up scanner, jsonreader, and hsonwriter and prints intro options
    private void intro() {
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JsonFile);
        jsonReader = new JsonReader(JsonFile);
        programRunning = true;
        System.out.println("Welcome! Would you like to: (type in the corresponding number)");
        System.out.println("(1) Load previously saved market information");
        System.out.println("(2) Create a new market");
        printLine();
    }

    // MODIFIES: this
    // EFFECTS: Creates a new default market
    private void createNewMarket() {
        System.out.println("What would you like the name your market?");
        printLine();

        String input = scanner.nextLine();
        market = new Market(input);
        System.out.println(input + " market Created!");
    }

    // MODIFIES: this
    // EFFECTS: Loads previously saved market from file
    private void fileLoad() {
        try {
            market = jsonReader.read();
            if (!market.getName().equals("THEDEFAULTMKT")) {
                System.out.println("Loaded the " + market.getName() + " market from " + JsonFile + "!");
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JsonFile);
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays and processes user inputs for the main menu
    private void mainMenu() {
        displayMainMenu();
        String choice = scanner.nextLine();
        processMenu(choice);
    }

    // EFFECTS: Displays the list of things you can do in the main menu
    private void displayMainMenu() {
        System.out.println("Please select an option by entering the coresponding letter:");
        System.out.println("a: Add a new product");
        System.out.println("r: Remove a product");
        System.out.println("e: Edit a product");
        System.out.println("m: Make a sale");
        System.out.println("v: View market information");
        System.out.println("l: Load previously saved market information");
        System.out.println("s: Save market information");
        System.out.println("q: Quit out of application");
        printLine();
    }

    // MODIFIES: this
    // EFFECTS: Processes the user input from main menu
    private void processMenu(String input) {
        printLine();
        if (input.equals("a")) {
            addProduct();
        } else if (input.equals("r")) {
            removeProduct();
        } else if (input.equals("e")) {
            editMenu();
        } else if (input.equals("m")) {
            makeSale();
        } else if (input.equals("v")) {
            viewMenu();
        } else if (input.equals("l")) {
            loadFileMainMenu();
        } else if (input.equals("s")) {
            fileSave();
        } else if (input.equals("q")) {
            quitApplication();
        } else {
            System.out.println("Invalid option inputted :( please try again.");
        }
        printLine();
    }

    // EFFECTS: Displays the list of things you can do in the edit menu
    private void displayEditMenu() {
        System.out.println("Please select an option by entering the coresponding letter:");
        System.out.println("c: Change a product price");
        System.out.println("i: Increase a product amount");
        System.out.println("r: Return to main menu");
        printLine();
    }

    // MODIFIES: this
    /// EFFECTS: Displays and processes user inputs for the edit menu
    private void editMenu() {
        displayEditMenu();
        String choice = scanner.nextLine();
        processEditMenu(choice);
    }

    // MODIFIES: this
    // EFFECTS: Processes the user input from edit menu
    private void processEditMenu(String input) {
        while ((!input.equals("c") && !input.equals("i")) && !input.equals("r")) {
            System.out.println("Invalid option inputted :( please try again.");
            input = scanner.nextLine();
        }

        printLine();

        if (input.equals("c")) {
            changePrice();
        } else if (input.equals("i")) {
            increaseAmount();
        } else if (input.equals("r")) {
            System.out.println("Returned to main menu ->");
        }
    }

    // EFFECTS: Displays the list of things you can do in the view menu
    private void displayViewInfoMenu() {
        System.out.println("Please select an option by entering the coresponding letter:");
        System.out.println("a: View current inventory of products");
        System.out.println("b: View sale logs");
        System.out.println("c: View total cost, total sales, and total profit");
        System.out.println("r: Return to main menu");
        printLine();
    }

    // MODIFIES: this
    /// EFFECTS: Displays and processes user inputs for the view menu
    private void viewMenu() {
        displayViewInfoMenu();
        String choice = scanner.nextLine();
        processViewMenu(choice);
    }

    // MODIFIES: this
    // EFFECTS: Processes the user input from view menu
    private void processViewMenu(String input) {
        while ((!input.equals("a") && !input.equals("b")) && (!input.equals("c") && !input.equals("r"))) {
            System.out.println("Invalid option inputted :( please try again.");
            input = scanner.nextLine();
        }

        printLine();

        if (input.equals("a")) {
            viewInventory();
        } else if (input.equals("b")) {
            viewPreviousSales();
        } else if (input.equals("c")) {
            viewCostSalesProfit();
        } else if (input.equals("r")) {
            System.out.println("Returned to main menu ->");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads previously saved market from file, warning the user that it
    // will override current unsaved market
    private void loadFileMainMenu() {
        System.out.println("This will overwrite the current unsaved market, do you want to continue? (y/n)");
        String input = scanner.nextLine();
        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid option inputted :( please try again.");
            input = scanner.nextLine();
        }
        printLine();
        if (input.equals("y")) {
            fileLoad();
            if (market.getName().equals("THEDEFAULTMKT")) {
                System.out.println("Market loaded was the default, please create a new market:");
                createNewMarket();
            }
        } else if (input.equals("n")) {
            System.out.println("File loading cancelled ->");
        }
    }

    // MODIFIES: this
    // EFFECTS: Saves market to file, warning the user that it will override
    // previously saved market
    private void fileSave() {
        System.out.println("This will overwrite the current saved market in file, do you want to continue? (y/n)");
        String input = scanner.nextLine();
        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid option inputted :( please try again.");
            input = scanner.nextLine();
        }
        printLine();
        if (input.equals("y")) {
            try {
                jsonWriter.open();
            } catch (FileNotFoundException e) {
                System.out.println("File " + JsonFile + " not found");
            }
            jsonWriter.write(market);
            jsonWriter.close();
            System.out.println("Market saved to " + JsonFile + "!");
        } else if (input.equals("n")) {
            System.out.println("File saving cancelled ->");
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a new product to the inventory of market
    private void addProduct() {
        System.out.println("What is the product name?");
        String name = scanner.nextLine();
        name = correctNameInput(name);

        System.out.println("What is the amount owned of that product?");
        int amountOwned = correctAmountOwnedInput();

        System.out.println("What was the cost to produce that product?");
        double cost = correctCostInput();

        System.out.println("What is the selling price of that product?");
        double price = correctPriceInput();

        market.addProduct(name, amountOwned, cost, price);
        System.out.println("Product \"" + name + "\" added!");
    }

    // EFFECTS: Returns the input for name that adheres to requires rules
    private String correctNameInput(String name) {
        while (market.findProduct(name) != null) {
            System.out.println("A product with that name is already in the list, please add a different product.");
            name = scanner.nextLine();
        }
        return name;
    }

    // EFFECTS: Continously tries to take user input for amountOwned until it does
    // not throw an error, then return amountOwned
    private int correctAmountOwnedInput() {
        int amountOwned = -1;
        while (amountOwned == -1) {
            try {
                amountOwned = checkAmountOwnedInput();
            } catch (NumberFormatException e) {
                System.out.println("Input must be an integer, please try again.");
            } catch (InvalidAmountException e) {
                System.out.println("Amount owned must be 0 or greater, please try again.");
            }
        }
        return amountOwned;
    }

    // EFFECTS: Takes the next user input as the amountOwned, if amountOwned is
    // smaller than 0, throws a InvalidAmountException, if not, returns amountOwned
    private int checkAmountOwnedInput() throws InvalidAmountException {
        int amountOwned = Integer.parseInt(scanner.nextLine());
        if (amountOwned < 0) {
            throw new InvalidAmountException();
        }
        return amountOwned;
    }

    // EFFECTS: Continously tries to take user input for cost until it does not
    // throw an error, then return cost
    private double correctCostInput() {
        double cost = 0;
        while (cost == 0) {
            try {
                cost = checkCostInput();
            } catch (NumberFormatException e) {
                System.out.println("Input must be an number, please try again.");
            } catch (InvalidAmountException e) {
                System.out.println("Cost must be greater than 0, please try again.");
            }
        }
        return cost;
    }

    // EFFECTS: Takes the next user input as the cost, if cost is smaller or equal
    // to 0, throws a InvalidAmountException, if not, returns cost
    private double checkCostInput() throws InvalidAmountException {
        double cost = Double.parseDouble(scanner.nextLine());
        if (cost <= 0) {
            throw new InvalidAmountException();
        }
        return cost;
    }

    // EFFECTS: Continously tries to take user input for cost until it does not
    // throw an error, then return cost
    private double correctPriceInput() {
        double price = 0;
        while (price == 0) {
            try {
                price = checkPriceInput();
            } catch (NumberFormatException e) {
                System.out.println("Input must be an number, please try again.");
            } catch (InvalidAmountException e) {
                System.out.println("Price must be greater than 0, please try again.");
            }
        }
        return price;
    }

    // EFFECTS: Takes the next user input as the cost, if cost is smaller or equal
    // to 0, throws a InvalidAmountException, if not, returns cost
    private double checkPriceInput() throws InvalidAmountException {
        double price = Double.parseDouble(scanner.nextLine());
        if (price <= 0) {
            throw new InvalidAmountException();
        }
        return price;
    }

    // MODIFIES: this
    // EFFECTS: Removes a product from the inventory of market if it exists
    private void removeProduct() {
        if (market.getInventoryList().isEmpty()) {
            System.out.println("No products in inventory to remove.");
        } else {
            System.out.println("What is the name of the product you want to remove?");
            String name = scanner.nextLine();
            if (market.findProduct(name) != null) {
                market.removeProduct(name);
                System.out.println("Product \"" + name + "\" removed!");
            } else {
                System.out.println("Theres no product with that name in the inventory.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Makes a sale of specifies product from market if it exists
    private void makeSale() {
        if (market.getInventoryList().isEmpty()) {
            System.out.println("No products in inventory to sell.");
        } else {
            System.out.println("Which product did you sell?");
            String name = scanner.nextLine();
            if (market.findProduct(name) != null) {
                int amountOwned = market.findProduct(name).getAmountOwned();
                if (amountOwned == 0) {
                    System.out.println("There is no amount owned of product \"" + name + "\" to sell.");
                } else {
                    System.out.println("How many did you sell?");
                    int amountSold = correctAmountSoldInput(amountOwned);
                    market.makeSale(name, amountSold);
                    System.out.println("Sold " + amountSold + " of \"" + name + "\" !");
                }
            } else {
                System.out.println("Theres no product with that name in the inventory.");
            }
        }
    }

    // EFFECTS: Continously tries to take user input for amountSold until it does
    // not
    // throw an error, then return amountSold
    private int correctAmountSoldInput(int amountOwned) {
        int amountSold = 0;
        while (amountSold == 0) {
            try {
                amountSold = checkAmountSoldInput(amountOwned);
            } catch (NumberFormatException e) {
                System.out.println("Input must be an integer, please try again.");
            } catch (InvalidAmountException e) {
                System.out
                        .println("Amount sold must be greater than 0 and less than amount owned, please try again.");
            }
        }
        return amountSold;
    }

    // EFFECTS: Takes the next user input as the amountSold, if cost is smaller or
    // equal to 0, throws a InvalidAmountException, if not, returns amountSold
    private int checkAmountSoldInput(int amountOwned) throws InvalidAmountException {
        int amountSold = Integer.parseInt(scanner.nextLine());
        if ((amountSold <= 0) || (amountSold > amountOwned)) {
            throw new InvalidAmountException();
        }
        return amountSold;
    }

    // EFFECTS: Displays the list of products in the inventory
    private void viewInventory() {
        if (market.getInventoryList().isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            System.out.println(market.toString());
        }
    }

    // EFFECTS: Displays the sale log of the market
    private void viewPreviousSales() {
        if (market.getSaleLogs().isEmpty()) {
            System.out.println("No sales made yet.");
        } else {
            System.out.println(market.getSaleLogsAsString());
        }
    }

    // EFFECTS: Displays the total cost of products verses total sales and total
    // profit
    private void viewCostSalesProfit() {
        System.out.println(market.costSalesProfit());
    }

    // MODIFIES: this
    // EFFECTS: Changes selling price of specified product by price if it exists
    private void changePrice() {
        if (market.getInventoryList().isEmpty()) {
            System.out.println("No products in inventory to change price of.");
        } else {
            System.out.println("Which product's selling price do you want to change?");
            String name = scanner.nextLine();
            if (market.findProduct(name) != null) {
                System.out.println("What price do you want to change it to?");
                double price = correctPriceInput();
                market.findProduct(name).setPrice(price);
                System.out.println("Price of \"" + name + "\" changed to $" + price);
            } else {
                System.out.println("Theres no product with that name in the inventory.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Increases amount owned of specified product by amount if it exists
    private void increaseAmount() {
        if (market.getInventoryList().isEmpty()) {
            System.out.println("No products in inventory to increases amount owned of.");
        } else {
            System.out.println("Which product's amount do you want to increase?");
            String name = scanner.nextLine();
            if (market.findProduct(name) != null) {
                System.out.println("What amount do you want to add to amount owned of " + name + "?");
                int amount = correctAmountOwnedInput();
                market.increaseAmountOwnedOfProduct(name, amount);
                System.out.println(amount + " of \"" + name + "\" added");
            } else {
                System.out.println("Theres no product with that name in the inventory.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Prints a closing message and marks the program as not running and
    // warns user to save
    private void quitApplication() {
        System.out.println(
                "Any unsaved changed will be lost when quitting the application, do you want to continue? (y/n)");
        String input = scanner.nextLine();
        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid option inputted :( please try again.");
            input = scanner.nextLine();
        }
        printLine();
        if (input.equals("y")) {
            System.out.println("Hope you had fun running the " + market.getName() + " market!");
            programRunning = false;
        } else if (input.equals("n")) {
            System.out.println("Returned to main menu ->");
        }
    }
}
