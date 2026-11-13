import javax.swing.*;
import java.awt.*;

public class FeeWindow extends JFrame {

    private JTextField regNoField;
    private JTextField amountField;
    private JLabel statusLabel;

    private String receipt = "";
    private final FeeService feeService = new FeeService();

    
public FeeWindow() {
    setTitle("Student Fee Payment");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel regLabel = new JLabel("Registration Number:");
    regNoField = new JTextField(18);
    regNoField.setPreferredSize(new Dimension(180, 28));

    JLabel amountLabel = new JLabel("Amount:");
    amountField = new JTextField(18);
    amountField.setPreferredSize(new Dimension(180, 28));

    JButton payButton = new JButton("Pay");
    JButton clearButton = new JButton("Clear");
    JButton receiptButton = new JButton("Receipt");

    statusLabel = new JLabel("Enter payment details.");

    JPanel formPanel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 8, 10, 8);

    c.gridx = 0;
    c.gridy = 0;
    c.anchor = GridBagConstraints.LINE_END;
    formPanel.add(regLabel, c);

    c.gridx = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    formPanel.add(regNoField, c);

    c.gridx = 0;
    c.gridy = 1;
    c.fill = GridBagConstraints.NONE;
    c.weightx = 0;
    formPanel.add(amountLabel, c);

    c.gridx = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    formPanel.add(amountField, c);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(payButton);
    buttonPanel.add(clearButton);
    buttonPanel.add(receiptButton);

    setLayout(new BorderLayout(8, 8));
    add(statusLabel, BorderLayout.NORTH);
    add(formPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    payButton.addActionListener(e -> makePayment());

    clearButton.addActionListener(e -> {
        regNoField.setText("");
        amountField.setText("");
        statusLabel.setText("Fields cleared.");
        receipt = "";
    });

    receiptButton.addActionListener(e -> {
        if (receipt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No payment receipt is available.");
        } else {
            JOptionPane.showMessageDialog(this, receipt,
                    "Payment Receipt",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    });

    pack();
    setLocationRelativeTo(null);
}


    private void makePayment() {
        String regNo = regNoField.getText().trim();
        String amountText = amountField.getText().trim();

        double amount;

        try {
            amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid amount greater than zero.");
            return;
        }

        // Run payment processing in the background.
        JButton sourceButton = null;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        statusLabel.setText("Processing payment...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                return feeService.processPayment(regNo, amount);
            }

            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());

                try {
                    receipt = get();
                    statusLabel.setText("Payment successful.");
                    JOptionPane.showMessageDialog(FeeWindow.this, receipt);
                } catch (Exception ex) {
                    statusLabel.setText("Payment failed.");
                    JOptionPane.showMessageDialog(FeeWindow.this,
                            "Payment could not be completed: "
                                    + ex.getMessage());
                }
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FeeWindow().setVisible(true));
    }
}