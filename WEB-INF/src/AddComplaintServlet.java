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

@WebServlet("/addComplaint")
public class AddComplaintServlet extends HttpServlet {

    private Gson json = new Gson();

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // get data from frontend
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        // create the object (like before)
        Complaint complaint = new Complaint(name, category, title, description);

        // add the complaint
        try {
            Connection conn = DBconnection.getConnection();

            String sql = "INSERT INTO complaints (name, category, title, description) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, category);
            ps.setString(3, title);
            ps.setString(4, description);

            ps.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // convert to json
        String response = json.toJson(complaint);// complaint OBJ -> JSON

        // send back the response
        PrintWriter out = res.getWriter();
        out.println(response);
        out.flush();
    }

}