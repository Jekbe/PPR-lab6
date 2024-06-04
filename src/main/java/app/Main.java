package app;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(5);

        // Dodaj krawędzie (przykładowe)
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);

        // Uruchom algorytm Echo z węzłem inicjującym (np. 0)
        graph.runEcho(0);

        // Wydrukuj drzewo rozpinające
        graph.printTree();
    }
}

