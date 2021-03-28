package Servlet;

import DB.MongoUser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/friendsAdderServlet")
public class FriendAdderServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String username = request.getParameter("user");
      String friendUsername = request.getParameter("friend");
      MongoUser user = new MongoUser(username);
      user.addFriend(friendUsername);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
