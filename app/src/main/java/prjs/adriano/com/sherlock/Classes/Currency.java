package prjs.adriano.com.sherlock.Classes;

import android.database.Cursor;

public class Currency {

    private int id;
    private String code;
    private String name;
    private String symbol;
    private String value;
    private String country;

    public Currency(int id, String code, String name, String symbol, String value, String country) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        this.value = value;
        this.country = country;
    }

    public Currency(){};
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
