import java.util.ArrayList;

public class Committee {
  private String name;
  private String description;
  private int committeeSize;
  private ArrayList<Seat> seats = new ArrayList<>();

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getCommitteeSize() {
    return seats.size();
  }

  public void addMember(int i, Seat seat) {
    this.seats.add(i, seat);
  }
}
