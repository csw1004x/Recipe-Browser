package persistence;

import model.Catalogue;
import model.Meal;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Code citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // Code citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JsonReader(String source) {
        this.source = source;
    }

    // Code citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Catalogue read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCatalogue(jsonObject);
    }

    // Code citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // Code citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses workroom from JSON object and returns it
    private Catalogue parseCatalogue(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Catalogue cata = new Catalogue(name);
        addMeals(cata, jsonObject);
        return cata;
    }

    // MODIFIES: cata
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addMeals(Catalogue cata, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("meals");
        for (Object json : jsonArray) {
            JSONObject nextMeal = (JSONObject) json;
            addMeal(cata, nextMeal);
        }
    }

    // MODIFIES: cata
    // EFFECTS: adds meals into the cata
    private void addMeal(Catalogue cata, JSONObject jsonObject) {
        String name = jsonObject.getString("meal name");
        int amountEaten = jsonObject.getInt("amount eaten");
        ArrayList<String> myIngredients = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
        for (Object json : jsonArray) {
            myIngredients.add((String) json);
        }
        Meal meals = new Meal(name, amountEaten, myIngredients);
        cata.addMeal(meals);
    }
}
