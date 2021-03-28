package Servlet;

import DB.MongoConnection;
import DB.MongoUser;
import Users.Date;
import Users.Interests;
import Users.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

    try {
      MongoCollection<Document> users = MongoConnection.DBConnect().getCollection("users");

      String username = request.getParameter("username");
      String firstname = request.getParameter("firstname");
      String lastname = request.getParameter("lastname");
      String password = request.getParameter("password");
      String dateToParse = request.getParameter("dob");
      Date dob = null;
      if (parseDate(dateToParse).isEmpty()) {
        response.addHeader("invalidDate", "true");
      } else {
        dob = parseDate(dateToParse).get();
        response.addHeader("invalidDate", "false");
      }

      String interestsToParse = request.getParameter("interests");
      String interestStrings[] = interestsToParse.split(",");
      List<Interests> interests = new ArrayList<>();
      for(String s : interestStrings) {
        Interests toAdd =
            switch (s.toUpperCase()) {
              case "SPORTS" -> Interests.SPORTS;
              case "ESPORTS" -> Interests.ESPORTS;
              case "COOKING" -> Interests.COOKING;
              case "READING" -> Interests.READING;
              case "ART" -> Interests.ART;
              case "MUSIC" -> Interests.MUSIC;
              case "TRAVELLING" -> Interests.TRAVELLING;
              case "LANGUAGES" -> Interests.LANGUAGES;
              case "CODING" -> Interests.CODING;
              case "CINEMA" -> Interests.CINEMA;
              case "TECHNOLOGY" -> Interests.TECHNOLOGY;
              default -> throw new IllegalStateException("Unexpected value: " + s.toUpperCase());
            };
        interests.add(toAdd);
      }

      FindIterable<Document> itr = users.find(Filters.eq("user_name", username));

      if (itr.iterator().hasNext()) {
        response.addHeader("userTaken", "true");
        response.addHeader("success", "false");
      } else if (!(dob == null)) {
        response.addHeader("userTaken", "false");
        User newUser = new User(firstname, lastname, username, password, dob, interests);
        newUser.addUser(users);
        response.addHeader("success", "true");
      } else {
        response.addHeader("success", "false");
      }

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private static Optional<Date> parseDate(String dateToParse) {
    String dateElements[] = dateToParse.split("/");
    if (!(0 < Integer.parseInt(dateElements[0]) && Integer.parseInt(dateElements[0]) <= 31)) {
      return Optional.empty();
    }
    if (!(0 < Integer.parseInt(dateElements[1]) && Integer.parseInt(dateElements[1]) <= 12)) {
      return Optional.empty();
    }
    if (!(dateElements[2].length() == 4)) {
      return Optional.empty();
    }
    return Optional.of(new Date(Integer.parseInt(dateElements[2]),
        Integer.parseInt(dateElements[1]), Integer.parseInt(dateElements[0])));
  }

}
