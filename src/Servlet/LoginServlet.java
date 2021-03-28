package Servlet;

import DB.MongoConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

    try {
      MongoCollection<Document> users = MongoConnection.DBConnect().getCollection("users");

      String username = request.getParameter("username");
      String password = request.getParameter("password");

      FindIterable<Document> itr = users.find(Filters.and(
          Filters.eq("user_name", username),
          Filters.eq("password", password)
      ));

      if (itr.iterator().hasNext()) {
        //Username and Password Match
      }


      //SC_ACCEPTED = 202
      //SC_NOT_ACCEPTABLE = 406
      response.setStatus(1);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

}