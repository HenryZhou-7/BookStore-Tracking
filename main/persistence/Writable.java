package persistence;

import org.json.JSONObject;

// Writeable is an interface that makes other classes override toJson.
public interface Writable {
    // Citation: from example project
    // EFFECTS: returns this as JSON Object
    JSONObject toJson();
}
