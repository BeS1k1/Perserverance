import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class FindPath implements RouteFinder {

    @Override
    public char[][] findRoute(char[][] map) {
        Pair<Integer, Integer> start = new Pair<>(-1, -1);
        Pair<Integer, Integer> finish = new Pair<>(-1, -1);
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> track = new HashMap<>();
        Queue<Pair<Integer, Integer>> queue = new LinkedList();
        startLabel:
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == '@') {
                    start = new Pair<>(i, j);
                    break startLabel;
                }
            }
        }
        queue.offer(start);
        BFSLabel:
        while (queue.peek() != null) {
            Pair<Integer, Integer> vertex = queue.remove();
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if (dx != 0 && dy != 0 || dx == 0 && dy == 0) continue;
                    if (vertex.getKey() + dx < 0 || vertex.getKey() + dx >= map.length ||
                            vertex.getValue() + dy < 0 || vertex.getValue() + dy >= map[0].length) continue;
                    if (map[vertex.getKey() + dx][vertex.getValue() + dy] == '#') continue;
                    Pair<Integer, Integer> vertex2 = new Pair<>(vertex.getKey() + dx, vertex.getValue() + dy);
                    if (track.containsKey(vertex2)) continue;

                    track.put(vertex2, vertex);
                    if (map[vertex2.getKey()][vertex2.getValue()] == 'X') {
                        finish = vertex2;
                        break BFSLabel;
                    }
                    queue.offer(vertex2);
                }
            }
        }
        if (finish.getKey() == -1)
            return null;

        Pair<Integer, Integer> pathItem = finish;

        while (pathItem != null) {
            pathItem = track.get(pathItem);
            if (pathItem.getKey().equals(start.getKey()) && pathItem.getValue().equals(start.getValue())) {
                break;
            }
            map[pathItem.getKey()][pathItem.getValue()] = '+';
        }
        return map;
    }
}
