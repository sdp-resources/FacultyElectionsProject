package dbGateway;

import fsc.entity.Committee;
import fsc.entity.Profile;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.Gateway;
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
  public void canCreateACommittee() {
    saveTheCommittee();
    withNewGateway(gateway -> {
      assertEquals(List.of(committee), gateway.getCommittees());
      assertEquals(true, gateway.hasCommittee(COMMITTEE_NAME));
      assertEquals(committee, getCommitteeOrNull(gateway, COMMITTEE_NAME));
    });
  }

  @Test
  public void canAddASeat() {
    saveCommitteeSeatAndProfile();
    Seat theSeat = returnWithNewGateway(
          gateway -> getSeatOrNull(gateway, COMMITTEE_NAME, SEAT_NAME));
    assertEquals(seat, theSeat);
  }

  @Test
  public void canChangeSeatProperties()
        throws CommitteeGateway.UnknownCommitteeException,
               CommitteeGateway.UnknownSeatNameException {
    saveCommitteeSeatAndProfile();
    String newName = returnWithNewGateway(this::changeSeatInformation);
    assertSeatHasGivenQueryAndProfile(anotherGateway.getSeat(COMMITTEE_NAME, newName),
                                      Query.never(), profile);
  }

  private void assertSeatHasGivenQueryAndProfile(
        Seat seat, Query expectedQuery, Profile expectedProfile
  ) {
    assertEquals(expectedQuery, seat.getCandidateQuery());
    assertEquals(expectedProfile, seat.getProfile());
  }

  private String changeSeatInformation(Gateway gateway) {
    String newName = "newName";
    gateway.begin();
    Seat seatCopy = getSeatOrNull(gateway, committee.getName(), seat.getName());
    seatCopy.setName(newName);
    seatCopy.setCandidateQuery(Query.never());
    seatCopy.setProfile(profile);
    gateway.commit();
    return newName;
  }

  private Seat getSeatOrNull(Gateway gateway, String committeeName, String seatName) {
    try {
      return gateway.getSeat(committeeName, seatName);
    } catch (CommitteeGateway.UnknownCommitteeException |
                   CommitteeGateway.UnknownSeatNameException e) {
      return null;
    }
  }

  private Committee getCommitteeOrNull(Gateway gateway, String committeeName) {
    try {
      return gateway.getCommittee(committeeName);
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return null;
    }
  }

  private void saveCommitteeSeatAndProfile() {
    withNewGateway(gateway -> {
      committee = gateway.getEntityFactory()
                         .createCommittee(COMMITTEE_NAME, "Faculty Evaluation", Query.always());
      seat = gateway.getEntityFactory().createSeat(SEAT_NAME, Query.always(), committee);
      profile = gateway.getEntityFactory().createProfile("name", "username", "", "");
      gateway.addProfile(profile);
      gateway.addCommittee(committee);
      gateway.addSeat(seat);
      gateway.commit();
    });
  }

  private void saveTheCommittee() {
    withNewGateway(gateway -> {
      committee = gateway.getEntityFactory()
                         .createCommittee(COMMITTEE_NAME, "Faculty Evaluation", Query.always());
      gateway.addCommittee(committee);
      gateway.commit();
    });
  }

}
