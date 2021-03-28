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

@WebServlet("/messagesGetterServlet")
public class MessagesGetterServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      MongoCollection<Document> messages = MongoConnection.DBConnect().getCollection("messages");
      String sender = request.getParameter("sender");
      String recipient = request.getParameter("recipient");
      FindIterable<Document> itr = messages.find(Filters.or(Filters.and(Filters.eq("sender"
        , sender),Filters.eq("recipient", recipient)),Filters.and(Filters.eq("sender",
        recipient),Filters.eq("recipient", sender))));
      StringBuilder messagesString = new StringBuilder();
      for (Document message : itr) {
        messagesString.append(message.get("sender")).append("|");
        messagesString.append(message.get("recipient")).append("|");
        messagesString.append(message.get("content")).append("|");
        messagesString.append(message.get("time")).append("|");
      }
      messagesString.deleteCharAt(messagesString.length() - 1);
      response.addHeader("messages", messagesString.toString());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}

