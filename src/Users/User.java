package Users;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
              Date dateOfBirth, File profilePic, List<Interests> interests)
      throws IOException {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
    this.interests = interests;
    this.friends = new ArrayList<>();
    File targetFile = new File("profilepictures/" + userName + ".jpg");
    FileInputStream input = new FileInputStream(profilePic);
    FileOutputStream output = new FileOutputStream(targetFile);
    copyStream(input, output, 10000);
    output.flush();
    output.close();
    input.close();
    this.profilePic = targetFile.getPath();
  }

  public static void copyStream(final InputStream inputStream,
                                final OutputStream outputStream, final int bufferLength)
      throws IOException {
    // copy the input stream to the output stream
    byte[] buf = new byte[bufferLength];
    int len;
    while ((len = inputStream.read(buf)) > 0) {
      outputStream.write(buf, 0, len);
    }
  }

  public void addUser(MongoCollection<Document> usersCollection) {
    Document doc = new Document("first_name", firstName)
        .append("last_name", lastName)
        .append("user_name", userName)
        .append("password", password)
        .append("dob", dateOfBirth.toString())
        .append("interests", interests.stream().map(Enum::toString).collect(Collectors.toList()))
        .append("friends", friends.stream().map(friend -> friend.userName).collect(Collectors.toList()))
        .append("profile_pic", profilePic);
    usersCollection.insertOne(doc);
  }

  private void UpdateDB(MongoCollection<Document> usersCollection) {
    Document doc = new Document("first_name", firstName)
        .append("last_name", lastName)
        .append("user_name", userName)
        .append("password", password)
        .append("dob", dateOfBirth.toString())
        .append("interests", interests.stream().map(Enum::toString).collect(Collectors.toList()))
        .append("friends", friends.stream().map(friend -> friend.userName).collect(Collectors.toList()))
        .append("profile_pic", profilePic);
    usersCollection.findOneAndReplace(Filters.eq("user_name", userName), doc);
  }

  public void addFriend(User friend, MongoCollection<Document> usersCollection) {
    friends.add(friend);
    UpdateDB(usersCollection);
    friend.addFriend(this, usersCollection);
  }
  //Pre: friend is in list of friends.

  public void removeFriend(User friend, MongoCollection<Document> usersCollection) {
    friends.remove(friend);
    UpdateDB(usersCollection);
    friend.removeFriend(this, usersCollection);
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

  public String getProfile() {
    StringBuilder interestString = new StringBuilder();
    List<String> interestsList = interests.stream().map(Enum::toString)
        .collect(Collectors.toList());
    for (String interest : interestsList) {
      interestString.append(interest).append(",");
    }
    interestString.deleteCharAt(interestString.length() - 1);

    return userName + "," + profilePic + "," + firstName + "," + lastName + "," +
        dateOfBirth.toString() + "," +
        interestString.toString();
  }

  public String getFriends() {
    StringBuilder friendString = new StringBuilder();
    List<String> friendsList = friends.stream().map(friend -> friend.userName)
        .collect(Collectors.toList());
    for (String friend : friendsList) {
      friendString.append(friend).append(",");
    }
    friendString.deleteCharAt(friendString.length() - 1);
    return friendString.toString();
  }

  public static void main(String[] args) throws IOException, ParseException {
    MongoCollection<Document> users = MongoConnection.DBConnect().getCollection("users");
    User test = new User("Norberto", "Mateos", "norbertomat", "1234", new Date(2002,07,06),
        new File("profilepictures/Photo.jpg"), List.of(Interests.CODING, Interests.SPORTS));
    test.addUser(users);
    User test1 = new User("Oscar", "Garcia", "oscargarcia", "14534", new Date(2002,8,03),
        new File("profilepictures/Photo.jpg"), List.of(Interests.CODING, Interests.SPORTS));
    test1.addUser(users);
    test.addFriend(test1, users);
  }
}
