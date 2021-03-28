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
      Message msg = new Message("oscargarcia", "norbertomat", "Hey, how you been? You going to the Hackathon?");
      msg.addMessage(MongoConnection.DBConnect().getCollection("messages"));
  }
}
