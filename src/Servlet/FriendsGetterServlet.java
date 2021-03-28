package Servlet;

import DB.MongoUser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/friendsGetterServlet")
public class FriendsGetterServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String username = request.getParameter("user");
      MongoUser user = new MongoUser(username);
      String friends = user.getFriends();
      response.addHeader("friendsList", friends);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

}
