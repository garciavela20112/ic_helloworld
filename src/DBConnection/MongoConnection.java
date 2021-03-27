package DBConnection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class MongoConnection {

  public static void main(String[] args) throws IOException, ParseException {

    JSONParser parser = new JSONParser();
    JSONArray a = (JSONArray) parser.parse(new FileReader("../../db_config.json"));

    Object o = a.get(0);
    JSONObject config = (JSONObject) o;
    Object u = config.get("authentication");
    JSONObject uname = (JSONObject) u;
    String username = (String) uname.get("username");
    String password = (String) uname.get("password");

    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://" + username + ":"  + password +
        "@cluster0.1rt3i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority)"));
    MongoCollection<Document> database =  mongoClient.getDatabase("elgemo").getCollection("users");
    System.out.println(database.find().toString());
  }

}

