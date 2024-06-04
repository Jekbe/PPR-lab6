package app;

import jakarta.jms.JMSException;
import java.util.List;

public class Graf {
    private List<Wezel> wezly;
    private List<Polaczenie> poloczenia;

    public Graf(List<Wezel> wezly, List<Polaczenie> poloczenia){
        this.wezly = wezly;
        this.poloczenia = poloczenia;
    }

    @lombok.SneakyThrows
    public void start(String id) throws JMSException {
        wezly.stream().filter(wezel -> wezel.getId().equals(id)).findFirst().ifPresent(Wezel::startEcho);
    }
}
