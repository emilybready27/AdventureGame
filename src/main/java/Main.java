import com.google.gson.*;
import student.adventure.AdventureGame;
import student.adventure.Room;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the Adventure Game!");
        System.out.println("To get started, please input a valid JSON file.");

        // TODO: sanitize path, check for invalid: doesn't fit schema, file doesn't exist
        AdventureGame adventureGame;
        while (true) {
            System.out.print("> ");
            try {
                String path = getUserInput(); // "src/main/resources/westeros.json", "src/main/resources/malformed.json"
                String json = readFileAsString(path);
//                Gson gson = new Gson();
//                adventureGame = gson.fromJson(json, AdventureGame.class);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.registerTypeAdapter(AdventureGame.class, new AnnotatedDeserializer<AdventureGame>()).create();
                adventureGame = gson.fromJson(json, AdventureGame.class);
                System.out.println(adventureGame.getStartingRoom());
                adventureGame.printLayout();
                break;
            } catch (JsonParseException e) {
                System.out.println("Sorry, there was an error with your Json. Try again?");
            } catch (IOException e) {
                System.out.println("Sorry, there was an error with your file. Try again?");
            }
        }



    }


    public static String getUserInput() {
        // TODO: sanitize input
        return scanner.next();
    }

    /**
     * Transforms a JSON file's contents into a String to facilitate GSON parsing.
     * @param file A String for path to file
     * @return A String of the file's contents
     * @throws IOException
     */
    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}

// Code below inspired from https://stackoverflow.com/questions/14242236/let-gson-throw-exceptions-on-wrong-types.
class AnnotatedDeserializer<T> implements JsonDeserializer<T> {
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        T pojo = new Gson().fromJson(je, type);
        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getAnnotation(AdventureGame.JsonRequired.class) != null) {
                try {
                    f.setAccessible(true);
                    if (f.get(pojo) == null) {
                        throw new JsonParseException("Missing field in JSON: " + f.getName());
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(AnnotatedDeserializer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return pojo;
    }
}