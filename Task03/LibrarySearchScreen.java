
import javax.swing.*;
import java.awt.*;

public class LibrarySearchScreen extends JFrame {

    private JTextField searchField;
    private JComboBox<String> categoryChoice;
    private JTextArea resultsArea;
    private JLabel statusLabel;

    public LibrarySearchScreen() {
        setTitle("Campus Library Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Main window uses BorderLayout
        setLayout(new BorderLayout(10, 10));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(
                "Campus Library Search", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // Search row uses FlowLayout
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));

        searchField = new JTextField(25);

        String[] categories = {
                "All Categories", "Books", "Authors", "Journals"
        };
        categoryChoice = new JComboBox<>(categories);

        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(categoryChoice);
        searchPanel.add(searchButton);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        // Results area expands in the centre
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Arial", Font.PLAIN, 15));
        resultsArea.setText("Search results will appear here.");

        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));

        // Bottom section
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Buttons use FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));

        JButton prevButton = new JButton("Prev");
        JButton nextButton = new JButton("Next");
        JButton reserveButton = new JButton("Reserve");

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(reserveButton);

        statusLabel = new JLabel("Status: Ready");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 8, 10));

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        // Add the main regions
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Basic button actions
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            String category = (String) categoryChoice.getSelectedItem();

            if (query.isEmpty()) {
                statusLabel.setText("Status: Please enter a search term.");
            } else {
                resultsArea.setText(
                        "Search term: " + query +
                        "\nCategory: " + category +
                        "\n\nNo actual library database is connected.");
                statusLabel.setText("Status: Search completed.");
            }
        });

        prevButton.addActionListener(e ->
                statusLabel.setText("Status: Previous page selected."));

        nextButton.addActionListener(e ->
                statusLabel.setText("Status: Next page selected."));

        reserveButton.addActionListener(e ->
                statusLabel.setText("Status: Reserve button selected."));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibrarySearchScreen screen = new LibrarySearchScreen();
            screen.setVisible(true);
        });
    }
}
