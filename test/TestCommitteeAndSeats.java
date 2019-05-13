import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestCommitteeAndSeats {

  private Committee committee;
  private ArrayList<Seat> seats;

  @Before
  public void setup() {
    committee = new Committee();
    seats = new ArrayList<>();
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
    assertThat(committee.getCommitteeSize(), is(0));
  }

  @Test
  public void committeeHasOneSeat() {
    Seat seat = new Seat();
    committee.addMember(0, seat);

    assertThat(committee.getCommitteeSize(), is(1));
  }
}
