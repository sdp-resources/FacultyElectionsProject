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
    committee = entityFactory.createCommittee("cccc", "xxxx", null);
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
    seat = entityFactory.createSeat("a", Query.always(), committee);

    assertThat(committee.getCommitteeSize(), is(1));
  }

  @Test
  public void setProfileOnSeat() {
    seat = entityFactory.createSeat("a", Query.always(), committee);
    Profile profile = EntityStub.getProfile(0);
    seat.setProfile(profile);

    assertThat(seat.getProfile(), is(profile));
  }

  @Test
  public void setDefaultQueryOnSeat() {
    seat = entityFactory.createSeat("a", Query.always(), committee);
    Query query = Query.always();
    seat.setCandidateQuery(query);

    assertThat(seat.getCandidateQuery(), is(query));
  }
}
