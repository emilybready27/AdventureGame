import org.glassfish.grizzly.http.server.HttpServer;
import student.adventure.UserInteraction;
import student.server.AdventureResource;
import student.server.AdventureServer;
import student.server.AdventureService;

import java.io.IOException;

public class Main {
    private final static boolean playWithConsole = false;

    public static void main(String[] args) {
        if (playWithConsole) {
            UserInteraction.setUp();
            UserInteraction.play();
        } else {
            try {
                HttpServer server = AdventureServer.createServer(AdventureResource.class);
                server.start();
            } catch (IOException e) {
                System.out.println("Server error.");
            }
        }
    }
}
