package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;
import fsc.mock.EntityStub;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.request.ViewElectionRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewElectionRequestTest {

  public static final String ELECTION_ID = "1";
  public static final long SEAT_ID = 3;
  private ViewElectionRequest request = new ViewElectionRequest(ELECTION_ID);
  private SimpleEntityFactory entityFactory = new SimpleEntityFactory();
  private ElectionInteractor interactor;
  private Response response;
  private Election election;
  private Committee committee;
  private Profile[] profiles;
  ;

  @Before
  public void setUp() {
    profiles = new Profile[]{ EntityStub.getProfile(1), EntityStub.getProfile(2) };
    committee = entityFactory.createCommittee("a committee", "a description");
    Seat seat = entityFactory.createSeat("a seat", Query.always(), committee);
    seat.setId(SEAT_ID);
    Ballot ballot = entityFactory.createBallot();
    election = entityFactory.createElection(seat, Query.always(), ballot);
    election.setID(ELECTION_ID);
    election.getBallot().add(entityFactory.createCandidate(profiles[0], election.getBallot()));
    request = new ViewElectionRequest(ELECTION_ID);
  }

  @Test
  public void whenElectionIdIsInvalid_throwAnError() {
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertEquals(ELECTION_ID, electionGateway.requestedElectionId);
  }

  @Test
  public void whenElectionIdIsValid_returnElectionInformation() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ProvidedCommitteeGatewaySpy committeeGateway = new ProvidedCommitteeGatewaySpy(committee);
    ProfileGateway profileGateway = new ExistingProfileGatewaySpy(profiles);
    interactor = new ElectionInteractor(electionGateway, committeeGateway,
                                        profileGateway, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.ofElection(election), response);
    assertEquals(false, electionGateway.hasSaved);
  }
}