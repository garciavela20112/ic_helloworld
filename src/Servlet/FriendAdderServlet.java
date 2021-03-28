package Servlet;

import DB.MongoConnection;
import DB.MongoUser;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FriendAdderServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      MongoCollection<Document> users = MongoConnection.DBConnect().getCollection("users");
      String username = request.getParameter("user");
      MongoUser user = new MongoUser(username);
      String friends = user.getFriends();
      response.addHeader("friendsList", friends);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
