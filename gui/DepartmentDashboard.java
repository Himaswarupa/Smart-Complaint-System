package gui;

import model.Complaint;
import service.ComplaintManager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DepartmentDashboard extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 245);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180); 
    private static final Color HEADER_BG = new Color(230, 230, 235);
    
    private JTable complaintTable;
    private DefaultTableModel tableModel;
    private String departmentName;
    
    public DepartmentDashboard(String departmentName) {
        this.departmentName = departmentName;
        
        setTitle("Department Dashboard - " + departmentName);
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        setContentPane(mainPanel);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel heading = new JLabel("Department: " + departmentName, JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setForeground(new Color(50, 50, 50));
        headerPanel.add(heading, BorderLayout.NORTH);
        
        JLabel subHeading = new JLabel("Assigned Complaints", JLabel.CENTER);
        subHeading.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subHeading.setBorder(new EmptyBorder(5, 0, 15, 0));
        headerPanel.add(subHeading, BorderLayout.CENTER);
        
        String[] columns = {"ID", "Title", "Submitted By", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        complaintTable = new JTable(tableModel);
        complaintTable.setRowHeight(25);
        complaintTable.setShowVerticalLines(false);
        complaintTable.setSelectionBackground(new Color(210, 230, 250));
        complaintTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        complaintTable.getTableHeader().setBackground(HEADER_BG);
        complaintTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        TableColumnModel columnModel = complaintTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  
        columnModel.getColumn(1).setPreferredWidth(250); 
        columnModel.getColumn(2).setPreferredWidth(150); 
        columnModel.getColumn(3).setPreferredWidth(100); 
        
        JScrollPane scrollPane = new JScrollPane(complaintTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        JButton updateBtn = new JButton("Update Status");
        styleButton(updateBtn, BUTTON_COLOR);
        
        JButton refreshBtn = new JButton("Refresh");
        styleButton(refreshBtn, new Color(100, 150, 100));
        
        JButton logoutBtn = new JButton("Logout");
        styleButton(logoutBtn, new Color(150, 150, 150));
        
        buttonPanel.add(updateBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(logoutBtn);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadDepartmentComplaints();

        updateBtn.addActionListener(e -> updateComplaintStatus());
        refreshBtn.addActionListener(e -> loadDepartmentComplaints());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Total complaints: " + tableModel.getRowCount());
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        statusBar.add(statusLabel);
        mainPanel.add(statusBar, BorderLayout.PAGE_END);
        
        setVisible(true);
    }
    
    private void loadDepartmentComplaints() {
        tableModel.setRowCount(0);
        
        List<Complaint> allComplaints = ComplaintManager.getInstance().getAllComplaints();
        
        for (Complaint c : allComplaints) {
            if (c.getAssignedDepartment() != null && c.getAssignedDepartment().equalsIgnoreCase(departmentName)) {
                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getTitle(),
                        c.getSubmittedBy(),
                        c.getStatus()
                });
            }
        }
    }
    
    private void updateComplaintStatus() {
        int row = complaintTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a complaint first.", 
                "No Selection", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String complaintId = tableModel.getValueAt(row, 0).toString();
        String[] options = {"Pending", "In Progress", "Resolved"};

        JDialog dialog = new JDialog(this, "Update Complaint Status", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 15));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Select new status for complaint #" + complaintId);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JComboBox<String> statusBox = new JComboBox<>(options);
        statusBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.setBackground(Color.WHITE);
        JButton cancelButton = new JButton("Cancel");
        JButton updateButton = new JButton("Update");
        updateButton.setBackground(BUTTON_COLOR);
        updateButton.setForeground(Color.WHITE);
        buttonPane.add(cancelButton);
        buttonPane.add(updateButton);
        
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(statusBox, BorderLayout.CENTER);
        contentPanel.add(buttonPane, BorderLayout.SOUTH);
        dialog.add(contentPanel);

        cancelButton.addActionListener(e -> dialog.dispose());
        updateButton.addActionListener(e -> {
            String newStatus = statusBox.getSelectedItem().toString();
            ComplaintManager cm = ComplaintManager.getInstance();
            cm.updateComplaintStatus(complaintId, newStatus);
            loadDepartmentComplaints();
            JOptionPane.showMessageDialog(this, "Status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        dialog.setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(new EmptyBorder(8, 15, 8, 15));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(bgColor.darker()); }
            public void mouseExited(MouseEvent e) { button.setBackground(bgColor); }
        });
    }
}