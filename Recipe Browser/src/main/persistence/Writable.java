package persistence;

import org.json.JSONObject;

//Interface for Writable.
public interface Writable {
    // EFFECTS: returns this as JSON object
    public JSONObject toJson();
}
