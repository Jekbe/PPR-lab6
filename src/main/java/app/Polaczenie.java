package app;

public record Polaczenie(Wezel wezel1, Wezel wezel2) {
    public boolean czyPoloczone(Wezel wezel, Wezel wezel2) {
        return (this.wezel1 == wezel && this.wezel2 == wezel2) || (this.wezel1 == wezel2 && this.wezel2 == wezel1);
    }
}
