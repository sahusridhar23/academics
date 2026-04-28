import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/UpdateComplaintServlet")
public class UpdateComplaintServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String id = req.getParameter("id");
        String status = req.getParameter("status");

        try {
            Connection conn = DBconnection.getConnection();

            String sql = "UPDATE complaints SET status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, Integer.parseInt(id));

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