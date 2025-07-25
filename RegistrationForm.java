import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame {
    private JTextField txtID, txtName, txtAddress, txtContact;
    private JRadioButton rbtnMale, rbtnFemale;
    private JButton btnRegister, btnExit;
    private JTable table;
    private DefaultTableModel tableModel;
    private Connection conn;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize DB connection
        connectDatabase();
        createTableIfNotExists();

        // Left panel for form inputs
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        leftPanel.add(new JLabel("ID:"), gbc);
        txtID = new JTextField(15);
        gbc.gridx = 1;
        leftPanel.add(txtID, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        leftPanel.add(new JLabel("Name:"), gbc);
        txtName = new JTextField(15);
        gbc.gridx = 1;
        leftPanel.add(txtName, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy = 2;
        leftPanel.add(new JLabel("Gender:"), gbc);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rbtnMale = new JRadioButton("Male");
        rbtnFemale = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbtnMale);
        genderGroup.add(rbtnFemale);
        genderPanel.add(rbtnMale);
        genderPanel.add(rbtnFemale);
        gbc.gridx = 1;
        leftPanel.add(genderPanel, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy = 3;
        leftPanel.add(new JLabel("Address:"), gbc);
        txtAddress = new JTextField(15);
        gbc.gridx = 1;
        leftPanel.add(txtAddress, gbc);

        // Contact
        gbc.gridx = 0; gbc.gridy = 4;
        leftPanel.add(new JLabel("Contact:"), gbc);
        txtContact = new JTextField(15);
        gbc.gridx = 1;
        leftPanel.add(txtContact, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnRegister = new JButton("Register");
        btnExit = new JButton("Exit");
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnExit);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        leftPanel.add(buttonPanel, gbc);

        add(leftPanel, BorderLayout.WEST);

        // Right panel for table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Gender", "Address", "Contact"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load existing data
        loadData();

        // Button actions
        btnRegister.addActionListener(e -> registerUser());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void connectDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:registration.db");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT," +
                "gender TEXT," +
                "address TEXT," +
                "contact TEXT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to create table: " + e.getMessage());
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("contact")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load data: " + e.getMessage());
        }
    }

    private void registerUser() {
        String id = txtID.getText().trim();
        String name = txtName.getText().trim();
        String gender = rbtnMale.isSelected() ? "Male" : rbtnFemale.isSelected() ? "Female" : "";
        String address = txtAddress.getText().trim();
        String contact = txtContact.getText().trim();

        if (id.isEmpty() || name.isEmpty() || gender.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        String sql = "INSERT INTO users (id, name, gender, address, contact) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, gender);
            pstmt.setString(4, address);
            pstmt.setString(5, contact);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "User registered successfully.");
            loadData();
            clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to register user: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtID.setText("");
        txtName.setText("");
        rbtnMale.setSelected(false);
        rbtnFemale.setSelected(false);
        txtAddress.setText("");
        txtContact.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationForm form = new RegistrationForm();
            form.setVisible(true);
        });
    }
}
