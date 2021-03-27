package Users;

public class Date {

  private final boolean present;
  private final Integer year;
  private final Integer month;
  private final Integer day;

  public Date(int year, int month, int day) {
    this.present = true;
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public Date() {
    this.present = false;
    this.year = null;
    this.month = null;
    this.day = null;
  }
}
