
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;

public class ConverterAnonymous extends JFrame {

    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target =
            new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel("");

    public ConverterAnonymous() {
        super("Converter - Anonymous Inner Class");
        setLayout(new FlowLayout());

        JButton button = new JButton("Convert");
        add(new JLabel("INR"));
        add(amount);
        add(target);
        add(button);
        add(result);

        ActionListener handler = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convert();
            }
        };

        button.addActionListener(handler);
        amount.addActionListener(handler);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        try {
            double inr = Double.parseDouble(amount.getText().trim());
            if (!Double.isFinite(inr) || inr < 0)
                throw new NumberFormatException();

            String currency = (String) target.getSelectedItem();
            double rate = currency.equals("USD") ? 0.012 : 0.011;
            String output = String.format(
                    Locale.US, "%.2f %s", inr * rate, currency);

            result.setText(output);
            JOptionPane.showMessageDialog(this, output,
                    "Conversion Result", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            result.setText("Invalid amount");
            JOptionPane.showMessageDialog(this, "Invalid amount",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new ConverterAnonymous().setVisible(true));
    }
}
