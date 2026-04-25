import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

record Complaint(int id, String name, String category, String title, String description) {
}

@WebServlet("/complaints")
public class GetComplaintsServlet extends HttpServlet {

    private Gson json = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        ArrayList<Complaint> complaints = new ArrayList<>();

        try {
            Connection conn = DBconnection.getConnection();

            String sql = "SELECT id, name, category, title, description FROM complaints";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Complaint c = new Complaint(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getString("description"));
                complaints.add(c);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = json.toJson(complaints);

        PrintWriter out = res.getWriter();
        out.println(response);
    }
}