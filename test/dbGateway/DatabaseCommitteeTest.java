package dbGateway;

import fsc.entity.Committee;
import fsc.entity.Profile;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ProfileGateway;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseCommitteeTest extends BasicDatabaseTest {
  private static final String COMMITTEE_NAME = "FEC";
  public static final String SEAT_NAME = "fec_at_large";
  private Committee committee;
  private Seat seat;
  private Profile profile;

  @Test
  public void canCreateACommittee() throws CommitteeGateway.UnknownCommitteeException {
    saveTheCommittee();
    List<Committee> committees = anotherGateway.getCommittees();
    assertEquals(1, committees.size());
    Committee actual = committees.get(0);
    assertEquals(committee, actual);
    assertEquals(committee, anotherGateway.getCommittee(COMMITTEE_NAME));
    assertEquals(true, anotherGateway.hasCommittee(COMMITTEE_NAME));
  }

  @Test
  public void canAddASeat()
        throws CommitteeGateway.UnknownCommitteeException,
               CommitteeGateway.UnknownSeatNameException {
    saveTheCommittee();
    Query query = Query.always();
    gateway.begin();
    seat = gateway.getEntityFactory().createSeat(SEAT_NAME, query, committee);
    gateway.addSeat(seat);
    gateway.commit();
    assertEquals(seat, anotherGateway.getSeat(COMMITTEE_NAME, SEAT_NAME));
  }

  @Test
  public void canChangeSeatProperties()
        throws CommitteeGateway.UnknownCommitteeException,
               CommitteeGateway.UnknownSeatNameException,
               ProfileGateway.InvalidProfileUsernameException {
    String newName = "newName";
    saveCommitteeAndSeat();
    gateway.begin();
    seat.setName(newName);
    seat.setCandidateQuery(Query.never());
    Profile aProfile = gateway.getProfile(profile.getUsername());
    seat.setProfile(aProfile);
    gateway.commit();
    assertEquals(profile, aProfile);
    assertEquals(seat, anotherGateway.getSeat(COMMITTEE_NAME, newName));
    assertEquals(Query.never(), anotherGateway.getSeat(COMMITTEE_NAME, newName).getCandidateQuery());
    assertEquals(profile, anotherGateway.getSeat(COMMITTEE_NAME, newName).getProfile());
  }

  private void saveCommitteeAndSeat() {
    committee = gateway.getEntityFactory()
                       .createCommittee(COMMITTEE_NAME, "Faculty Evaluation", Query.always());
    seat = gateway.getEntityFactory().createSeat(SEAT_NAME, Query.always(), committee);
    profile = gateway.getEntityFactory().createProfile("name", "username", "", "");
    gateway.addProfile(profile);
    gateway.addCommittee(committee);
    gateway.addSeat(seat);
    gateway.commit();
  }

  private void saveTheCommittee() {
    committee = gateway.getEntityFactory()
                       .createCommittee(COMMITTEE_NAME, "Faculty Evaluation", Query.always());
    gateway.addCommittee(committee);
    gateway.commit();
  }

}
