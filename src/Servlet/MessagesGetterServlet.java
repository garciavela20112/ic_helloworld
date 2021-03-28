package Servlet;

import DB.MongoConnection;

@WebServlet("/messagesGetterServlet")
public class MessagesGetterServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            MongoCollection<Document> messages = MongoConnection.DBConnect().getCollection("messages");
            String sender = request.getParameter("sender");
            String recipient = request.getParameter("recipient");
            FindIterable<Document> itr = messages.find(Filters.or(Filters.and(Filter.eq("sender", sender),Filter.eq("recipient", recipient)),Filter.and(Filter.eq("sender", recipient),Filter.eq("recipient", sender))));
            Iterator iterator = itr.iterator();
        }
    }
}

