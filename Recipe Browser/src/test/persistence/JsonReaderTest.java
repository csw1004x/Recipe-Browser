package persistence;

import model.Catalogue;
import model.CatalogueTest;
import model.Meal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Catalogue cata = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCatalogue() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            Catalogue cata = reader.read();
            assertEquals(0, cata.getMealCatalogue().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            Catalogue cata = reader.read();
            List<Meal> meals = cata.getMealCatalogue();
            assertEquals(2, meals.size());
            assertEquals(1, meals.get(0).getAmountEaten());
            assertEquals(0, meals.get(1).getAmountEaten());
            assertEquals("Ramen", meals.get(0).getName());
            assertEquals("Corn salad", meals.get(1).getName());
            assertTrue(meals.get(0).getIngredients().contains("Noodles"));
            assertTrue(meals.get(0).getIngredients().contains("Broth"));
            assertTrue(meals.get(0).getIngredients().contains("Chasu"));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
