package by.it.group551002.tereshchenko.lesson13;

import java.util.*;

public class GraphB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            return;
        }
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("no");
            return;
        }

        Map<String, Set<String>> graph = new HashMap<>();

        String[] edges = input.split(",");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            String from = parts[0].trim();
            String to = parts[1].trim();

            graph.putIfAbsent(from, new HashSet<>());
            graph.putIfAbsent(to, new HashSet<>());
            graph.get(from).add(to);
        }
        Map<String, Integer> color = new HashMap<>();
        boolean hasCycle = false;

        for (String vertex : graph.keySet()) {
            if (color.getOrDefault(vertex, 0) == 0) {
                if (dfs(vertex, graph, color)) {
                    hasCycle = true;
                    break;
                }
            }
        }

        System.out.println(hasCycle ? "yes" : "no");
    }

    private static boolean dfs(String current, Map<String, Set<String>> graph, Map<String, Integer> color) {
        color.put(current, 1);
        for (String neighbor : graph.get(current)) {
            int neighborColor = color.getOrDefault(neighbor, 0);

            if (neighborColor == 1) {
                return true;
            }
            if (neighborColor == 0 && dfs(neighbor, graph, color)) {
                return true;
            }
        }

        color.put(current, 2);
        return false;
    }
}
