package Users;

public class Date {

  private final Integer year;
  private final Integer month;
  private final Integer day;

  public Date(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  @Override
  public String toString() {
    return day.toString() + "/" + month.toString() + "/" + year.toString();
  }
}
