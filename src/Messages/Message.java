package Messages;

import Users.User;

import java.time.Clock;

public class Message {

  private final User sender;
  private final User recipient;
  private final Clock time;
  private final String content;

  public Message(User sender, User recipient, Clock time, String content) {
    this.sender = sender;
    this.recipient = recipient;
    this.time = time;
    this.content = content;
    //MESSAGE POST
  }
}
