package app;

public class Message {
    public int source;
    public int destination;
    public String content;

    public Message(int source, int destination, String content) {
        this.source = source;
        this.destination = destination;
        this.content = content;
    }
}

