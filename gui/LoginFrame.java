package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import service.AuthService;
import db.DBConnection; 

public class LoginFrame extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 245);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);

    public LoginFrame() {
        setTitle("Campus Management System");
        setSize(400, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        setContentPane(mainPanel);

        JLabel headerLabel = new JLabel("User Login", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR);

        JTextField usernameField = new JTextField();
        styleTextField(usernameField);

        JPasswordField passwordField = new JPasswordField();
        styleTextField(passwordField);

        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Student", "Faculty", "Admin", "Department"});
        styleComboBox(roleBox);

        JButton loginButton = new JButton("Login");
        styleButton(loginButton, BUTTON_COLOR);

        JButton registerButton = new JButton("Register");
        styleButton(registerButton, new Color(100, 149, 237)); 

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");
        styleLabel(usernameLabel);
        styleLabel(passwordLabel);
        styleLabel(roleLabel);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(roleLabel);
        formPanel.add(roleBox);
        formPanel.add(loginButton);
        formPanel.add(registerButton); 

        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = roleBox.getSelectedItem().toString();

            System.out.println("INPUT — Username: " + username + " | Password: " + password + " | Role: " + role);

            if (AuthService.authenticate(username, password, role)) {
                dispose();
                switch (role) {
                    case "Admin" -> new AdminDashboard().setVisible(true);
                    case "Department" -> {
                        String departmentName = switch (username) {
                            case "dept1" -> "IT";
                            case "dept2" -> "Maintenance";
                            case "dept3" -> "Library";
                            default -> "General";
                        };
                        new DepartmentDashboard(departmentName).setVisible(true);
                    }
                    case "Student" -> new StudentDashboard(username).setVisible(true);
                    case "Faculty" -> new FacultyDashboard(username).setVisible(true);
                    default -> JOptionPane.showMessageDialog(this, "Unknown role.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = roleBox.getSelectedItem().toString();

            if (!username.isEmpty() && !password.isEmpty() && !role.isEmpty()) {
                saveUserCredentials(username, password, role);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        });

        setResizable(false);
        setVisible(true);
    }

    private void saveUserCredentials(String username, String password, String role) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleTextField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(5, 7, 5, 7)));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(new EmptyBorder(8, 0, 8, 0));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
