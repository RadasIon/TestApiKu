package api.kucoinApi;

public class TicketShort {
    private String name;
    private Float changeRate;

    public TicketShort(String name, Float changeRate) {
        this.name = name;
        this.changeRate = changeRate;
    }

    public String getName() {
        return name;
    }

    public Float getChangeRate() {
        return changeRate;
    }
}
