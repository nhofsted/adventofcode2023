import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day25 extends Puzzle {

    private HashMap<String, String[]> parsedConnections;
    private String[] randomNodes;
    private int randomNodeIndex = 0;
    private HashMap<String, String[]> connections;

    public static void main(String[] args) throws Exception {
        new Day25().run();
    }

    @Override
    protected long getSampleSolution() {
        return 54;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        parsedConnections = parse(in);
        randomNodes = parsedConnections.keySet().toArray(String[]::new);
        return findSolution(100);
    }

    private int findSolution(int samples) {
        System.out.println("Trying with " + samples + " samples");
        connections = new HashMap<>(parsedConnections);

        HashMap<String, AtomicInteger> heatMap = new HashMap<>();
        for (int i = 0; i < samples; ++i) {
            String[]  path = findShortestPath(selectRandomNode(), selectRandomNode());
            for(int j = 0; j < path.length-1; ++j) {
                String node1 = path[j];
                String node2 = path[j+1];
                String link = (node1.compareTo(node2) <= 0) ? node1 + "/" + node2 : node2 + "/" + node1;
                heatMap.computeIfAbsent(link, (s) -> new AtomicInteger()).incrementAndGet();
            }
        }

        removeTop3(heatMap);

        int sizeA = countAndRemove(selectRandomNode());
        if (connections.isEmpty()) return findSolution(samples * 2);
        int sizeB = countAndRemove(selectRandomNode());
        if (connections.isEmpty()) return sizeA * sizeB;
        return findSolution(samples * 2);
    }

    private HashMap<String, String[]> parse(BufferedReader in) {
        HashMap<String, String[]> retVal = new HashMap<>();
        in.lines().forEach(line -> {
            String[] e = line.split(": ");
            String[] c = e[1].split(" ");
            Arrays.stream(c).forEach(t -> {
                addLink(retVal, e[0], t);
                addLink(retVal, t, e[0]);
            });
        });
        return retVal;
    }

    private String selectRandomNode() {
        if(randomNodes.length != connections.size()) {
            randomNodes = connections.keySet().toArray(String[]::new);
            randomNodeIndex = 0;
        }
        if (randomNodeIndex == 0) {
            Collections.shuffle(Arrays.asList(randomNodes));
        }
        String retVal = randomNodes[randomNodeIndex++];
        randomNodeIndex %= randomNodes.length;
        return retVal;
    }

    private String[] findShortestPath(String n1, String n2) {
        LinkedTransferQueue<ArrayList<String>> paths = new LinkedTransferQueue<>();
        paths.add(new ArrayList<>(List.of(n1)));
        while(!paths.isEmpty()) {
            ArrayList<String> path = paths.poll();
            String lastNode = path.getLast();
            if(lastNode.equals(n2)) return path.toArray(String[]::new);
            Arrays.stream(parsedConnections.get(lastNode))
                    .filter(s -> !path.contains(s))
                    .forEach(node -> {
                        ArrayList<String> nextPath = new ArrayList<>(path);
                        nextPath.add(node);
                        paths.add(nextPath);
                    });
        }
        return new String[0];
    }

    private void removeTop3(HashMap<String, AtomicInteger> heatMap) {
        PriorityQueue<Map.Entry<String, AtomicInteger>> maxHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o.getValue().get()));
        heatMap.entrySet().forEach(entry -> {
            maxHeap.add(entry);
            if (maxHeap.size() > 3) maxHeap.poll();
        });

        maxHeap.stream().map(Map.Entry::getKey).forEach(link -> {
            String[] nodes = link.split("/");
            deleteLink(connections, nodes[0], nodes[1]);
            deleteLink(connections, nodes[1], nodes[0]);
        });
    }

    private void addLink(HashMap<String, String[]> links, String from, String to) {
        links.computeIfPresent(from, (n, c) -> Stream.concat(Arrays.stream(c), Stream.of(to)).toArray(String[]::new));
        links.putIfAbsent(from, new String[]{to});
    }

    private void deleteLink(HashMap<String, String[]> links, String from, String to) {
        links.computeIfPresent(from, (n, c) -> Arrays.stream(c).filter(e -> !to.equals(e)).toArray(String[]::new));
    }

    private int countAndRemove(String node) {
        int removed = 0;
        Queue<String> toRemove = new LinkedTransferQueue<>();
        toRemove.add(node);
        while (!toRemove.isEmpty()) {
            String[] c = connections.remove(toRemove.remove());
            if (c != null) {
                removed++;
                toRemove.addAll(Arrays.asList(c));
            }
        }
        return removed;
    }
}