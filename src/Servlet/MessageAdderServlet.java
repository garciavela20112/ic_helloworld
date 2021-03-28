package Servlet;


import java.io.IOException; 

import javax.servlet.ServletException;

@WebServlet("/messageAdderSevlet")
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
