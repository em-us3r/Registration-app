import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class RegistrationFormWithDOB extends JFrame {
    private JTextField txtName, txtMobile;
    private JRadioButton rbtnMale, rbtnFemale;
    private JComboBox<String> cbDay, cbMonth, cbYear;
    private JTextArea txtAddress;
    private JCheckBox chkTerms;
    private JButton btnSubmit, btnReset;
    private JTextArea displayArea;
    private DefaultTableModel tableModel;
    private JTable table;

    public RegistrationFormWithDOB() {
        setTitle("Registration Form");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Left panel for form inputs
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        leftPanel.add(new JLabel("Name:"), gbc);
        txtName = new JTextField(15);
        gbc.gridx = 1;
        leftPanel.add(txtName, gbc);

        // Mobile
        gbc.gridx = 0; gbc.gridy = 1;
        leftPanel.add(new JLabel("Mobile:"), gbc);
        txtMobile = new JTextField(15);
        gbc.gridx = 1;
        leftPanel.add(txtMobile, gbc);

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

        // DOB
        gbc.gridx = 0; gbc.gridy = 3;
        leftPanel.add(new JLabel("DOB:"), gbc);
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        cbDay = new JComboBox<>();
        for (int i = 1; i <= 31; i++) cbDay.addItem(String.valueOf(i));
        cbMonth = new JComboBox<>(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        cbYear = new JComboBox<>();
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) cbYear.addItem(String.valueOf(i));
        dobPanel.add(cbDay);
        dobPanel.add(cbMonth);
        dobPanel.add(cbYear);
        gbc.gridx = 1;
        leftPanel.add(dobPanel, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy = 4;
        leftPanel.add(new JLabel("Address:"), gbc);
        txtAddress = new JTextArea(3, 15);
        JScrollPane addressScroll = new JScrollPane(txtAddress);
        gbc.gridx = 1;
        leftPanel.add(addressScroll, gbc);

        // Terms checkbox
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        chkTerms = new JCheckBox("Accept Terms And Conditions.");
        leftPanel.add(chkTerms, gbc);

        // Buttons
        gbc.gridy = 6; gbc.gridwidth = 1;
        btnSubmit = new JButton("Submit");
        btnReset = new JButton("Reset");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnReset);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        leftPanel.add(buttonPanel, gbc);

        add(leftPanel, BorderLayout.WEST);

        // Right panel for display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane displayScroll = new JScrollPane(displayArea);
        add(displayScroll, BorderLayout.CENTER);

        // Button actions
        btnSubmit.addActionListener(e -> submitForm());
        btnReset.addActionListener(e -> resetForm());

        // Initialize DB and create table
        try {
            DatabaseHelper.createUsersWithDOBTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }

        // Load existing data
        loadData();
    }

    private void submitForm() {
        String name = txtName.getText().trim();
        String mobile = txtMobile.getText().trim();
        String gender = rbtnMale.isSelected() ? "Male" : rbtnFemale.isSelected() ? "Female" : "";
        String dob = cbDay.getSelectedItem() + " " + cbMonth.getSelectedItem() + " " + cbYear.getSelectedItem();
        String address = txtAddress.getText().trim();
        boolean termsAccepted = chkTerms.isSelected();

        if (name.isEmpty() || mobile.isEmpty() || gender.isEmpty() || address.isEmpty() || !termsAccepted) {
            JOptionPane.showMessageDialog(this, "Please fill all fields and accept terms.");
            return;
        }

        // Insert into database
        String sql = "INSERT INTO users_dob (name, mobile, gender, dob, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, mobile);
            pstmt.setString(3, gender);
            pstmt.setString(4, dob);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration successful.");
            loadData();
            resetForm();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void resetForm() {
        txtName.setText("");
        txtMobile.setText("");
        rbtnMale.setSelected(false);
        rbtnFemale.setSelected(false);
        cbDay.setSelectedIndex(0);
        cbMonth.setSelectedIndex(0);
        cbYear.setSelectedIndex(0);
        txtAddress.setText("");
        chkTerms.setSelected(false);
        displayArea.setText("");
    }

    private void loadData() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM users_dob";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sb.append("Name: ").append(rs.getString("name")).append("\\n");
                sb.append("Mobile: ").append(rs.getString("mobile")).append("\\n");
                sb.append("Gender: ").append(rs.getString("gender")).append("\\n");
                sb.append("DOB: ").append(rs.getString("dob")).append("\\n");
                sb.append("Address: ").append(rs.getString("address")).append("\\n");
                sb.append("------------------------------\\n");
            }
            displayArea.setText(sb.toString());
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationFormWithDOB form = new RegistrationFormWithDOB();
            form.setVisible(true);
        });
    }
}
