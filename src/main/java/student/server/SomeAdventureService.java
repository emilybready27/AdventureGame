package student.server;

import student.adventure.AdventureGame;
import student.server.AdventureException;
import student.server.AdventureService;
import student.server.Command;
import student.server.GameStatus;

import java.util.Map;
import java.util.SortedMap;

public class SomeAdventureService implements AdventureService {
    private Map<Integer, AdventureGame> games;

    @Override
    public void reset() {

    }

    @Override
    public int newGame() throws AdventureException {
        return 0;
    }

    @Override
    public GameStatus getGame(int id) {
        return null;
    }

    @Override
    public boolean destroyGame(int id) {
        return false;
    }

    @Override
    public void executeCommand(int id, Command command) {

    }

    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }
}
