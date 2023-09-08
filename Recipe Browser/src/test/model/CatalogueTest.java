package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Testing for the catalogue class.
public class CatalogueTest {
    private Catalogue mealBook;

    @BeforeEach
    public void setUp() {
        mealBook = new Catalogue("My Cook Book!!!");
    }

    @Test
    void testConstructor() {
        assertEquals("My Cook Book!!!", mealBook.getName());
        assertEquals(0, mealBook.getMealNames().size());
        assertEquals(0, mealBook.getMealCatalogue().size());
    }

    @Test
    void testAddMeal() {
        Meal hotdog = new Meal("Hot Dog");
        Meal hamburger = new Meal("Hamburger");

        assertTrue(mealBook.addMeal(hotdog));
        assertTrue(mealBook.addMeal(hamburger));
        assertEquals(2, mealBook.getMealNames().size());
        assertEquals(2, mealBook.getMealCatalogue().size());
        assertEquals("Hot Dog", mealBook.getMealNames().get(0));
        assertEquals("Hamburger", mealBook.getMealNames().get(1));
        assertEquals(hotdog, mealBook.getMealCatalogue().get(0));
        assertEquals(hamburger, mealBook.getMealCatalogue().get(1));
    }

    @Test
    void testAddMealFail() {
        Meal hotdog = new Meal("Hot Dog");
        assertTrue(mealBook.addMeal(hotdog));
        assertFalse(mealBook.addMeal(hotdog));
        assertEquals(1, mealBook.getMealNames().size());
        assertEquals(1, mealBook.getMealCatalogue().size());
        assertEquals("Hot Dog", mealBook.getMealNames().get(0));
        assertEquals(hotdog, mealBook.getMealCatalogue().get(0));
    }


    public void addMeals() {
        Meal hotdog = new Meal("Hot Dog");
        Meal hamburger = new Meal("Hamburger");
        mealBook.addMeal(hotdog);
        mealBook.addMeal(hamburger);
    }

    @Test
    public void testRemoveMeals() {
        addMeals();
        assertTrue(mealBook.removeMeal("Hot Dog"));
        assertEquals(1, mealBook.getMealNames().size());
        assertEquals(1, mealBook.getMealCatalogue().size());
        assertEquals("Hamburger", mealBook.getMealNames().get(0));

        assertTrue(mealBook.removeMeal("Hamburger"));
        assertEquals(0, mealBook.getMealNames().size());
        assertEquals(0, mealBook.getMealCatalogue().size());
    }

    @Test
    public void testRemoveMealsFail() {
        addMeals();
        assertFalse(mealBook.removeMeal("Grilled Cheese"));
        assertEquals(2, mealBook.getMealNames().size());
        assertEquals(2, mealBook.getMealCatalogue().size());
        assertEquals("Hot Dog", mealBook.getMealNames().get(0));
        assertEquals("Hamburger", mealBook.getMealNames().get(1));
    }

    @Test
    public void testGetMeal() {
        Meal grilledCheese = new Meal("Grilled Cheese");
        grilledCheese.addIngredients("Bread");
        grilledCheese.addIngredients("Cheese");
        mealBook.addMeal(grilledCheese);
        Meal tmp = mealBook.getSpecificMeal("Grilled Cheese");
        assertEquals("Grilled Cheese", tmp.getName());
        assertEquals("Bread", tmp.getIngredients().get(0));
        assertEquals("Cheese", tmp.getIngredients().get(1));
    }

    @Test
    public void testGetMealFail() {
        Meal grilledCheese = new Meal("Grilled Cheese");
        grilledCheese.addIngredients("Bread");
        grilledCheese.addIngredients("Cheese");
        mealBook.addMeal(grilledCheese);
        assertEquals(null, mealBook.getSpecificMeal("Potato Salad"));
    }

    @Test
    public void testAmountEaten() {
        Meal grilledCheese = new Meal("Grilled Cheese");
        grilledCheese.increaseAmountEaten();
        grilledCheese.increaseAmountEaten();
        mealBook.addMeal(grilledCheese);
        assertEquals(2, mealBook.getSpecificMeal("Grilled Cheese").getAmountEaten());
    }


    @Test
    public void testSetName() {
        mealBook.setName("Another name for a cookbook");
        assertEquals("Another name for a cookbook", mealBook.getName());
    }

    @Test
    public void testSetNameMultiple() {
        mealBook.setName("Potato");
        mealBook.setName("John");
        mealBook.setName("Pizza");
        assertEquals("Pizza", mealBook.getName());
    }

    @Test
    public void testChangeMealName() {
        addMeals();
        assertEquals("Hot Dog", mealBook.getMealNames().get(0));
        assertTrue(mealBook.changeMealName("Hot Dog", "French fries"));
        assertEquals("French fries", mealBook.getMealNames().get(0));
    }

    @Test
    public void testChangeMealNameFails() {
        addMeals();
        assertEquals("Hot Dog", mealBook.getMealNames().get(0));
        assertFalse(mealBook.changeMealName("Hot Dog", "Hamburger"));
        assertEquals("Hot Dog", mealBook.getMealNames().get(0));
    }

    @Test
    public void testHasSpecificMeal() {
        addMeals();
        assertTrue(mealBook.hasSpecificMeal("Hot Dog"));
    }

    @Test
    public void testHasSpecificMealFalse() {
        addMeals();
        assertFalse(mealBook.hasSpecificMeal("Ramen"));
    }

    @Test
    public void testPrintLog() {
        addMeals();
        mealBook.printLog();
    }

    @Test
    public void testPrintChangeMealName() {
        addMeals();
        EventLog.getInstance().clear();
        mealBook.changeMealName("Hot Dog", "Ramen");
        mealBook.printLog();
    }

    @Test
    public void testPrintRemoveMeal() {
        addMeals();
        EventLog.getInstance().clear();
        mealBook.removeMeal("Hot Dog");
        mealBook.printLog();
    }

    @Test
    public void testToString() {
        addMeals();
        assertTrue(mealBook.toString().contains("Catalogue Name: My Cook Book!!!<br/> Amount of meals: 2<br/> Meals: <br/><br/>Hot Dog<br/>Hamburger"));
    }
}
