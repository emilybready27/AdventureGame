package student.server;

import student.adventure.AdventureGame;

import java.util.Map;
import java.util.SortedMap;

public class MyAdventureService implements AdventureService {
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
