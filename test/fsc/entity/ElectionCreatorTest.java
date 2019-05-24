package fsc.entity;

import fsc.entity.query.AttributeQuery;
import fsc.gateway.ElectionGateway;
import fsc.mock.AlwaysTrueQueryStub;
import org.junit.Before;

class ElectionCreatorTest {

  private Election election;
  private ElectionGateway mockGateway;
  Seat seat;
  Committee committee;

  @Before
  public void setUp() {
    Committee committee = new Committee("f", "g");
    AlwaysTrueQueryStub queryStub = new AlwaysTrueQueryStub();
    Seat seat = new Seat("a", queryStub);
    Ballot ballot = new Ballot();
    AttributeQuery query = new AttributeQuery("a", "b");
    election = new Election(seat, committee, query, ballot);
  }

}
