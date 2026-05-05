class TransactionLogger {
    private static TransactionLogger instance;

    private TransactionLogger() {}

    public static TransactionLogger getInstance() {
        if (instance == null) {
            synchronized (TransactionLogger.class) {
                if (instance == null)
                    instance = new TransactionLogger();
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("The message is: " + message);
    }
}

class TransactionBuilderPattern {
    private int transactionId; // mandatory
    private int customerId;    // mandatory
    private double amount;     // mandatory
    private String currency;   // optional
    private String notes;      // optional
    private String timestamp;  // optional

    // Private constructor — only the inner Builder can instantiate this class
    private TransactionBuilderPattern(int transactionId, int customerId, double amount, String currency, String notes, String timestamp) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
        this.notes = notes;
        this.timestamp = timestamp;
    }

    public void printDetails() {
        System.out.println("transactionId: " + transactionId);
        System.out.println("customerId: " + customerId);
        System.out.println("amount: " + amount);
        System.out.println("currency: " + currency);
        System.out.println("notes: " + notes);
        System.out.println("timestamp: " + timestamp);
    }

    
    public int getTransactionId() { return transactionId; }
    public int getCustomerId() { return customerId; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getNotes() { return notes; }
    public String getTimestamp() { return timestamp; }

    // Static nested Builder class — collects parameters and constructs the outer object
    static class Builder {
        private int transactionId; // mandatory
        private int customerId;    // mandatory
        private double amount;     // mandatory
        private String currency;   // optional
        private String notes;      // optional
        private String timestamp;  // optional

        // Constructor accepts only mandatory fields
        Builder(int transactionId, int customerId, double amount) {
            this.transactionId = transactionId;
            this.customerId = customerId;
            this.amount = amount;
        }

        // Each setter returns 'this' to enable method chaining
        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder setTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        // Constructs and returns the final TransactionBuilderPattern object
        public TransactionBuilderPattern build() {
            return new TransactionBuilderPattern(transactionId, customerId, amount, currency, notes, timestamp);
        }
    }
}

interface PaymentProcessor {
    void processPayment(double amount);
}

class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}

class UPIProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing UPI payment of $" + amount);
    }
}

class NetBankingProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing net banking payment of $" + amount);
    }
}

class PaymentProcessorFactory {
    public static PaymentProcessor getProcessor(String paymentType) {
        if (paymentType.equals("credit")) {
            return new CreditCardProcessor();
        } else if (paymentType.equals("upi")) {
            return new UPIProcessor();
        } else if (paymentType.equals("netbanking")) {
            return new NetBankingProcessor();
        }
        return null;
    }
}

class TransactionProcessingSystem {
    public static void main(String[] args) {
        PaymentProcessor processor = PaymentProcessorFactory.getProcessor("upi");
        TransactionBuilderPattern txn = new TransactionBuilderPattern.Builder(1, 1, 500.0)
                       .setCurrency("INR")
                       .build();
        processor.processPayment(txn.getAmount());
        TransactionLogger.getInstance().log("Transaction Success");

    }
}

//javac TransactionProcessingSystem.java && java TransactionProcessingSystem
