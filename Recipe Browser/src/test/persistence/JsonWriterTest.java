package persistence;

import model.Catalogue;
import model.Meal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Catalogue cata = new Catalogue("Name");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCatalogue() {
        try {
            Catalogue cata = new Catalogue("Name");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(cata);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            cata = reader.read();
            assertEquals(0, cata.getMealCatalogue().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCatalogue() {
        try {
            ArrayList<String> ingredient = new ArrayList<>();
            ingredient.add("Sausage");
            ingredient.add("buns");
            Catalogue cata = new Catalogue("Insane catalogue");
            cata.addMeal(new Meal("Hot Dog", 2, ingredient));
            cata.addMeal(new Meal("Hamburger"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(cata);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            cata = reader.read();
            assertEquals(2, cata.getMealCatalogue().size());
            assertEquals("Insane catalogue", cata.getName());
            assertEquals("Hot Dog", cata.getMealCatalogue().get(0).getName());
            assertEquals(ingredient, cata.getMealCatalogue().get(0).getIngredients());
            assertEquals(2, cata.getMealCatalogue().get(0).getAmountEaten());

            assertEquals("Hamburger", cata.getMealCatalogue().get(1).getName());
            assertEquals(0, cata.getMealCatalogue().get(1).getAmountEaten());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }
}
