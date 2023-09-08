package ui;

import model.Catalogue;
import model.Meal;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Application for browsing and creating catalogues of meals.
public class ConsoleInterface {
    private Catalogue currentBook;
    private Scanner input;

    private static final String JSON_STORE = "./data/catalogue.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs the application
    public ConsoleInterface() {
        runApplication();
    }

    // Code citation : https://github.students.cs.ubc.ca/CPSC210/TellerApp
    //  MODIFIES: this
    //  EFFECTS: processes user inputs
    private void runApplication() {
        System.out.println("Welcome to the Meal Browser!");
        boolean keepGoing = true;
        String command = null;

        init();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // Code citation : https://github.students.cs.ubc.ca/CPSC210/TellerApp
    //  MODIFIES: this
    //  EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            doEditName();
        } else if (command.equals("i")) {
            viewCatalogueInfo();
        } else if (command.equals("v")) {
            viewMealInfo();
        } else if (command.equals("e")) {
            doEditMeals();
        } else if (command.equals("a")) {
            doAddMeal();
        } else if (command.equals("r")) {
            doRemoveMeal();
        } else if (command.equals("f")) {
            doEatMeal();
        } else if (command.equals("s")) {
            saveCatalogue();
        } else if (command.equals("l")) {
            loadCatalogue();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //  MODIFIES: this
    //  EFFECTS: Eats a meal and increments the amount eaten by 1.
    private void doEatMeal() {
        System.out.println("\nPlease enter the name of the meal: \n");
        String name = input.next();
        if (currentBook.getSpecificMeal(name) != null) {
            System.out.println("\nTime to feast on this " + name + "!!!");
            System.out.println("Nom Nom Crunch Crunch...");
            System.out.println("That was delicious!\n");
            currentBook.getSpecificMeal(name).increaseAmountEaten();
        } else {
            System.out.println("\nERROR: Cannot find meal, you can't eat a non existing food...\n");
        }
    }

    //  EFFECTS: Displays information about the catalogue such as name,
    //  number of foods and the foods in the catalogue.
    private void viewCatalogueInfo() {
        System.out.println("Displaying information on Catalogue: ");
        System.out.println(currentBook + "\n");
    }

    //  EFFECTS: Displays information about the meal such as name,
    //  number of times eaten and the ingredients.
    private void viewMealInfo() {
        System.out.println("\nPlease enter the name of the meal: \n");
        String name = input.next();
        if (currentBook.getSpecificMeal(name) != null) {
            System.out.println(currentBook.getSpecificMeal(name) + "\n");
        } else {
            System.out.println("\nERROR: Cannot find meal, please make sure it is in the catalogue... \n");
        }
    }

    // Code citation : https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processMealCommand(String command, Meal m) {
        if (command.equals("c")) {
            doEditMealName(m);
        } else if (command.equals("a")) {
            doAddIngredient(m);
        } else if (command.equals("r")) {
            doRemoveIngredient(m);
        } else {
            System.out.println("\nSelection not valid... \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: Edits the name of the catalogue.
    private void doEditName() {
        System.out.print("\nEnter new name for the catalogue: \n");
        String name = input.next();
        currentBook.setName(name);

        System.out.println("\nYour new catalogue name is: " + name + "\n");
    }

    // REQUIRES: m is an element within the catalogue.
    // MODIFIES: this
    // EFFECTS: Edits the name of a meal.
    private void doEditMealName(Meal m) {
        System.out.print("\nEnter new name for the Meal: \n");
        String name = input.next();
        currentBook.changeMealName(m.getName(), name);
        m.setName(name);
    }

    // REQUIRES: m is an element within the catalogue.
    // MODIFIES: this
    // EFFECTS: Adds a new ingredient to a meal. If an ingredient of the same name already exists
    //          it does nothing.
    private void doAddIngredient(Meal m) {
        System.out.print("\nEnter new ingredient for the Meal: \n");
        String name = input.next();

        if (m.addIngredients(name)) {
            System.out.println("\nSuccessfully added " + name + " as an ingredient.\n");
        } else {
            System.out.println("\nError: Ingredient is already in the meal. Please enter a new ingredient\n");
        }
    }

    // REQUIRES: m is an element within the catalogue.
    // MODIFIES: this
    // EFFECTS: Removes a new ingredient to a meal. if an ingredient of the same name does not exist
    //          it does nothing.
    private void doRemoveIngredient(Meal m) {
        System.out.print("\nEnter a ingredient to remove from the Meal: \n");
        String name = input.next();

        if (m.removeIngredients(name)) {
            System.out.println("\nSuccessfully removed " + name + " as an ingredient.\n");
        } else {
            System.out.println("\nError: Ingredient is not in the meal. Please enter an existing ingredient.\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: Allows the user to edit a meal by changing its name, adding/removing ingredients
    private void doEditMeals() {
        boolean keepGoing = true;
        String command = null;

        System.out.println("\nPlease enter the name of the meal: \n");
        String name = input.next();
        if (currentBook.getSpecificMeal(name) != null) {
            Meal tmp = currentBook.getSpecificMeal(name);
            while (keepGoing) {
                System.out.println("\nWhat would you like to do with the meal? \n");
                displayMealMenu();
                command = input.next();
                command = command.toLowerCase();

                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processMealCommand(command, tmp);
                }
            }
        } else {
            System.out.println("\nERROR: Cannot find meal, please make sure it is in the catalogue...\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a new meal into the catalogue, if a meal of the same name already exists
    //          it does nothing.
    private void doAddMeal() {
        System.out.println("\nPlease enter the name of the meal: \n");
        String mealName = input.next();

        Meal newMeal = new Meal(mealName);
        boolean keepGoing = true;

        if (currentBook.addMeal(newMeal)) {
            String command = getResponse();

            if (command.equals("y")) {
                while (keepGoing) {
                    System.out.println("\nAdd an ingredient: (Press q to quit adding) \n");
                    String ingredient = input.next();

                    if (ingredient.equals("q") || ingredient.equals("Q")) {
                        keepGoing = false;
                    } else {
                        if (!newMeal.addIngredients(ingredient)) {
                            System.out.println("\nError: Ingredient is already in the meal. "
                                    + "Please enter a new ingredient\n");
                        }
                    }
                }
            }
        } else {
            System.out.println("\nError: meal is already in the catalogue.\n");
        }
    }

    // EFFECTS: Gets a response from a user to ask whether ingredients should be added to the new meal
    private String getResponse() {
        String command = null;
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\nWould you like to add ingredients into the meal? (y/n)\n");
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("y") || command.equals("n")) {
                keepGoing = false;
            }
        }
        return command;
    }

    // MODIFIES: this
    // EFFECTS: Removes a new meal into the catalogue, if a meal of the same name does not exist,
    //          it does nothing.
    private void doRemoveMeal() {
        System.out.println("\nPlease enter the name of the meal: \n");
        String mealName = input.next();
        if (currentBook.removeMeal(mealName)) {
            System.out.println("\nSuccessfully removed " + mealName + " from catalogue.\n");
        } else {
            System.out.println("\nERROR: Cannot find " + mealName + " within Catalogue. "
                    + "Please make sure the meal is in the list.\n");
        }
    }

    // EFFECTS: display menu for editing a meal
    private void displayMealMenu() {
        System.out.println("Please select your option: ");
        System.out.println("\tc -> Change the name");
        System.out.println("\ta -> Add an ingredient");
        System.out.println("\tr -> Remove an ingredient");
        System.out.println("\tq -> Return to the main menu");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Please select your option: ");
        System.out.println("\tc -> Edit your catalogue's name");
        System.out.println("\ti -> View the information about your catalogue");
        System.out.println("\ta -> Add a new meal in your catalogue");
        System.out.println("\tr -> Remove a meal in your catalogue");
        System.out.println("\te -> Edit a meal in your catalogue");
        System.out.println("\tv -> View details about a meal in your catalogue");
        System.out.println("\tf -> Feast on a meal!");
        System.out.println("\ts -> save your catalogue");
        System.out.println("\tl -> load a saved catalogue");
        System.out.println("\tq -> Quit the program");
    }

    //  The code is modified from the given Bank Teller application.
    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        currentBook = new Catalogue("Untitled");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // Code Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: saves catalogue from file
    private void saveCatalogue() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentBook);
            jsonWriter.close();
            System.out.println("Saved " + currentBook.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Code Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads catalogue from file
    private void loadCatalogue() {
        try {
            currentBook = jsonReader.read();
            System.out.println("Loaded " + currentBook.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
