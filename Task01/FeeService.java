public class FeeService {

    public String processPayment(String regNo, double amount) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number is required.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Simulate payment processing.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Payment was interrupted.");
        }

        return "Payment successful for " + regNo
                + "\nAmount paid: Rs. " + String.format("%.2f", amount);
    }
}