package dbGateway;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseCommitteeTest extends BasicDatabaseTest {
  private static final String COMMITTEE_NAME = "FEC";
  public static final String SEAT_NAME = "fec_at_large";
  private Committee committee;
  private SimpleEntityFactory entityFactory;

  @Before
  public void setUp() {
    super.setUp();
    entityFactory = new SimpleEntityFactory();
  }

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
    Seat seat = entityFactory.createSeat(SEAT_NAME, query, committee);
    gateway.addSeat(seat);
    gateway.commit();
    assertEquals(seat, anotherGateway.getSeat(COMMITTEE_NAME, SEAT_NAME));
  }

  private void saveTheCommittee() {
    committee = gateway.getEntityFactory()
                       .createCommittee(COMMITTEE_NAME, "Faculty Evaluation");
    gateway.addCommittee(committee);
    gateway.commit();
  }

}
