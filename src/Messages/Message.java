package Messages;

import java.io.IOException;
import java.util.Date;

import DB.MongoConnection;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.simple.parser.ParseException;

public class Message {

  private final String senderUsername;
  private final String recipientUsername;
  private final Date time;
  private final String content;

  public Message(String sender, String recipient, String content) {
    this.senderUsername = sender;
    this.recipientUsername = recipient;
    this.time = new Date();
    this.content = content;
  }

  public void addMessage(MongoCollection<Document> messageCollection) {
      Document doc = new Document("sender", senderUsername)
          .append("recipient", recipientUsername)
          .append("time", time.toString())
          .append("content", content); 
      messageCollection.insertOne(doc);
  }

  public static void main(String[] args) throws IOException, ParseException {
      Message msg = new Message("norbertomat", "oscargarcia", "Good, good, you? Is that even a question??? Ofcourse I am!");
      msg.addMessage(MongoConnection.DBConnect().getCollection("messages"));
      Message msg2 = new Message("oscargarcia", "norbertomat", "Awesoome. I'll see you there :)");
      msg2.addMessage(MongoConnection.DBConnect().getCollection("messages"));
      Message msg3 = new Message("norbertomat","oscargarcia","See you there buddy");
      msg3.addMessage(MongoConnection.DBConnect().getCollection("messages"));
      Message msg4 = new Message("billyg", "musky","Yo, can you swing me 3 long. Feeling like buying a private island today XD");
      msg4.addMessage(MongoConnection.DBConnect().getCollection("messages"));
      Message msg5 = new Message("musky","billyg","Ok, Bill, but last time. I'm tired of this");
      msg5.addMessage(MongoConnection.DBConnect().getCollection("messages"));
      Message msg6 = new Message("billyg","musky","Understood, cheers.");
      msg6.addMessage(MongoConnection.DBConnect().getCollection("messages"));

  }
}
