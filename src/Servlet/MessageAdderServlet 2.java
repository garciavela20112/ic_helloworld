package Servlet;


import DB.MongoConnection;
import Messages.Message;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/messageAdderServlet")
public class MessageAdderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        try {
            MongoCollection<Document> messages = MongoConnection.DBConnect().getCollection("messages");

            //Message Information
            String sender = request.getParameter("sender");
            String recipient = request.getParameter("recipient");
            String content = request.getParameter("content");

            Message msg = new Message(sender, recipient, content);
            msg.addMessage(messages); 
            
        } catch (ParseException e) {
            e.printStackTrace(); 
        }
    }
}
