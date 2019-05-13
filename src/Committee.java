import java.util.ArrayList;

public class Committee {
  private String name;
  private String description;
  private int committeeSize;
  private Seat seat;
  private ArrayList<Seat> members;

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

  public void setCommitteeSize(int committeeSize) {
    this.committeeSize = committeeSize;
  }

  public int getSeatSize() {
    return committeeSize;
  }

  public void setMember(Seat seat) {
    this.members.add(seat);
  }

  public Seat getMember(int i) {
    return members.get(i);
  }
}
