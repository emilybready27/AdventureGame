package student.adventure;

import static org.junit.Assert.assertThat;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class AdventureTest {
    @Before
    public void setUp() {
        // This is run before every test.
    }

    @Test
    public void sanityCheck() throws IOException {
        Gson gson = new Gson();
        String json = readFileAsString("src/main/resources/westeros.json");
        Layout layout = gson.fromJson(json, Layout.class);
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