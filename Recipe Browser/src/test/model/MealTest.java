package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Testing for the catalogue class.
public class MealTest {
    private Meal myMeal;
    private Catalogue tmp;

    @BeforeEach
    public void setUp() {
        myMeal = new Meal("Hamburger");
        tmp = new Catalogue("TMP");
    }

    @Test
    public void testConstructor() {
        assertEquals("Hamburger", myMeal.getName());
        assertEquals(0, myMeal.getIngredients().size());
        assertEquals(0, myMeal.getAmountEaten());
    }

    public void addIngredients() {
        myMeal.addIngredients("Buns");
        myMeal.addIngredients("Patty");
    }

    @Test
    public void testAddIngredients() {
        addIngredients();
        assertTrue(myMeal.addIngredients("Tomatoes"));
        assertEquals(3, myMeal.getIngredients().size());
        assertEquals("Buns", myMeal.getIngredients().get(0));
        assertEquals("Patty", myMeal.getIngredients().get(1));
        assertEquals("Tomatoes", myMeal.getIngredients().get(2));
    }

    @Test
    public void testAddIngredientsWithRepeats() {
        addIngredients();
        assertFalse(myMeal.addIngredients("Buns"));
        assertEquals(2, myMeal.getIngredients().size());
        assertEquals("Buns", myMeal.getIngredients().get(0));
        assertEquals("Patty", myMeal.getIngredients().get(1));
    }

    @Test
    public void testRemoveIngredients() {
        addIngredients();
        assertTrue(myMeal.removeIngredients("Patty"));
        assertEquals(1, myMeal.getIngredients().size());
        assertEquals("Buns", myMeal.getIngredients().get(0));
    }

    @Test
    public void testRemoveIngredientsFail() {
        addIngredients();
        assertFalse(myMeal.removeIngredients("Tomatoes"));
        assertEquals(2, myMeal.getIngredients().size());
        assertEquals("Buns", myMeal.getIngredients().get(0));
        assertEquals("Patty", myMeal.getIngredients().get(1));
    }

    @Test
    public void testIncreaseAmountEaten() {
        myMeal.increaseAmountEaten();
        assertEquals(1, myMeal.getAmountEaten());
    }

    @Test
    public void testIncreaseAmountEatenMutliple() {
        myMeal.increaseAmountEaten();
        myMeal.increaseAmountEaten();
        myMeal.increaseAmountEaten();
        assertEquals(3, myMeal.getAmountEaten());
    }

    @Test
    public void testSetName() {
        myMeal.setName("Potato");
        assertEquals("Potato", myMeal.getName());
    }

    @Test
    public void testSetNameMultiple() {
        myMeal.setName("Potato");
        myMeal.setName("John");
        myMeal.setName("Pizza");
        assertEquals("Pizza", myMeal.getName());
    }

    @Test
    public void testToString() {
        EventLog.getInstance().clear();
        myMeal.increaseAmountEaten();
        myMeal.increaseAmountEaten();
        addIngredients();
        assertTrue(myMeal.toString().contains("Meal Name: Hamburger<br/> Amount of times Eaten: 2<br/> Ingredients: <br/><br/>Buns<br/>Patty"));
        tmp.printLog();
    }

    @Test
    public void testAddIngredientsPrint() {
        addIngredients();
        EventLog.getInstance().clear();
        myMeal.removeIngredients("Buns");
        tmp.printLog();
    }
}
