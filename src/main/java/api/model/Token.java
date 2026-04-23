package api.model;

public class Token {

    private String id;
    private String object;
    private Card card;
    private String email;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}