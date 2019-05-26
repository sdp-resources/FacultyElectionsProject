package fsc.entity;

import fsc.entity.query.Query;
import fsc.mock.AlwaysTrueQueryStub;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommitteeAndSeatsTest {

  private Committee committee;
  private ArrayList<Seat> seats;
  private Seat seat;

  @Before
  public void setup() {
    committee = new Committee("cccc", "xxxx");
    seats = new ArrayList<>();
    AlwaysTrueQueryStub queryStub = new AlwaysTrueQueryStub();
    seat = new Seat("a", queryStub);
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
    committee.addMember(seat);

    assertThat(committee.getCommitteeSize(), is(1));
  }

  @Test
  public void setProfileOnSeat() {
    Profile profile = new Profile("Skiadas", "skiadas", "Natural Science", "tenured");
    seat.setProfile(profile);

    assertThat(seat.getProfile(), is(profile));
  }

  @Test
  public void setDefaultQueryOnSeat() {
    Query query = new AlwaysTrueQueryStub();
    seat.setDefaultQuery(query);

    assertThat(seat.getDefaultQuery(), is(query));
  }
}
