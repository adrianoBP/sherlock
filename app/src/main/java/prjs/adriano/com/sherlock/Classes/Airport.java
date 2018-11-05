package prjs.adriano.com.sherlock.Classes;

public class Airport {
    private String iata;

    public Airport(String iata, String name) {
        this.iata = iata;
        this.name = name;
    }

    public String getIata() {

        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Airport() {
    }
}
