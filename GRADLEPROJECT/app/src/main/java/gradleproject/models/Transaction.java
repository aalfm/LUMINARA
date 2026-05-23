package gradleproject.models;

import java.sql.Timestamp;

public class Transaction extends BaseEntity {
    private double amount;
    private Timestamp transactionDate;
    private String status; // Success, Failed, Refunded

    public Transaction() {}

    public Transaction(int id, double amount, Timestamp transactionDate, String status) {
        super(id);
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.status = status;
    }

    // Getters and Setters
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Timestamp getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Timestamp transactionDate) { this.transactionDate = transactionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}