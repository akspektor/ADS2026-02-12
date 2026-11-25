package by.it.group551002.tereshchenko.lesson13;

import java.util.*;

public class GraphC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            return;
        }
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return;
        }

        Map<String, Set<String>> graph = new TreeMap<>();
        Map<String, Set<String>> reverseGraph = new TreeMap<>();

        String[] edges = input.split(",");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            String from = parts[0].trim();
            String to = parts[1].trim();

            graph.putIfAbsent(from, new TreeSet<>());
            graph.putIfAbsent(to, new TreeSet<>());
            reverseGraph.putIfAbsent(from, new TreeSet<>());
            reverseGraph.putIfAbsent(to, new TreeSet<>());

            graph.get(from).add(to);
            reverseGraph.get(to).add(from);
        }

        Set<String> visited = new HashSet<>();
        List<String> order = new ArrayList<>();

        for (String vertex : graph.keySet()) {
            if (!visited.contains(vertex)) {
                dfs1(vertex, graph, visited, order);
            }
        }

        Collections.reverse(order);

        visited.clear();

        for (String vertex : order) {
            if (!visited.contains(vertex)) {
                Set<String> component = new TreeSet<>();
                dfs2(vertex, reverseGraph, visited, component);
                System.out.println(String.join("", component));
            }
        }
    }

    private static void dfs1(String current, Map<String, Set<String>> graph,
                             Set<String> visited, List<String> order) {
        visited.add(current);
        for (String neighbor : graph.get(current)) {
            if (!visited.contains(neighbor)) {
                dfs1(neighbor, graph, visited, order);
            }
        }
        order.add(current);
    }

    private static void dfs2(String current, Map<String, Set<String>> reverseGraph,
                             Set<String> visited, Set<String> component) {
        visited.add(current);
        component.add(current);
        for (String neighbor : reverseGraph.get(current)) {
            if (!visited.contains(neighbor)) {
                dfs2(neighbor, reverseGraph, visited, component);
            }
        }
    }
}