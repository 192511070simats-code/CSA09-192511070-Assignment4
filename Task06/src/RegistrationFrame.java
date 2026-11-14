
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegistrationFrame extends JFrame {

    private final RegistrationDAO dao = new RegistrationDAO();
    private final RegistrationService service = new RegistrationService();
    private final ExportService exporter = new ExportService();

    private final JTextField studentIdField = new JTextField(20);
    private final JTextField studentNameField = new JTextField(20);

    private final JComboBox<String> departmentBox =
            new JComboBox<>(new String[]{
                "Computer Science",
                "Information Technology",
                "Electronics",
                "Electrical Engineering",
                "Mechanical Engineering",
                "Civil Engineering"
            });

    private final JComboBox<Event> eventBox = new JComboBox<>();
    private final JComboBox<Object> filterBox = new JComboBox<>();

    private final JLabel registrationStatus =
            new JLabel("Enter your details to register.");

    private final JLabel dashboardStatus =
            new JLabel("Ready.");

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                new String[]{
                    "Registration ID",
                    "Student ID",
                    "Student Name",
                    "Department",
                    "Event",
                    "Registration Date"
                }, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable registrationTable = new JTable(tableModel);

    private List<Registration> currentRegistrations =
            new ArrayList<>();

    public RegistrationFrame() {

        setTitle("Campus Event Registration System");
        setSize(900, 600);
        setMinimumSize(new Dimension(650, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setJMenuBar(createMenuBar());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Student Registration", createRegistrationPanel());
        tabs.addTab("Committee Dashboard", createDashboardPanel());

        add(tabs, BorderLayout.CENTER);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    RegistrationFrame.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        loadEvents();
        loadRegistrations(null);
    }

    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem exportItem = new JMenuItem("Export CSV");
        JMenuItem refreshItem = new JMenuItem("Refresh Dashboard");
        JMenuItem exitItem = new JMenuItem("Exit");

        exportItem.addActionListener(e -> exportRegistrations());
        refreshItem.addActionListener(e -> loadRegistrations(null));
        exitItem.addActionListener(e -> dispatchEvent(
            new java.awt.event.WindowEvent(
                this, java.awt.event.WindowEvent.WINDOW_CLOSING
            )
        ));

        fileMenu.add(exportItem);
        fileMenu.add(refreshItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

    private JPanel createRegistrationPanel() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel heading = new JLabel(
            "Campus Event Registration",
            SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 23));

        JPanel form = new JPanel(new GridLayout(5, 2, 12, 15));
        form.setBorder(BorderFactory.createTitledBorder(
            "Student Details"
        ));

        form.add(new JLabel("Student ID:"));
        form.add(studentIdField);

        form.add(new JLabel("Student Name:"));
        form.add(studentNameField);

        form.add(new JLabel("Department:"));
        form.add(departmentBox);

        form.add(new JLabel("Select Event:"));
        form.add(eventBox);

        JButton registerButton = new JButton("Register");
        JButton clearButton = new JButton("Clear");

        form.add(registerButton);
        form.add(clearButton);

        JPanel center = new JPanel(new BorderLayout(10, 15));
        center.add(form, BorderLayout.CENTER);
        center.add(registrationStatus, BorderLayout.SOUTH);

        panel.add(heading, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);

        registerButton.addActionListener(e -> registerStudent());

        clearButton.addActionListener(e -> {
            studentIdField.setText("");
            studentNameField.setText("");
            departmentBox.setSelectedIndex(0);
            eventBox.setSelectedIndex(-1);
            registrationStatus.setText("Form cleared.");
        });

        return panel;
    }

    private JPanel createDashboardPanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel(
            "Committee Registration Dashboard",
            SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 21));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));

        filterBox.addItem("All Events");

        JButton filterButton = new JButton("Filter");
        JButton refreshButton = new JButton("Refresh");
        JButton exportButton = new JButton("Export CSV");

        controls.add(new JLabel("Event:"));
        controls.add(filterBox);
        controls.add(filterButton);
        controls.add(refreshButton);
        controls.add(exportButton);

        registrationTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(registrationTable);

        panel.add(heading, BorderLayout.NORTH);
        panel.add(controls, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        filterButton.addActionListener(e -> {
            Object selected = filterBox.getSelectedItem();

            if (selected instanceof Event) {
                Event event = (Event) selected;
                loadRegistrations(event.getEventId());
            } else {
                loadRegistrations(null);
            }
        });

        refreshButton.addActionListener(e -> loadRegistrations(null));
        exportButton.addActionListener(e -> exportRegistrations());

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.add(controls, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(dashboardStatus, BorderLayout.SOUTH);

        panel.remove(controls);
        panel.remove(scrollPane);
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    private void loadEvents() {

        new SwingWorker<List<Event>, Void>() {

            @Override
            protected List<Event> doInBackground() throws Exception {
                return dao.getEvents();
            }

            @Override
            protected void done() {
                try {
                    List<Event> events = get();

                    eventBox.removeAllItems();
                    filterBox.removeAllItems();
                    filterBox.addItem("All Events");

                    for (Event event : events) {
                        eventBox.addItem(event);
                        filterBox.addItem(event);
                    }

                    if (!events.isEmpty()) {
                        eventBox.setSelectedIndex(0);
                    }

                } catch (Exception e) {
                    showError("Could not load events: " + e.getMessage());
                }
            }
        }.execute();
    }

    private void registerStudent() {

        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String department = (String) departmentBox.getSelectedItem();
        Event event = (Event) eventBox.getSelectedItem();

        registrationStatus.setText("Registering...");
        
        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                service.registerStudent(
                    studentId, studentName, department, event
                );
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();

                    registrationStatus.setText(
                        "Registration successful!"
                    );

                    JOptionPane.showMessageDialog(
                        RegistrationFrame.this,
                        "Student registered successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    studentIdField.setText("");
                    studentNameField.setText("");

                    loadRegistrations(null);

                } catch (Exception e) {
                    Throwable cause = e.getCause();

                    String message = cause != null
                            ? cause.getMessage()
                            : e.getMessage();

                    registrationStatus.setText("Registration failed.");

                    showError(message);
                }
            }
        }.execute();
    }

    private void loadRegistrations(Integer eventId) {

        dashboardStatus.setText("Loading registrations...");

        new SwingWorker<List<Registration>, Void>() {

            @Override
            protected List<Registration> doInBackground()
                    throws Exception {
                return dao.getRegistrations(eventId);
            }

            @Override
            protected void done() {
                try {
                    currentRegistrations = get();
                    tableModel.setRowCount(0);

                    for (Registration r : currentRegistrations) {
                        tableModel.addRow(new Object[]{
                            r.getRegistrationId(),
                            r.getStudentId(),
                            r.getStudentName(),
                            r.getDepartment(),
                            r.getEventName(),
                            r.getRegistrationDate()
                        });
                    }

                    dashboardStatus.setText(
                        "Total registrations: " +
                        currentRegistrations.size()
                    );

                } catch (Exception e) {
                    dashboardStatus.setText("Unable to load data.");
                    showError("Dashboard error: " + e.getMessage());
                }
            }
        }.execute();
    }

    private void exportRegistrations() {

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(
            new File("campus_event_registrations.csv")
        );

        int result = chooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getAbsolutePath() + ".csv");
            }

            final File selectedFile = file;

            try {
                exporter.exportToCSV(
                    currentRegistrations, selectedFile
                );

                JOptionPane.showMessageDialog(
                    this,
                    "CSV exported successfully:\n" +
                    selectedFile.getAbsolutePath(),
                    "Export Complete",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception e) {
                showError("Export failed: " + e.getMessage());
            }
        }
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
            this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            RegistrationFrame frame = new RegistrationFrame();
            frame.setVisible(true);
        });
    }
}
