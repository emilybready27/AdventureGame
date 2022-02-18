import org.glassfish.grizzly.http.server.HttpServer;
import student.adventure.UserInteraction;
import student.server.AdventureResource;
import student.server.AdventureServer;
import student.server.AdventureService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //UserInteraction.setUp();
        //UserInteraction.play();
        try {
            HttpServer server = AdventureServer.createServer(AdventureResource.class);
            server.start();
        } catch (IOException e) {
            System.out.println("Server error.");
        }
    }
}
