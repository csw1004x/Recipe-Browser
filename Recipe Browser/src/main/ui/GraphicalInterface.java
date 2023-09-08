package ui;

import model.Catalogue;
import model.Meal;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.exceptions.NotInCatalogueException;
import model.exceptions.DuplicateException;
import model.exceptions.EmptyNameException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

//Graphical UI for the application.
public class GraphicalInterface extends JFrame implements ActionListener {
    private Catalogue currentBook;
    private static final String JSON_STORE = "./data/catalogue.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JPanel panel;
    private JPanel menuQuestion;
    private JPanel currentPage;
    private JPanel currentButtons;
    private JButton information;
    private JButton changeCatalogueName;
    private JButton editMeal;
    private JButton addMeal;
    private JButton removeMeal;
    private JButton feastMeal;
    private JButton saveCatalogue;
    private JButton loadCatalogue;
    private JButton exitToMenu;
    private JButton viewMeal;
    private JButton button;
    private JButton mainMenuQuestionButton;
    private JTextField textField;
    private JTextField mainMenuField;
    private JLabel menuImage;
    private JLabel currentLabel;
    private ComponentAdapter exitEvent;

    private static final int WIDTH = 700;
    private static final int LENGTH = 850;
    private boolean status;

    //MODIFIES: this
    //EFFECTS: initiate a new gui
    public GraphicalInterface() {
        super("Food Catalogue");

        exitEvent = new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                currentBook.printLog();
                ((JFrame) (e.getComponent())).dispose();
            }
        };

        status = false;
        currentBook = new Catalogue("Untitled");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        frame = new JFrame();
        panel = new JPanel();
        setFrame();
        setUpMenu();

    }

    //MODIFIES: this
    //EFFECTS: Sets up the frame for the application.
    private void setFrame() {
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, LENGTH);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.addComponentListener(exitEvent);
    }

    //MODIFIES: this
    //EFFECTS: sets up the user interface for the main menu for the user.
    private void setUpMenu() {
        panel.setBounds(5, 345, 685, 420);
        panel.setLayout(new GridLayout(0, 2));

        menuImage = new JLabel("Please select from the options below!");
        menuImage.setFont(new Font("Ariel", Font.BOLD, 20));
        menuImage.setIcon(new ImageIcon("./data/CatalogueImage.jpg"));
        menuImage.setHorizontalTextPosition(JLabel.CENTER);
        menuImage.setVerticalTextPosition(JLabel.BOTTOM);
        menuImage.setVerticalAlignment(JLabel.TOP);
        menuImage.setHorizontalAlignment(JLabel.CENTER);
        frame.add(menuImage);
        setUpTextfieldMenu();
        setUpButtons();
        menuImage.revalidate();

        currentPage = new JPanel();
        currentButtons = new JPanel();
        currentPage.setVisible(false);
        currentButtons.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the buttons for the main menu.
    private void setUpButtons() {
        setUpInformation();
        setUpChangeCatalogueName();
        setUpAddMeal();
        setUpEditMeal();
        setUpRemoveMeal();
        setUpFeastMeal();
        setUpSaveCatalogue();
        setUpLoadCatalogue();
        setUpViewMeal();

        exitToMenu = new JButton("Exit to Menu");
        exitToMenu.setFont(new Font("Ariel", Font.BOLD, 20));
        exitToMenu.addActionListener(this);
        exitToMenu.setSize(750, 200);

        panel.add(information);
        panel.add(changeCatalogueName);
        panel.add(addMeal);
        panel.add(editMeal);
        panel.add(viewMeal);
        panel.add(feastMeal);
        panel.add(saveCatalogue);
        panel.add(loadCatalogue);
        frame.revalidate();
        panel.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: Sets up the textfield for the main menu.
    private void setUpTextfieldMenu() {
        menuQuestion = new JPanel();
        mainMenuField = new JTextField("What would you like to do?", 15);
        mainMenuField.setMaximumSize(mainMenuField.getPreferredSize());
        mainMenuField.setFont(new Font("Ariel", Font.BOLD, 20));
        menuQuestion.add(mainMenuField);
        mainMenuQuestionButton = new JButton("Submit");
        mainMenuQuestionButton.setFont(new Font("Ariel", Font.BOLD, 20));
        menuQuestion.add(mainMenuQuestionButton);
        frame.add(menuQuestion, BorderLayout.SOUTH);
        mainMenuQuestionButton.addActionListener(event -> menuQuestion());
    }

    //MODIFIES: this
    //EFFECTS: User input through text
    private void menuQuestion() {
        String tmp = mainMenuField.getText();
        tmp = tmp.toLowerCase();
        if (tmp.equals("add a meal") || tmp.equals("add") || tmp.equals("a")) {
            makeMenuInvisible();
            doAddMeal();
        } else if (tmp.equals("information of catalogue") || tmp.equals("information") || tmp.equals("i")) {
            makeMenuInvisible();
            displayInformation();
        } else if (tmp.equals("change catalogue name") || tmp.equals("change") || tmp.equals("c")) {
            makeMenuInvisible();
            doChangeCatalogueName();
        } else if (tmp.equals("edit a meal") || tmp.equals("edit") || tmp.equals("e")) {
            makeMenuInvisible();
            doEditMeal();
        } else if (tmp.equals("view details of a meal") || tmp.equals("view") || tmp.equals("v")) {
            makeMenuInvisible();
            doViewMeal();
        } else {
            menuQuestionHelper(tmp);
        }
    }

    //MODIFIES: this
    //EFFECTS: User input through text continued.
    private void menuQuestionHelper(String tmp) {
        if (tmp.equals("feast on a meal") || tmp.equals("feast") || tmp.equals("f")) {
            makeMenuInvisible();
            doFeastMeal();
        } else if (tmp.equals("save catalogue") || tmp.equals("save") || tmp.equals("s")) {
            doSaveCatalogue();
        } else if (tmp.equals("load save") || tmp.equals("load") || tmp.equals("l")) {
            doLoadCatalogue();
        }
    }

    //MODIFIES: this
    //EFFECTS: process user input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == information) {
            makeMenuInvisible();
            displayInformation();
        } else if (e.getSource() == changeCatalogueName) {
            makeMenuInvisible();
            doChangeCatalogueName();
        } else if (e.getSource() == editMeal) {
            makeMenuInvisible();
            doEditMeal();
        } else if (e.getSource() == addMeal) {
            makeMenuInvisible();
            doAddMeal();
        } else if (e.getSource() == removeMeal) {
            makeMenuInvisible();
            doRemoveMeal();
        } else {
            actionPerformedMore(e);
        }
    }

    //MODIFIES: this
    //EFFECTS: user input process continued.
    private void actionPerformedMore(ActionEvent e) {
        if (e.getSource() == feastMeal) {
            makeMenuInvisible();
            doFeastMeal();
        } else if (e.getSource() == saveCatalogue) {
            doSaveCatalogue();
        } else if (e.getSource() == loadCatalogue) {
            doLoadCatalogue();
        } else if (e.getSource() == viewMeal) {
            makeMenuInvisible();
            doViewMeal();
        } else if (e.getSource() == exitToMenu) {
            exitToMenu.setVisible(false);
            clearMenuPanels();
            makeMenuVisible();
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for loading the catalogue.
    private void setUpLoadCatalogue() {
        loadCatalogue = new JButton("Load Save");
        loadCatalogue.setFont(new Font("Ariel", Font.BOLD, 20));
        loadCatalogue.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for saving the catalogue.
    private void setUpSaveCatalogue() {
        saveCatalogue = new JButton("Save Catalogue");
        saveCatalogue.setFont(new Font("Ariel", Font.BOLD, 20));
        saveCatalogue.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for feasting in the catalogue.
    private void setUpFeastMeal() {
        feastMeal = new JButton("Feast on a Meal!");
        feastMeal.setFont(new Font("Ariel", Font.BOLD, 20));
        feastMeal.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for removing a meal from the catalogue.
    private void setUpRemoveMeal() {
        removeMeal = new JButton("Remove a Meal");
        removeMeal.setFont(new Font("Ariel", Font.BOLD, 20));
        removeMeal.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for adding a meal in the catalogue.
    private void setUpAddMeal() {
        addMeal = new JButton("Add a Meal");
        addMeal.setFont(new Font("Ariel", Font.BOLD, 20));
        addMeal.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for editing a meal in the catalogue.
    private void setUpEditMeal() {
        editMeal = new JButton("Edit a Meal");
        editMeal.setFont(new Font("Ariel", Font.BOLD, 20));
        editMeal.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for changing the name of the catalogue.
    private void setUpChangeCatalogueName() {
        changeCatalogueName = new JButton("Change Catalogue Name");
        changeCatalogueName.setFont(new Font("Ariel", Font.BOLD, 20));
        changeCatalogueName.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for displaying info about the catalogue.
    private void setUpInformation() {
        information = new JButton("Information of Catalogue");
        information.setFont(new Font("Ariel", Font.BOLD, 20));
        information.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Sets up the button for viewing details of a meal in the catalogue.
    private void setUpViewMeal() {
        viewMeal = new JButton("View Details of a Meal");
        viewMeal.setFont(new Font("Ariel", Font.BOLD, 20));
        viewMeal.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Edits the meal, with either removing, changing name or adding more ingredients.
    private void doEditMeal() {
        JButton button = new JButton("Change a Meal Name");
        button.setFocusable(false);
        button.setFont(new Font("Ariel", Font.BOLD, 20));

        JButton buttonTwo = new JButton("Edit a Meal's Ingredients");
        buttonTwo.setFocusable(false);
        buttonTwo.setFont(new Font("Ariel", Font.BOLD, 20));

        JButton buttonThree = new JButton("Remove a Meal");
        buttonThree.setFocusable(false);
        buttonThree.setFont(new Font("Ariel", Font.BOLD, 20));

        currentButtons = new JPanel();
        currentButtons.setLayout(new GridLayout(0, 1));
        currentButtons.add(button);
        currentButtons.add(buttonTwo);
        currentButtons.add(buttonThree);
        currentButtons.add(exitToMenu);

        frame.add(currentButtons);

        button.addActionListener(event -> changeMealName());
        buttonTwo.addActionListener(event -> addMealEventIngredientHelper());
        buttonThree.addActionListener(event -> doRemoveMeal());
    }

    //MODIFIES: this
    //EFFECTS: Helper function for adding ingredients for a meal.
    private void addMealEventIngredientHelper() {
        makeFrameInvisible();

        currentPage = new JPanel();
        currentPage.setLayout(new GridLayout(0, 1));

        addButtons("Submit Name");

        labelMaker("Please enter the name of the meal:");
        textfieldMaker("Enter food here");

        currentPageAdd();

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                addMealEventIngredientEvent();
            } catch (NotInCatalogueException e) {
                exceptionHandler("Meal is not in catalogue.");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Event for adding an ingredient.
    private void addMealEventIngredientEvent() throws NotInCatalogueException {
        if (!currentBook.hasSpecificMeal(textField.getText())) {
            throw new NotInCatalogueException();
        }
        Meal currentMeal = currentBook.getSpecificMeal(textField.getText());
        makeFrameInvisible();

        currentPage = new JPanel();
        currentPage.setLayout(new FlowLayout());

        addButtons("Submit Name");

        labelMaker("Enter the name of the ingredient:");
        textfieldMaker("Enter Ingredient here");

        currentPageAdd();

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                addIngredientEvent(currentMeal);
            } catch (DuplicateException e) {
                exceptionHandler("Duplication issue.");
            } catch (EmptyNameException e) {
                exceptionHandler("Cannot use an empty string...");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: function for changing the name of a meal.
    private void changeMealName() {
        makeFrameInvisible();

        currentPage = new JPanel(new GridLayout(5, 5));

        addButtons("Submit Name");

        labelMaker("Please enter the name of the meal:");
        textfieldMaker("Enter food here");

        JLabel newLabel = newLabelMaker("New Name:");
        JTextField newName = newTextFieldMaker("Enter food here");

        currentPageAdd();
        currentPage.add(newLabel);
        currentPage.add(newName);

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                changeMealNameEvent(textField.getText(), newName.getText());
            } catch (DuplicateException e) {
                exceptionHandler("Duplicate issue.");
            } catch (EmptyNameException e) {
                exceptionHandler("Cannot use an empty name.");
            } catch (NotInCatalogueException e) {
                exceptionHandler("Meal not in list.");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Action event handler for changing the meal name.
    private void changeMealNameEvent(String oldName, String newName) throws DuplicateException,
            EmptyNameException, NotInCatalogueException {
        if (newName.equals("")) {
            throw new EmptyNameException();
        } else if (currentBook.hasSpecificMeal(newName)) {
            throw new DuplicateException();
        } else if (!currentBook.hasSpecificMeal(oldName)) {
            throw new NotInCatalogueException();
        }
        currentBook.changeMealName(oldName, newName);
    }

    //MODIFIES: this
    //EFFECTS: Function for removing meals in the catalogue.
    private void doRemoveMeal() {
        makeFrameInvisible();

        currentPage = new JPanel();
        currentPage.setLayout(new FlowLayout());

        addButtons("Submit Name");

        labelMaker("Please enter the name of the meal:");
        textfieldMaker("Enter food here");

        currentPageAdd();

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                removeMealEvent();
            } catch (NotInCatalogueException e) {
                exceptionHandler("Meal not in catalogue.");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Action event handler for removing a meal.
    private void removeMealEvent() throws NotInCatalogueException {
        if (!currentBook.hasSpecificMeal(textField.getText())) {
            throw new NotInCatalogueException();
        }
        currentBook.removeMeal(textField.getText());
    }

    //MODIFIES: this
    //EFFECTS: Function for adding meals.
    private void doAddMeal() {
        makeFrameInvisible();
        addButtons("Submit Name");

        currentPage = new JPanel();
        currentPage.setLayout(new FlowLayout());

        labelMaker("Please enter the name of the new meal:");
        textfieldMaker("Enter food here");
        currentPageAdd();

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                addMealEvent();
            } catch (DuplicateException e) {
                exceptionHandler("Duplicate issue.");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Action event handler for adding meals.
    private void addMealEvent() throws DuplicateException {
        if (currentBook.hasSpecificMeal(textField.getText())) {
            throw new DuplicateException();
        }
        status = true;
        makeFrameInvisible();

        currentPage = new JPanel(new FlowLayout());

        addButtons("Yes");

        labelMaker("Would you like to add ingredients?");
        currentPage.add(currentLabel);

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        Meal currentMeal = new Meal(textField.getText());
        currentBook.addMeal(currentMeal);
        button.addActionListener(event -> {
            try {
                addMealEventIngredient(currentMeal);
            } catch (EmptyNameException e) {
                exceptionHandler("Cannot use empty response...");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Action event for adding ingredients for adding a meal.
    private void addMealEventIngredient(Meal currentMeal) throws EmptyNameException {
        status = false;
        if (textField.getText().equals("")) {
            throw new EmptyNameException();
        }
        makeFrameInvisible();

        currentPage = new JPanel(new FlowLayout());

        addButtons("Submit Name");
        labelMaker("Please enter the name of the ingredient:");
        textfieldMaker("Enter ingredient here");

        currentPageAdd();

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                addIngredientEvent(currentMeal);
            } catch (DuplicateException e) {
                exceptionHandler("Duplicate issues.");
            } catch (EmptyNameException e) {
                exceptionHandler("Cannot put an empty thing...");
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: Action event handler for adding ingredients.
    private void addIngredientEvent(Meal currentMeal) throws DuplicateException, EmptyNameException {
        if (textField.getText().equals("")) {
            throw new EmptyNameException();
        } else if (currentMeal.getIngredients().contains(textField.getText())) {
            throw new DuplicateException();
        } else {
            currentMeal.addIngredients(textField.getText());
        }
    }

    //MODIFIES: this
    //EFFECTS: Function for displaying information.
    private void displayInformation() {
        String tmp = "<html>Displaying information on Catalogue <br/>" + currentBook + "</html>";

        currentPage = new JPanel();
        currentPage.setLayout(new GridLayout(0, 1));
        currentPage.setBounds(0, 750, WIDTH, 250);

        currentLabel = new JLabel(tmp);
        currentLabel.setHorizontalTextPosition(JLabel.LEFT);
        currentLabel.setVerticalTextPosition(JLabel.TOP);
        currentLabel.setFont(new Font("Ariel", Font.BOLD, 20));

        exitToMenu.setVisible(true);
        exitToMenu.setHorizontalAlignment(JLabel.CENTER);
        exitToMenu.setVerticalAlignment(JLabel.TOP);
        currentPage.add(exitToMenu);

        frame.add(currentLabel, BorderLayout.NORTH);
        frame.add(currentPage, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: Function for changing the catalogue name.
    private void doChangeCatalogueName() {
        currentPage = new JPanel();
        currentPage.setLayout(new FlowLayout());

        addButtons("Submit Name");

        currentLabel = new JLabel("Please enter the new name:");
        currentLabel.setFont(new Font("Ariel", Font.BOLD, 20));

        textField = new JTextField(currentBook.getName(), 15);
        textField.setMaximumSize(textField.getPreferredSize());

        currentPage.add(currentLabel);
        currentPage.add(textField);

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                changeNameEvent();
            } catch (EmptyNameException e) {
                exceptionHandler("Cannot use an empty response...");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Action event handler for changing the catalogue name.
    private void changeNameEvent() throws EmptyNameException {
        if (textField.getText().isEmpty()) {
            throw new EmptyNameException();
        } else {
            currentBook.setName(textField.getText());
        }
    }

    //MODIFIES: this
    //EFFECTS: Function for viewing information of a meal.
    private void doViewMeal() {
        makeFrameInvisible();

        currentPage = new JPanel();
        currentPage.setLayout(new FlowLayout());

        addButtons("Submit Name");

        labelMaker("Please enter the name of the meal:");
        textfieldMaker("Enter food here");

        currentPage.add(currentLabel);
        currentPage.add(textField);

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                viewMealEvent();
            } catch (EmptyNameException e) {
                exceptionHandler("Cannot find an empty response...");
            } catch (NotInCatalogueException e) {
                exceptionHandler("Meal is not in the catalogue.");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Action event handler for viewing details of a meal.
    private void viewMealEvent() throws NotInCatalogueException, EmptyNameException {
        if (textField.getText().equals("")) {
            throw new EmptyNameException();
        } else if (!currentBook.hasSpecificMeal(textField.getText())) {
            throw new NotInCatalogueException();
        }
        makeFrameInvisible();

        Meal currentMeal = currentBook.getSpecificMeal(textField.getText());
        String tmp = "<html>Displaying information on a Meal <br/>" + currentMeal + "</html>";

        currentPage = new JPanel();
        currentPage.setLayout(new GridLayout(0, 1));
        currentPage.setBounds(0, 750, WIDTH, 250);

        currentLabel = new JLabel(tmp);
        currentLabel.setHorizontalTextPosition(JLabel.LEFT);
        currentLabel.setVerticalTextPosition(JLabel.TOP);
        currentLabel.setFont(new Font("Ariel", Font.BOLD, 20));

        exitToMenu.setVisible(true);
        exitToMenu.setHorizontalAlignment(JLabel.CENTER);
        exitToMenu.setVerticalAlignment(JLabel.TOP);
        currentPage.add(exitToMenu);

        frame.add(currentLabel, BorderLayout.NORTH);
        frame.add(currentPage, BorderLayout.SOUTH);
    }

    // Code Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: loads the catalogue
    private void doLoadCatalogue() {
        try {
            currentBook = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Code Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the catalogue
    private void doSaveCatalogue() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentBook);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: Function for feasting on a meal.
    private void doFeastMeal() {
        makeFrameInvisible();

        currentPage = new JPanel();
        currentPage.setLayout(new FlowLayout());

        addButtons("Submit Name");

        labelMaker("Please enter the name of the meal:");
        textfieldMaker("Enter food here");

        currentPage.add(currentLabel);
        currentPage.add(textField);

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(currentButtons, BorderLayout.SOUTH);

        button.addActionListener(event -> {
            try {
                eatMealEvent();
            } catch (EmptyNameException e) {
                exceptionHandler("Cannot use an empty response...");
            } catch (NotInCatalogueException e) {
                exceptionHandler("Meal is not in the catalogue.");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Action event handler for feasting on a meal.
    private void eatMealEvent() throws NotInCatalogueException, EmptyNameException {
        if (textField.getText().equals("")) {
            throw new EmptyNameException();
        } else if (!currentBook.hasSpecificMeal(textField.getText())) {
            throw new NotInCatalogueException();
        } else {
            currentBook.getSpecificMeal(textField.getText()).increaseAmountEaten();
        }
    }

    //MODIFIES: this
    //EFFECTS: helper function to add buttons for various functions.
    private void addButtons(String tmp) {
        button = new JButton(tmp);
        button.setFocusable(false);
        button.setFont(new Font("Ariel", Font.BOLD, 20));
        currentButtons = new JPanel();
        currentButtons.add(button);
        currentButtons.add(exitToMenu);
    }

    //MODIFIES: this
    //EFFECTS: Helper function for adding labels for various functions.
    private void labelMaker(String tmp) {
        currentLabel = new JLabel(tmp);
        currentLabel.setFont(new Font("Ariel", Font.BOLD, 20));
    }

    //MODIFIES: this
    //EFFECTS: Helper function for making a new labels for various functions.
    private JLabel newLabelMaker(String tmp) {
        JLabel newLabel = new JLabel(tmp);
        newLabel.setFont(new Font("Ariel", Font.BOLD, 20));
        return newLabel;
    }

    //MODIFIES: this
    //EFFECTS: Helper function for adding text fields for various functions.
    private void textfieldMaker(String tmp) {
        textField = new JTextField(tmp, 15);
        textField.setMaximumSize(textField.getPreferredSize());
    }

    //MODIFIES: this
    //EFFECTS: Helper function for making new text fields for various functions.
    private JTextField newTextFieldMaker(String tmp) {
        JTextField newName = new JTextField(tmp, 15);
        newName.setMaximumSize(textField.getPreferredSize());
        return newName;
    }

    //MODIFIES: this
    //EFFECTS: Helper function for adding labels and text fields for current page.
    private void currentPageAdd() {
        currentPage.add(currentLabel);
        currentPage.add(textField);
    }

    //MODIFIES: this
    //EFFECTS: Function for handling exceptions.
    private void exceptionHandler(String tmp) {
        makeFrameInvisible();

        currentPage = new JPanel();
        currentPage.setLayout(new FlowLayout());

        currentLabel = new JLabel("ERROR: " + tmp);
        currentLabel.setFont(new Font("Ariel", Font.BOLD, 20));

        currentPage.add(currentLabel);

        frame.add(currentPage, BorderLayout.NORTH);
        frame.add(exitToMenu, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: Helper function for making the menu invisible.
    private void makeFrameInvisible() {
        currentPage.setVisible(false);
        currentButtons.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: Helper function for clearing the application screen.
    private void clearMenuPanels() {
        if (!status) {
            frame.remove(currentPage);
            frame.remove(currentButtons);
            frame.remove(currentLabel);
        } else {
            frame.remove(currentPage);
            frame.remove(currentButtons);
            frame.remove(currentLabel);
            currentBook.addMeal(new Meal(textField.getText()));
        }
        exitToMenu = new JButton("Exit to Menu");
        exitToMenu.setFont(new Font("Ariel", Font.BOLD, 20));
        exitToMenu.addActionListener(this);
        status = false;
        frame.revalidate();
        panel.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: Helper function for making the menu invisible.
    private void makeMenuInvisible() {
        menuImage.setVisible(false);
        panel.setVisible(false);
        menuQuestion.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: Helper function for making the menu visible.
    private void makeMenuVisible() {
        menuImage.setVisible(true);
        panel.setVisible(true);
        menuQuestion.setVisible(true);
    }
}
