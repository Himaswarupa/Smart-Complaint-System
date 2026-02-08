package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 245);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180); 
    
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        setContentPane(mainPanel);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel welcome = new JLabel("Administrator Control Panel", JLabel.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 24));
        welcome.setForeground(new Color(50, 50, 50));
        welcome.setBorder(new EmptyBorder(0, 0, 20, 0));
        headerPanel.add(welcome, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(BACKGROUND_COLOR);
        JLabel statusLabel = new JLabel("Admin Portal • Active Session");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
        statusPanel.add(statusLabel);
        headerPanel.add(statusPanel, BorderLayout.SOUTH);
 
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        
        JButton viewAllBtn = new JButton("View All Complaints");
        styleButton(viewAllBtn, BUTTON_COLOR);
        
        JButton manageUsersBtn = new JButton("Manage Users");
        styleButton(manageUsersBtn, BUTTON_COLOR);
        
        JButton systemSettingsBtn = new JButton("System Settings");
        styleButton(systemSettingsBtn, BUTTON_COLOR);
        
        buttonPanel.add(viewAllBtn);
        buttonPanel.add(manageUsersBtn);
        buttonPanel.add(systemSettingsBtn);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(BACKGROUND_COLOR);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerPanel.add(logoutBtn);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        viewAllBtn.addActionListener(e -> new AdminViewComplaints().setVisible(true));
        
        manageUsersBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "User management will be implemented in future updates.", 
            "Coming Soon", JOptionPane.INFORMATION_MESSAGE));
        
        systemSettingsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "System settings will be implemented in future updates.", 
            "Coming Soon", JOptionPane.INFORMATION_MESSAGE));
        
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        
        setResizable(false);
        setVisible(true);
    }
  
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(12, 0, 12, 0));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(bgColor.darker()); }
            public void mouseExited(MouseEvent e) { button.setBackground(bgColor); }
        });
    }
}