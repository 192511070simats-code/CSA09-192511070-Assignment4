
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Converter extends JFrame {

    private final JTextField amount = new JTextField(10);

    private final JComboBox<String> target =
            new JComboBox<>(new String[]{"USD", "EUR"});

    private final JLabel result = new JLabel("");

    private static double rate(String currency) {
        return currency.equals("USD") ? 0.012 : 0.011;
    }

    public Converter() {
        super("Currency Converter");

        setLayout(new FlowLayout());

        JButton convertButton = new JButton("Convert");

        add(new JLabel("INR"));
        add(amount);
        add(target);
        add(convertButton);
        add(result);

        // TODO1: One lambda listener handles both button and Enter key.
        ActionListener convertAction = e -> convert();

        convertButton.addActionListener(convertAction);
        amount.addActionListener(convertAction);

        // Handle window closing using WindowAdapter.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void convert() {

        // TODO2: Parse amount and show result with two decimals.
        try {
            double inr = Double.parseDouble(amount.getText().trim());

            if (!Double.isFinite(inr) || inr < 0) {
                throw new NumberFormatException();
            }

            String currency = (String) target.getSelectedItem();

            double converted = inr * rate(currency);

            String output = String.format(
                    Locale.US, "%.2f %s", converted, currency
            );

            result.setText(output);

            JOptionPane.showMessageDialog(
                    this,
                    "Converted amount: " + output,
                    "Conversion Result",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            result.setText("Invalid amount");

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid amount. Please enter a valid number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new Converter().setVisible(true)
        );
    }
}
