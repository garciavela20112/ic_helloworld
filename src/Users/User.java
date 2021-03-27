package Users;

import java.util.List;
import com.mongodb.MongoClient;

public class User {

  private final String firstName;
  private final String lastName;
  private String userName;
  private static int id = 0;
  private final int privateUserName;
  private String password;
  private List<User> friends;
  private final Date dateOfBirth;
  private final List<Interests> interests;

  public User(String firstName, String lastName, String userName, Date dateOfBirth,
              List<Interests> interests) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.privateUserName = id;
    this.dateOfBirth = dateOfBirth;
    this.interests = interests;
    id++;
  }

  public User(String firstName, String lastName, String userName, List<Interests> interests) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.privateUserName = id;
    this.dateOfBirth = new Date();
    this.interests = interests;
    id++;
  }

    MongoClient test = new MongoClient();

}
