package app;

import lombok.Getter;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Node {
    @Getter
    private static List<Node> allNodes = new ArrayList<>();
    @Getter
    private int id;
    private final List<Edge> edges;
    private final BlockingQueue<Message> messageQueue;
    private boolean initiator;
    private boolean receivedEcho;
    @Getter
    private int parent;
    private boolean work = true;

    public Node(int id) {
        this.id = id;
        this.edges = new ArrayList<>();
        Set<Integer> component = new HashSet<>();
        this.messageQueue = new LinkedBlockingQueue<>();
        this.initiator = false;
        this.receivedEcho = false;
        this.parent = -1;
        allNodes.add(this);
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public void setInitiator() {
        this.initiator = true;
    }

    public void sendMessage(Message message) {
        Network.sendMessage(message);
    }

    public void receiveMessage(Message message) {
        messageQueue.add(message);
    }

    public Message getMessage() throws InterruptedException {
        return messageQueue.take();
    }

    public void processMessages() throws InterruptedException {
        while (work) {
            Message message = getMessage();
            if (message.content.equals("ECHO")) {
                if (parent == -1) {
                    parent = message.source;
                    edges.stream().filter(edge -> edge.destination != parent).map(edge -> new Message(id, edge.destination, "ECHO")).forEach(this::sendMessage);
                }
                receivedEcho = true;
            }
        }
    }

    public boolean hasReceivedEcho() {
        return receivedEcho;
    }

    public void changeWork(){
        work = !work;
    }
}

