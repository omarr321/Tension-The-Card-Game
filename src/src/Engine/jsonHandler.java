package Engine;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class jsonHandler {
    private String jsonString;
    private Gson gsonObj = new Gson();
    private JsonObject obj = new JsonObject();

    public jsonHandler(String cat, String cardName) {
        String currentWorkingDirectory = new File("").getAbsolutePath();
        Path filePath = Paths.get(currentWorkingDirectory, "src", "database", "cards", cat, cardName);

        System.out.println("Card File: " + filePath.toString());

        try {
            jsonString = Files.readString(filePath);
            obj = gsonObj.fromJson(jsonString, obj.getClass());
        } catch (IOException ex) {
            //TODO: Error out to the logging
        }
    }

    public String getStringKey(String[] arr) {
        JsonObject temp = obj;
        for (int i = 0; i < arr.length-1; i++) {
            temp = obj.getAsJsonObject(arr[i]);
        }
        if (!temp.isJsonPrimitive()) {
            return temp.get(arr[arr.length-1]).toString();
        } else {
            return temp.get(arr[arr.length-1]).getAsString();
        }
    }

    public int getIntKey(String[] arr) {
        JsonObject temp = obj;
        for (int i = 0; i < arr.length-1; i++) {
            temp = obj.getAsJsonObject(arr[i]);
        }
        return temp.get(arr[arr.length-1]).getAsInt();
    }
}
