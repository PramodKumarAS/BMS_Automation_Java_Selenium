package api.model;

public class PaymentDetails {

    private Token token;
    private int amount;

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}