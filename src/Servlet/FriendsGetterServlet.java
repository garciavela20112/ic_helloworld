package Servlet;

import DB.MongoConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/friendsGetterServlet")
public class FriendsGetterServlet extends HttpServlet {

  public FriendsGetterServlet() {
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      MongoCollection<Document> users = MongoConnection.DBConnect().getCollection("users");
      String username = request.getParameter("user");
      FindIterable<Document> itr = users.find(Filters.eq("user_name", username));
      List<String> friendsList = itr.iterator().next().get("friends");

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

}
