package by.it.group551002.tereshchenko.lesson13;

import java.util.*;

public class GraphA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return;

        Map<String, Set<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();

        String[] edges = input.split(",");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            String from = parts[0].trim();
            String to = parts[1].trim();

            graph.putIfAbsent(from, new HashSet<>());
            graph.putIfAbsent(to, new HashSet<>());
            inDegree.putIfAbsent(from, 0);
            inDegree.putIfAbsent(to, 0);

            if (graph.get(from).add(to)) {
                inDegree.put(to, inDegree.get(to) + 1);
            }
        }

        PriorityQueue<String> queue = new PriorityQueue<>();
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);

            for (String neighbor : graph.get(current)) {
                int newDegree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, newDegree);
                if (newDegree == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        System.out.println(String.join(" ", result));
    }
}
