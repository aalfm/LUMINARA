package gradleproject.models;

import java.util.Date;

public class Transaction {
    private int id;
    private String eventName;
    private String participantName;
    private double totalAmount;
    private Date transactionDate;

    public Transaction (int id, String eventName, String participantName, double totalAmount, Date transactionDate) {
        this.id = id;
        this.eventName = eventName;
        this.participantName = participantName;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
    }

    public int getId() { return id; }
    public String getEventName() { return eventName; }
    public String getParticipantName() { return participantName; }
    public double getTotalAmount() { return totalAmount; }
    public Date getTransactionDate() { return transactionDate; }
}