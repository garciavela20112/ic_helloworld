package DBConnection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class MongoConnection {

  public static MongoDatabase DBConnect() throws IOException, ParseException {

    JSONParser parser = new JSONParser();
    JSONObject config = (JSONObject) parser.parse(new FileReader("db_config.json"));
    String username = (String) config.get("username");
    String password = (String) config.get("password");
    String dbname = (String) config.get("db-name");
    String cname = (String) config.get("user-collection-name");


    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://" + username + ":"  + password +
        "@cluster0.1rt3i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority)"));
    return mongoClient.getDatabase(dbname);
  }

}

