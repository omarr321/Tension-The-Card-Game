package Engine;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class jsonHandler {
    private JsonObject obj = new JsonObject();

    public jsonHandler(String category, String cardName) {
        String currentWorkingDirectory = new File("").getAbsolutePath();
        Path filePath = Paths.get(currentWorkingDirectory, "src", "database", "cards", category, cardName + ".crd");

        System.out.println("Card File: " + filePath.toString());

        try {
            String jsonString = Files.readString(filePath);
            Gson gsonObj = new Gson();
            obj = gsonObj.fromJson(jsonString, obj.getClass());
        } catch (IOException ex) {
            //TODO: Error out to the logging
        }
    }

    public String getStringKey(String keyAddress) {
        JsonObject temp = obj;
        String[] arr = keyAddress.split("\\.");

        for (int i = 0; i < arr.length-1; i++) {
            temp = obj.getAsJsonObject(arr[i]);
        }

        return temp.get(arr[arr.length-1]).getAsString();
    }

    public int getIntKey(String keyAddress) {
        JsonObject temp = obj;
        String[] arr = keyAddress.split("\\.");

        for (int i = 0; i < arr.length-1; i++) {
            temp = obj.getAsJsonObject(arr[i]);
        }

        return temp.get(arr[arr.length-1]).getAsInt();
    }
}
