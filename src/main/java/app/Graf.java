package app;

import lombok.Getter;

import java.util.List;

public class Graf {
    private final List<Wezel> wezly;
    @Getter
    private List<Polaczenie> poloczenia;

    public Graf(List<Wezel> wezly, List<Polaczenie> poloczenia){
        this.wezly = wezly;
        this.poloczenia = poloczenia;
    }

    @lombok.SneakyThrows
    public void start(String id) {
        wezly.stream().filter(wezel -> wezel.getId().equals(id)).findFirst().ifPresent(Wezel::startEcho);
    }
}
