package api.model;

public class Card {

    private String id;
    private String brand;
    private String last4;
    private int exp_month;
    private int exp_year;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getLast4() { return last4; }
    public void setLast4(String last4) { this.last4 = last4; }

    public int getExp_month() { return exp_month; }
    public void setExp_month(int exp_month) { this.exp_month = exp_month; }

    public int getExp_year() { return exp_year; }
    public void setExp_year(int exp_year) { this.exp_year = exp_year; }
}