package fsc.entity;

import fsc.app.AppContext;
import fsc.entity.query.AttributeQuery;
import fsc.entity.query.Query;
import fsc.gateway.ElectionGateway;
import org.junit.Before;

class ElectionCreatorTest {

  private Election election;
  private ElectionGateway mockGateway;
  Seat seat;
  Committee committee;

  @Before
  public void setUp() {
    Committee committee = AppContext.getEntityFactory().createCommittee("f", "g");
    Query queryStub = Query.always();
    Seat seat = AppContext.getEntityFactory().createSeat("a", queryStub);
    Ballot ballot = new Ballot();
    AttributeQuery query = new AttributeQuery("a", "b");
    election = AppContext.getEntityFactory().createElection(seat, committee, query, ballot);
  }

}
