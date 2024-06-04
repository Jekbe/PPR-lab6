package app;

import jakarta.jms.JMSException;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JMSException {
        List<Wezel> wezly = new ArrayList<>();
        List<Polaczenie> polaczenia = new ArrayList<>();

        Wezel wezel0 = new Wezel("0");
        Wezel wezel1 = new Wezel("1");
        Wezel wezel2 = new Wezel("2");
        Wezel wezel3 = new Wezel("3");
        Wezel wezel4 = new Wezel("4");
        Wezel wezel5 = new Wezel("5");

        Polaczenie polaczenie01 = new Polaczenie(wezel0, wezel1);
        Polaczenie polaczenie02 = new Polaczenie(wezel0, wezel2);
        Polaczenie polaczenie13 = new Polaczenie(wezel1, wezel3);
        Polaczenie polaczenie23 = new Polaczenie(wezel2, wezel3);
        Polaczenie polaczenie24 = new Polaczenie(wezel2, wezel4);
        Polaczenie polaczenie34 = new Polaczenie(wezel3, wezel4);
        Polaczenie polaczenie45 = new Polaczenie(wezel4, wezel5);

        wezly.add(wezel0);
        wezly.add(wezel1);
        wezly.add(wezel2);
        wezly.add(wezel3);
        wezly.add(wezel4);
        wezly.add(wezel5);

        polaczenia.add(polaczenie01);
        polaczenia.add(polaczenie02);
        polaczenia.add(polaczenie13);
        polaczenia.add(polaczenie23);
        polaczenia.add(polaczenie24);
        polaczenia.add(polaczenie34);
        polaczenia.add(polaczenie45);

        Graf graf = new Graf(wezly, polaczenia);
        Echo.run(graf, "0");
    }
}
