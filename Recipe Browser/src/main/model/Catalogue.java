package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a catalogue that holds meals within it.
public class Catalogue implements Writable {
    private List<Meal> mealCatalogue;
    private List<String> mealIndex;
    private String name;

    /*
     * REQUIRES: Name is not an empty string
     * EFFECTS: name of the catalogue is set to name,
     *          new arraylist for the catalogue and another
     *          arraylist to hold the indexes are created.
     */
    public Catalogue(String name) {
        mealCatalogue = new ArrayList<Meal>();
        mealIndex = new ArrayList<String>();
        this.name = name;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Adds a meal into the catalogue.
     *          if meal with same name is already in list, do nothing and return false,
     *          otherwise add the new meal and its name in the two lists and return true.
     *
     */
    public boolean addMeal(Meal m) {
        if (!mealIndex.contains(m.getName())) {
            mealCatalogue.add(m);
            mealIndex.add(m.getName());
            EventLog.getInstance().logEvent(new Event(m.getName() + " has been added to: " + name));
            return true;
        } else {
            return false;
        }

    }

    /*
     * MODIFIES: this
     * EFFECTS: Removes a meal into the catalogue.
     *          if meal with same name is in list, remove the meal from both lists and return true,
     *          otherwise do nothing and return false.
     *
     */
    public boolean removeMeal(String name) {
        if (mealIndex.contains(name)) {
            mealCatalogue.remove(mealIndex.indexOf(name));
            mealIndex.remove(mealIndex.indexOf(name));
            EventLog.getInstance().logEvent(new Event(name + " has been removed from: " + this.name));
            return true;
        } else {
            return false;
        }
    }

    /*
     * EFFECTS: Returns a meal within the catalogue
     *          if meal with same name is in list, return the meal.
     *          otherwise do nothing and return null.
     *
     */
    public Meal getSpecificMeal(String name) {
        if (mealIndex.contains(name)) {
            return mealCatalogue.get(mealIndex.indexOf(name));
        } else {
            return null;
        }
    }

    /*
     * EFFECTS: Returns whether a meal is inside a catalogue or not.
     *
     */
    public boolean hasSpecificMeal(String name) {
        return mealIndex.contains(name);
    }

    /*
     * REQUIRES: oldName is an element in the mealIndex.
     * MODIFIES: this
     * EFFECTS: changes the name of the old name in the meal index to a new one.
     */
    public boolean changeMealName(String oldName, String newName) {
        if (!mealIndex.contains(newName)) {
            mealCatalogue.get(mealIndex.indexOf(oldName)).setName(newName);
            mealIndex.set(mealIndex.indexOf(oldName), newName);
            return true;
        }
        return false;
    }

    public void setName(String name) {
        EventLog.getInstance().logEvent(new Event("Catalogue name has been changed to: " + name));
        this.name = name;
    }

    public List<Meal> getMealCatalogue() {
        return mealCatalogue;
    }

    public List<String> getMealNames() {
        return mealIndex;
    }

    public String getName() {
        return this.name;
    }

    /*
     * EFFECTS: returns a string representation of Catalogue
     */
    @Override
    public String toString() {
        String tmp = "Catalogue Name: " + this.name + "<br/> Amount of meals: " + mealIndex.size()
                + "<br/> Meals: <br/>";
        for (String meals : mealIndex) {
            tmp = tmp + "<br/>" + meals;
        }
        return tmp;
    }

    /*
     * EFFECTS: Converts the catalogue into Json
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("meals", mealsToJson());
        return json;
    }

    // EFFECTS: returns the meals in a catalogue as a JSON array
    private JSONArray mealsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Meal m : mealCatalogue) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }

    // Prints the event logs to the console once the application is exited.
    public void printLog() {
        System.out.println("Event Log: ");
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
    }
}
