package Servlet;

import DB.MongoUser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profileGetterServlet")
public class ProfileGetterServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String username = request.getParameter("username");
      MongoUser user = new MongoUser(username);
      response.addHeader("profile", user.getProfile());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
