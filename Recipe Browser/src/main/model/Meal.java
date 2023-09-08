package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a meal that holds the ingredients to make it.
public class Meal implements Writable {
    private int amountEaten;
    private String name;
    private List<String> ingredients;

    /*
     * REQUIRES: Name is not an empty string
     * EFFECTS: name of the meal is set to name,
     *          the number of times eaten is set to zero
     *          and creates a new list of ingredients.
     */
    public Meal(String name) {
        amountEaten = 0;
        this.name = name;
        ingredients = new ArrayList<String>();
    }

    /*
     * REQUIRES: Name is not an empty string
     *           amountEaten >= 0
     *           ingredients is not null.
     * EFFECTS: name of the meal is set to name,
     *          the number of times eaten is set to amountEaten
     *          and appends a list of ingredients.
     */
    public Meal(String name, int amountEaten, ArrayList ingredients) {
        this.amountEaten = amountEaten;
        this.name = name;
        this.ingredients = ingredients;
    }

    /*
     * REQUIRES: ingredients is not an empty string
     * MODIFIES: this
     * EFFECTS: If ingredient is not in the list, adds it in and return true,
     *          otherwise nothing happens and return false.
     */
    public boolean addIngredients(String ingredient) {
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
            EventLog.getInstance().logEvent(new Event(ingredient + " has been added as an ingredient to: " + name));
            return true;
        } else {
            return false;
        }
    }

    /*
     * REQUIRES: ingredients is not an empty string
     * MODIFIES: this
     * EFFECTS: If ingredient is in the list, removes it and return true,
     *          otherwise nothing happens and return false.
     */
    public boolean removeIngredients(String ingredient) {
        if (ingredients.contains(ingredient)) {
            ingredients.remove(ingredient);
            EventLog.getInstance().logEvent(new Event(ingredient + " has been removed as an ingredient to: " + name));
            return true;
        } else {
            return false;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: increments the number of times this meal has
     *          been eaten by 1.
     */
    public void increaseAmountEaten() {
        EventLog.getInstance().logEvent(new Event(name + " has been eaten"));
        amountEaten++;
    }

    public void setName(String name) {
        this.name = name;
        EventLog.getInstance().logEvent(new Event(this.name + " has been changed to: " + name));
    }

    public String getName() {
        return this.name;
    }

    public int getAmountEaten() {
        return this.amountEaten;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    /*
     * EFFECTS: returns a string representation of Meal
     */
    @Override
    public String toString() {
        String tmp = "Meal Name: " + this.name + "<br/> Amount of times Eaten: " + amountEaten
                + "<br/> Ingredients: <br/>";
        for (String ingredient : ingredients) {
            tmp = tmp + "<br/>" + ingredient;
        }
        return tmp;

    }

    /*
     * EFFECTS: Converts the meal into Json
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("meal name", name);
        json.put("amount eaten", amountEaten);
        json.put("ingredients", ingredientToJson());
        return json;
    }

    // EFFECTS: returns ingredients in this meal as a JSON array
    private JSONArray ingredientToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String s : ingredients) {
            jsonArray.put(s);
        }

        return jsonArray;
    }
}
