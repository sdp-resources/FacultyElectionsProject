import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestCommitteeAndSeats {

  private Committee committee;
  private ArrayList<Seat> seats;
  private Seat seat;

  @Before
  public void setup() {
    committee = new Committee();
    seats = new ArrayList<>();
    seat = new Seat();
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
    committee.addMember(0, seat);

    assertThat(committee.getCommitteeSize(), is(1));
  }

  @Test
  public void setProfileOnSeat(){
    Profile profile = new Profile("Skiadas","skiadas", "Natural Science", "tenured");
    seat.setProfile(profile);

    assertThat(seat.getProfile(), is(profile));
  }

  @Test
  public void setRuleOnSeat(){
    Rule rule = new Rule();
    rule.setRequiresActive(true);
    seat.setRule(rule);

    assertThat(rule.getRequiresActive(),is(true));
    assertThat(seat.getRule(), is(rule));
  }
}
