import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String[] options = {"Registration Form", "Registration Form with DOB"};
        int choice = JOptionPane.showOptionDialog(null, "Select the form to open:", "Form Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            SwingUtilities.invokeLater(() -> {
                RegistrationForm form = new RegistrationForm();
                form.setVisible(true);
            });
        } else if (choice == 1) {
            SwingUtilities.invokeLater(() -> {
                RegistrationFormWithDOB form = new RegistrationFormWithDOB();
                form.setVisible(true);
            });
        } else {
            System.exit(0);
        }
    }
}
