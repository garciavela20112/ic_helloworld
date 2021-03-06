package DB;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class MongoUser {

  private final String firstName;
  private final String lastName;
  private String userName;
  private String password;
  private final List<String> friends;
  private final String dateOfBirth;
  private List<String> interests;
  private String profilePic;
  private final MongoCollection<Document> users;


  public MongoUser(String userName) throws IOException, ParseException {
    this.userName = userName;

    this.users = MongoConnection.DBConnect().getCollection("users");
    FindIterable<Document> itr = users.find(Filters.eq("user_name", userName));
    this.firstName = (String) itr.iterator().next().get("first_name");
    this.lastName = (String) itr.iterator().next().get("last_name");
    this.friends = (List<String>) itr.iterator().next().get("friends");
    this.dateOfBirth = (String) itr.iterator().next().get("dob");
    this.interests = (List<String>) itr.iterator().next().get("interests");
    this.profilePic = (String) itr.iterator().next().get("profile_pic");
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

  private void UpdateDB() {
    Document doc = new Document("first_name", firstName)
        .append("last_name", lastName)
        .append("user_name", userName)
        .append("password", password)
        .append("dob", dateOfBirth)
        .append("interests", interests)
        .append("friends", friends)
        .append("profile_pic", profilePic);
    users.findOneAndReplace(Filters.eq("user_name", userName), doc);
  }

  public void addFriend(String friendUserName) throws IOException, ParseException {
    UpdateDB();
    if (!(friends.contains(friendUserName))) {
      friends.add(friendUserName);
      UpdateDB();
      MongoUser friend = new MongoUser(friendUserName);
      friend.addFriend(this.userName);
      friend.UpdateDB();
    }
  }

  //Pre: friend is in list of friends.
  public void removeFriend(String friendUserName) throws IOException, ParseException {
    friends.remove(friendUserName);
    UpdateDB();
    MongoUser friend = new MongoUser(friendUserName);
    friend.removeFriend(this.userName);
    friend.UpdateDB();
  }

  public void changePassword(String newPassword) {
    this.password = newPassword;
    UpdateDB();
  }

  public void changeProfilePic(String newProfilePic) throws IOException {
    File targetFile = new File("profilepictures/" + userName + ".jpg");
    FileInputStream input = new FileInputStream(newProfilePic);
    FileOutputStream output = new FileOutputStream(targetFile);
    copyStream(input, output, 10000);
    output.flush();
    output.close();
    input.close();
    this.profilePic = targetFile.getPath();
    UpdateDB();
  }

  public void updateInterest(List<String> newInterests) {
    this.interests = newInterests;
    UpdateDB();
  }

  public String getProfile() {
    StringBuilder interestString = new StringBuilder();
    for (String interest : interests) {
      interestString.append(interest).append("|");
    }
    interestString.deleteCharAt(interestString.length() - 1);

    return userName + "|" + profilePic + "|" + firstName + "|" + lastName + "|" +
        dateOfBirth + "|" + interestString.toString();
  }

  public String getFriends() {
    StringBuilder friendString = new StringBuilder();
    for (String friend : friends) {
      friendString.append(friend).append("|");
    }
    friendString.deleteCharAt(friendString.length() - 1);
    return friendString.toString();
  }

  public boolean checkCredentials(String passwordTry) {
    return this.password.equals(passwordTry);

  }

  public static void main(String[] args) throws IOException, ParseException {
    MongoUser bill = new MongoUser("musky");
    bill.changePassword("asdnasd3");
  }
}
