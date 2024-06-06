package app;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Graph graph = new Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);

        graph.runEcho(4);

        Thread.sleep(5000);
        graph.printTree();
    }
}

