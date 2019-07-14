package fsc.entity;

import fsc.entity.query.AttributeQuery;
import fsc.entity.query.Query;
import fsc.gateway.ElectionGateway;
import org.junit.Before;

class ElectionCreatorTest {

  private Election election;
  private ElectionGateway mockGateway;
  Seat seat;
  Committee committee;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    Committee committee = entityFactory.createCommittee("f", "g");
    Query queryStub = Query.always();
    Seat seat = entityFactory.createSeat("a", queryStub, committee);
    Ballot ballot = entityFactory.createBallot();
    AttributeQuery query = new AttributeQuery("a", "b");
    election = entityFactory.createElection(seat, query, ballot);
  }

}
