import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/DeleteComplaintServlet")
public class DeleteComplaintServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String id = req.getParameter("id");

        try {
            Connection conn = DBconnection.getConnection();

            String sql = "DELETE FROM complaints WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));

            int rows = ps.executeUpdate();

            res.setContentType("text/plain");

            if (rows > 0)
                res.getWriter().print("success");
            else
                res.getWriter().print("failed");

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}