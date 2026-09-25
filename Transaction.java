import java.time.LocalDateTime;

public class Transaction {

    private String type;
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(String type, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be greater than 0."
            );
        }

        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {

        return "Date: " + dateTime
                + " | Type: " + type
                + " | Amount: ₹" + amount;
    }
}