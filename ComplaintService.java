import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.DBConnection;

public class ComplaintService {
    public void submitComplaint(String title, String description, String department) {
        String query = "INSERT INTO complaints (title, description, department, status) VALUES (?, ?, ?, 'Pending')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, department);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllComplaints() {
        List<String> complaints = new ArrayList<>();
        String query = "SELECT * FROM complaints";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String comp = "ID: " + rs.getInt("id") +
                        ", Title: " + rs.getString("title") +
                        ", Dept: " + rs.getString("department") +
                        ", Status: " + rs.getString("status");
                complaints.add(comp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }
}
