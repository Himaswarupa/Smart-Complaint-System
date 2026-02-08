package model;

import java.time.LocalDateTime;

public class Complaint {
    private String id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String submittedBy;
    private String assignedTo;
    private String assignedDepartment;
    private LocalDateTime timestamp;

    public Complaint(String id, String title, String description, String submittedBy,
                     String priority, String status, String department,
                     String assignedTo, LocalDateTime timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.submittedBy = submittedBy;
        this.priority = priority;
        this.status = status;
        this.assignedDepartment = department;
        this.assignedTo = assignedTo;
        this.timestamp = timestamp;
    }

    public Complaint(String id, String title, String description, String submittedBy, String priority) {
        this(id, title, description, submittedBy, priority, "New", null, null, LocalDateTime.now());
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public String getSubmittedBy() { return submittedBy; }
    public String getAssignedTo() { return assignedTo; }
    public String getAssignedDepartment() { return assignedDepartment; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setStatus(String status) { this.status = status; }
    public void updateStatus(String newStatus) { this.status = newStatus; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    public void setAssignedDepartment(String dept) { this.assignedDepartment = dept; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
