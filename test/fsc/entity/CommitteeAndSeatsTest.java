package fsc.entity;

import fsc.entity.query.Query;
import fsc.mock.EntityStub;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommitteeAndSeatsTest {

  private Committee committee;
  private ArrayList<Seat> seats;
  private Seat seat;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    committee = entityFactory.createCommittee("cccc", "xxxx");
    seats = new ArrayList<>();
    seat = entityFactory.createSeat("a", Query.always());
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
    committee.addSeat(seat);

    assertThat(committee.getCommitteeSize(), is(1));
  }

  @Test
  public void setProfileOnSeat() {
    Profile profile = EntityStub.getProfile(0);
    seat.setProfile(profile);

    assertThat(seat.getProfile(), is(profile));
  }

  @Test
  public void setDefaultQueryOnSeat() {
    Query query = Query.always();
    seat.setDefaultQuery(query);

    assertThat(seat.getDefaultQuery(), is(query));
  }
}
