package Users;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnection {

  public static void main(String[] args) {
    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://root:ichelloworld" +
        "@cluster0.1rt3i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority)"));
    MongoCollection<Document> database =  mongoClient.getDatabase("elgemo").getCollection("users");
    System.out.println(database.find().toString());
  }

}

