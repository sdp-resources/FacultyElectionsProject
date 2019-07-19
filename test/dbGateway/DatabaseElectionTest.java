package dbGateway;

import fsc.entity.Committee;
import fsc.entity.Election;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.gateway.ElectionGateway;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DatabaseElectionTest extends BasicDatabaseTest {
  private Election election;

  @Test
  public void canCreateAndSaveElection() throws ElectionGateway.InvalidElectionIDException {
    saveTheElection();
    assertNotNull(election.getID());
    Election anotherGatewayElection = anotherGateway.getElection(this.election.getID());
    assertNotNull(anotherGatewayElection);
  }

  private void saveTheElection() {
    Committee committee = gateway.getEntityFactory()
                                 .createCommittee("committee name",
                                                  "committee description",
                                                  Query.always());
    Seat seat = gateway.getEntityFactory().createSeat("seat name", Query.always(),
                                                      committee);
    election = gateway.getEntityFactory().createElection(seat);
    gateway.addCommittee(committee);
    gateway.addSeat(seat);
    gateway.addElection(election);
    gateway.save();
    gateway.commit();
  }
}