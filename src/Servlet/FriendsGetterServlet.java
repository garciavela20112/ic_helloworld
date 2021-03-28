package Servlet;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/friendsGetterServlet")
public class FriendsGetterServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    MongoCollection<Document>


}
