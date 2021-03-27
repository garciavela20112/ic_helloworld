package Users;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DB.MongoConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.json.simple.parser.ParseException;

public class User {

  private final String firstName;
  private final String lastName;
  private String userName;
  private String password;
  private final List<User> friends;
  private final Date dateOfBirth;
  private List<Interests> interests;
  private String profilePic;

  public User(String firstName, String lastName, String userName, String password,
              Date dateOfBirth, String profilePic, List<Interests> interests) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
    this.interests = interests;
    this.friends = new ArrayList<>();
    this.profilePic = profilePic;
  }

  public void addUser(MongoCollection<Document> usersCollection) {
    Document doc = new Document("first_name", firstName)
        .append("last_name", lastName)
        .append("user_name", userName)
        .append("password", password)
        .append("dob", dateOfBirth)
        .append("interests", interests)
        .append("friends", friends)
        .append("profile_pic", profilePic);
    usersCollection.insertOne(doc);
  }

  private void UpdateDB(MongoCollection<Document> usersCollection) {
    Document doc = new Document("first_name", firstName)
        .append("last_name", lastName)
        .append("user_name", userName)
        .append("password", password)
        .append("dob", dateOfBirth)
        .append("interests", interests)
        .append("friends", friends)
        .append("profile_pic", profilePic);
    usersCollection.insertOne(doc);
    usersCollection.findOneAndReplace(Filters.eq("user_name", userName), doc);
  }

  public void addFriend(User friend, MongoCollection<Document> usersCollection) {
    friends.add(friend);
    UpdateDB(usersCollection);
  }
  //Pre: friend is in list of friends.

  public void removeFriend(User friend, MongoCollection<Document> usersCollection) {
    friends.remove(friend);
    UpdateDB(usersCollection);
  }

  public void changeUsername(String newUsername, MongoCollection<Document> usersCollection) {
    this.userName = newUsername;
    UpdateDB(usersCollection);
  }

  public void changePassword(String newPassword, MongoCollection<Document> usersCollection) {
    this.password = newPassword;
    UpdateDB(usersCollection);
  }

  public void changeProfilePic(String newProfilePic, MongoCollection<Document> usersCollection) {
    this.profilePic = newProfilePic;
    File file = new File(profilePic);
    file.delete();
    UpdateDB(usersCollection);
  }

  public void updateInterest(List<Interests> newInterests,
                             MongoCollection<Document> usersCollection) {
    this.interests = newInterests;
    UpdateDB(usersCollection);
  }
}
