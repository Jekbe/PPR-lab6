package app;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Graph {
    private final List<Node> nodes;
    private final ExecutorService executorService;

    public Graph(int numberOfNodes) {
        nodes = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(numberOfNodes);
        IntStream.range(0, numberOfNodes).mapToObj(Node::new).forEach(node -> {
            nodes.add(node);
            Network.registerReceiver(node);
        });
    }

    public void addEdge(int source, int destination) {
        nodes.get(source).addEdge(new Edge(source, destination));
        nodes.get(destination).addEdge(new Edge(destination, source));
    }

    public void runEcho(int initiatorId) {
        Node initiator = nodes.get(initiatorId);
        initiator.setInitiator();
        nodes.stream().<Runnable>map(node -> () -> {
            try {
                node.processMessages();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).forEach(executorService::submit);
        initiator.sendMessage(new Message(initiatorId, initiatorId, "ECHO"));
        executorService.shutdown();
    }

    public void printTree() {
        nodes.stream().map(node -> "Node " + node.getId() + ": Parent " + node.getParent()).forEach(System.out::println);
    }
}

