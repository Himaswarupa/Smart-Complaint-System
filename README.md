# Smart Complaint Management System

## Project Overview
The Smart Complaint Management System is a Java-based desktop application developed using Object-Oriented Programming concepts. The system provides a centralized platform for students, faculty, departments, and administrators to submit, track, assign, and resolve complaints efficiently.

The application uses Java Swing for GUI development, MySQL for database management, and JDBC for database connectivity. It replaces manual complaint handling processes and improves transparency, accountability, and workflow efficiency.



## Features

- Role-based login system (Student, Admin, Faculty, Department)
- Complaint submission with category and priority selection
- Real-time complaint status tracking
- Admin dashboard for complaint assignment and monitoring
- Faculty and Department dashboard for complaint resolution
- Complaint storage using MySQL and text file backup
- Complaint history tracking
- User-friendly Java Swing interface



## Technology Stack

- Programming Language: Java  
- GUI Framework: Java Swing  
- Database: MySQL  
- Database Connectivity: JDBC  
- Project Type: Core Java Desktop Application  



## Project Folder Structure

```
SMART COMPLAINT SYSTEM
│
├── App.java
├── complaints.txt
├── smart_complaint_db.sql
├── mysql-connector-j-9.3.0.jar
│
├── db/
│   └── DBConnection.java
│
├── gui/
│   ├── LoginFrame.java
│   ├── StudentDashboard.java
│   ├── AdminDashboard.java
│   ├── FacultyDashboard.java
│   ├── DepartmentDashboard.java
│   ├── ComplaintForm.java
│   ├── ViewComplaints.java
│   └── AdminViewComplaints.java
│
├── model/
│   ├── User.java
│   ├── Student.java
│   ├── Admin.java
│   ├── Faculty.java
│   ├── Department.java
│   └── Complaint.java
│
├── service/
│   ├── AuthService.java
│   ├── ComplaintManager.java
│   └── FileService.java
│
└── util/
    └── Constants.java
```



## Installation and Setup Guide

### Step 1: Clone Repository
```
git clone https://github.com/your-username/smart-complaint-system.git
```

### Step 2: Install Requirements

- Java JDK 8 or above  
- MySQL Server 8.0 or above  
- Any Java IDE such as IntelliJ, Eclipse, or NetBeans  



### Step 3: Setup Database

1. Open MySQL
2. Create a new database
3. Import the SQL file:
```
smart_complaint_db.sql
```



### Step 4: Configure Database Connection

Open:
```
db/DBConnection.java
```

Update database URL, username, and password.



### Step 5: Add JDBC Driver

Ensure the following file is added to project libraries:
```
mysql-connector-j-9.3.0.jar
```

---

### Step 6: Run the Application

Run:
```
App.java
```



## System Requirements

### Software Requirements
- Java JDK 8 or higher  
- MySQL Server  
- JDBC Driver  
- Java IDE  

### Hardware Requirements
- 2 GHz Processor  
- Minimum 2 GB RAM  
- 200 MB Free Disk Space  



## Functional Outcomes

- Centralized complaint management  
- Transparent complaint tracking  
- Role-based system workflow  
- Automated complaint assignment  
- Improved institutional communication and efficiency  



## Application Areas

- Educational Institutions  
- Student Housing Systems  
- Training Institutes  
- Research Organizations   
