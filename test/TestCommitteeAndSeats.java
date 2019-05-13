import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestCommitteeAndSeats {

  private Committee committee;
  private ArrayList<Seat> members;

  @Before
  public void setup() {
    committee = new Committee();
    members = new ArrayList<>();
  }

  @Test
  public void committeeHasNameNoSeatsNoDescription() {
    String name = "CCCC";
    committee.setName(name);
    assertThat(committee.getName(), is("CCCC"));
  }

  @Test
  public void addDescriptionToCommittee() {
    String description = "nothing";
    committee.setDescription(description);
    assertThat(committee.getDescription(), is("nothing"));
  }

  @Test
  public void committeeHasZeroSeats() {
    committee.setCommitteeSize(0);
    assertThat(committee.getSeatSize(), is(0));
  }

  @Test
  public void seatExists() {
    Seat seat1 = new Seat();
    committee.setMember(seat1);
    assertThat(committee.getMember(0), is(seat1));

  }
}
